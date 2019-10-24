package me.fungames.kotlinSquish

import max

/** -----------------------------------------------------------------------------
 *	Copyright (c) 2006 Simon Brown                          si@sjbrown.co.uk
 *	Copyright (c) 2007 Ignacio Castano                   icastano@nvidia.com
 *	Permission is hereby granted, free of charge, to any person obtaining
 *	a copy of this software and associated documentation files (the
 *	"Software"), to	deal in the Software without restriction, including
 *	without limitation the rights to use, copy, modify, merge, publish,
 *	distribute, sublicense, and/or sell copies of the Software, and to
 *	permit persons to whom the Software is furnished to do so, subject to
 *	the following conditions:
 *	The above copyright notice and this permission notice shall be included
 *	in all copies or substantial portions of the Software.
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 *	OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *	MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *	IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 *	CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *	TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 *	SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *  @author FunGames
 *
 * --------------------------------------------------------------------------
 */

const val kMaxIterations = 8

@ExperimentalUnsignedTypes
class ClusterFit (colours : ColourSet, flags : Int) : ColourFit(colours, flags) {

    val iterationCount : Int = if (flags and kColourIterativeClusterFit != 0) kMaxIterations else 1
    val principle : Vec3
    val order = UBytePointer(UByteArray(16 * kMaxIterations))
    val pointsWeights = Pointer(Array(16) { Vec4() })
    var xsumWsum = Vec4()
    val metric : Vec4
    var bestError : Vec4 = Vec4(Float.MAX_VALUE)

    init {
        // initialise the metric
        val perceptual = flags and kColourMetricPerceptual != 0
        metric = if (perceptual)
            Vec4(0.2126f, 0.7152f, 0.0722f, 0.0f)
        else
            Vec4(1.0f)

        // cache some values
        val count = colours.count
        val values = colours.points

        // get the covariance matrix
        val covariance = computeWeightedCovariance(count, values, colours.weights)

        // compute the principle component
        principle = computePrincipleComponent(covariance)
    }

    fun constructOrdering(axis : Vec3, iteration : Int): Boolean {
        // cache some values
        val count = colours.count
        val values = colours.points

        // build the list of me.fungames.kotlinSquish.dot products
        val dps = FloatArray(16)
        val order = this.order + 16 * iteration
        for (i in 0 until count) {
            dps[i] = dot(values[i], axis)
            order[i] = i.toUByte()
        }

        // stable sort using them
        for (i in 0 until count) {
            var j = i
            while (j > 0 && dps[j] < dps[j - 1]) {
                val temp = dps[j]
                dps[j] = dps[j - 1]
                dps[j - 1] = temp
                val temp2 = order[j]
                order[j] = order[j - 1]
                order[j - 1] = temp2
                --j
            }
        }

        // check this ordering is unique
        for (it in 0 until iteration) {
            val prev = this.order + 16 * it
            var same = true
            for (i in 0 until count) {
                if (order[i] != prev[i]) {
                    same = false
                    break
                }
            }
            if (same)
                return false
        }

        // copy the ordering and weight all the points
        val unweighted = colours.points
        val weights = colours.weights
        xsumWsum = Vec4(0.0f)
        for (i in 0 until count) {
            val j = order[i]
            val p = Vec4(
                unweighted[j.toInt()].x,
                unweighted[j.toInt()].y,
                unweighted[j.toInt()].z,
                1.0f
            )
            val w = Vec4(weights[j.toInt()])
            val x = p * w
            pointsWeights[i] = x
            xsumWsum = xsumWsum + x
        }
        return true
    }


    override fun compress3(block: UBytePointer) {
        // declare variables
        val count = colours.count
        val two = Vec4(2.0f)
        val one = Vec4(1.0f)
        val halfHalf2 = Vec4(0.5f, 0.5f, 0.5f, 0.25f)
        val zero = Vec4(0.0f)
        val half = Vec4(0.5f)
        val grid = Vec4(31.0f, 63.0f, 31.0f, 0.0f)
        val gridrcp = Vec4(1.0f / 31.0f, 1.0f / 63.0f, 1.0f / 31.0f, 0.0f)

        // prepare an ordering using the principle axis
        constructOrdering(principle, 0)

        // check all possible clusters and iterate on the total order
        var bestStart = Vec4(0.0f)
        var bestEnd = Vec4(0.0f)
        var bestError = bestError
        val bestIndices = UByteArray(16)
        var bestIteration = 0
        var besti = 0; var bestj = 0

        // loop over iterations (we avoid the case that all points in first or last cluster)
        var iterationIndex = 0
        while (true) {
            // first cluster [0,i) is at the start
            val part0 = Vec4(0.0f)
            for (i in 0 until count) {
                // second cluster [i,j) is half along
                val part1 = if (i == 0) pointsWeights[0] else Vec4(0.0f)
                val jmin = if (i == 0) 1 else i
                var j = jmin
                while (true) {
                    // last cluster [j,count) is at the end
                    val part2 = xsumWsum - part1 - part0

                    // compute least squares terms directly
                    val alphaxSum = multiplyAdd(part1, halfHalf2, part0)
                    val alpha2Sum = alphaxSum.splatW()

                    val betaxSum = multiplyAdd(part1, halfHalf2, part2)
                    val beta2Sum = betaxSum.splatW()

                    val alphabetaSum = (part1 * halfHalf2).splatW()

                    // compute the least-squares optimal points
                    val factor = reciprocal(
                        negativeMultiplySubtract(
                            alphabetaSum,
                            alphabetaSum,
                            alpha2Sum * beta2Sum
                        )
                    )
                    var a = negativeMultiplySubtract(
                        betaxSum,
                        alphabetaSum,
                        alphaxSum * beta2Sum
                    ) * factor
                    var b = negativeMultiplySubtract(
                        alphaxSum,
                        alphabetaSum,
                        betaxSum * alpha2Sum
                    ) * factor

                    // clamp to the grid
                    a = min(one, max(zero, a))
                    b = min(one, max(zero, b))
                    a = truncate(multiplyAdd(grid, a, half)) * gridrcp
                    b = truncate(multiplyAdd(grid, b, half)) * gridrcp

                    // compute the error (we skip the constant xxsum)
                    val e1 = multiplyAdd(a * a, alpha2Sum, b * b * beta2Sum)
                    val e2 = negativeMultiplySubtract(a, alphaxSum, a * b * alphabetaSum)
                    val e3 = negativeMultiplySubtract(b, betaxSum, e2)
                    val e4 = multiplyAdd(two, e3, e1)

                    // apply the metric to the error term
                    val e5 = e4 * metric
                    val error = e5.splatX() + e5.splatY() + e5.splatZ()

                    // keep the solution if it wins
                    if (compareAnyLessThan(error, bestError)) {
                        bestStart = a
                        bestEnd = b
                        besti = i
                        bestj = j
                        bestError = error
                        bestIteration = iterationIndex
                    }

                    // advance
                    if (j == count)
                        break
                    part1 += pointsWeights[j]
                    ++j
                }

                // advance
                part0 += pointsWeights[i]
            }

            // stop if we didn't improve in this iteration
            if (bestIteration != iterationIndex)
                break

            // advance if possible
            ++iterationIndex
            if (iterationIndex == iterationCount)
                break

            // stop if a new iteration is an ordering that has already been tried
            val axis = (bestEnd - bestStart).getVec3()
            if (!constructOrdering(axis, iterationIndex))
                break
        }

        // save the block if necessary
        if (compareAnyLessThan(bestError, this.bestError)) {
            // remap the indices
            val order = this.order + 16 * bestIteration

            val unordered = UByteArray(16)
            for (m in 0 until besti)
                unordered[order[m].toInt()] = 0u
            for (m in besti until bestj)
                unordered[order[m].toInt()] = 2u
            for (m in bestj until count)
                unordered[order[m].toInt()] = 1u

            colours.remapIndices(
                UBytePointer(unordered),
                UBytePointer(bestIndices)
            )

            // save the block
            ColourBlock.writeColourBlock3(bestStart.getVec3(), bestEnd.getVec3(),
                UBytePointer(bestIndices), block)

            // save the error
            this.bestError = bestError
        }
    }

    override fun compress4(block: UBytePointer) {
        // declare variables
        val count = colours.count
        val two = Vec4(2.0f)
        val one = Vec4(1.0f)
        val onethirdOnethird2 = Vec4(1.0f / 3.0f, 1.0f / 3.0f, 1.0f / 3.0f, 1.0f / 9.0f)
        val twothirdsTwoThirds2 = Vec4(2.0f / 3.0f, 2.0f / 3.0f, 2.0f / 3.0f, 4.0f / 9.0f)
        val twonineths = Vec4(2.0f / 9.0f)
        val zero = Vec4(0.0f)
        val half = Vec4(0.5f)
        val grid = Vec4(31.0f, 63.0f, 31.0f, 0.0f)
        val gridrcp = Vec4(1.0f / 31.0f, 1.0f / 63.0f, 1.0f / 31.0f, 0.0f)

        // prepare an ordering using the principle axis
        constructOrdering(principle, 0)

        // check all possible clusters and iterate on the total order
        var bestStart = Vec4(0.0f)
        var bestEnd = Vec4(0.0f)
        var bestError = this.bestError
        val bestIndices = UByteArray(16)
        var bestIteration = 0
        var besti = 0; var bestj = 0; var bestk = 0

        // loop over iterations (we avoid the case that all points in first or last cluster)
        var iterationIndex = 0
        while (true) {

            // first cluster [0,i) is at the start
            var part0 = Vec4(0.0f)
            for (i in 0 until count) {
                // second cluster [i,j) is one third along
                var part1 = Vec4(0.0f)
                var j = i
                while (true) {
                    // third cluster [j,k) is two thirds along
                    var part2 = if (j == 0) pointsWeights[0] else Vec4(0.0f)
                    val kMin = if (j == 0) 1 else j
                    var k = kMin
                    while (true) {
                        // last cluster [k,count) is at the end
                        val part3 = xsumWsum - part2 - part1 - part0

                        // compute least squares terms directly
                        val alphaxSum =
                            multiplyAdd(
                                part2,
                                onethirdOnethird2,
                                multiplyAdd(part1, twothirdsTwoThirds2, part0)
                            )
                        val alpha2Sum = alphaxSum.splatW()

                        val betaxSum =
                            multiplyAdd(
                                part1,
                                onethirdOnethird2,
                                multiplyAdd(part2, twothirdsTwoThirds2, part3)
                            )
                        val beta2Sum = betaxSum.splatW()

                        val alphabetaSum = twonineths * (part1 + part2).splatW()

                        // compute the least-squares optimal points
                        val factor =
                            reciprocal(
                                negativeMultiplySubtract(
                                    alphabetaSum,
                                    alphabetaSum,
                                    alpha2Sum * beta2Sum
                                )
                            )
                        var a = negativeMultiplySubtract(
                            betaxSum,
                            alphabetaSum,
                            alphaxSum * beta2Sum
                        ) * factor
                        var b = negativeMultiplySubtract(
                            alphaxSum,
                            alphabetaSum,
                            betaxSum * alpha2Sum
                        ) * factor

                        // clamp to the grid
                        a = min(one, max(zero, a))
                        b = min(one, max(zero, b))
                        a = truncate(multiplyAdd(grid, a, half)) * gridrcp
                        b = truncate(multiplyAdd(grid, b, half)) * gridrcp

                        // compute the error
                        val e1 = multiplyAdd(a * a, alpha2Sum, b * b * beta2Sum)
                        val e2 = negativeMultiplySubtract(a, alphaxSum, a * b * alphabetaSum)
                        val e3 = negativeMultiplySubtract(b, betaxSum, e2)
                        val e4 = multiplyAdd(two, e3, e1)

                        // apply the metric to the error term
                        val e5 = e4 * metric
                        val error = e5.splatX() + e5.splatY() + e5.splatZ()

                        // keep the solution if it wins
                        if (compareAnyLessThan(error, bestError)) {
                            bestStart = a
                            bestEnd = b
                            bestError = error
                            besti = i
                            bestj = j
                            bestk = k
                            bestIteration = iterationIndex
                        }

                        // advance
                        if (k == count)
                            break
                        part2 = part2 + pointsWeights[k]
                        ++k
                    }

                    // advance
                    if (j == count)
                        break
                    part1 = part1 + pointsWeights[j]
                    ++j
                }

                // advance
                part0 = part0 + pointsWeights[i]
            }



            // stop if we didn't improve in this iteration
            if (bestIteration != iterationIndex)
                break

            // advance if possible
            ++iterationIndex
            if (iterationIndex == iterationCount)
                break

            // stop if a new iteration is an ordering that has already been tried
            val axis = (bestEnd - bestStart).getVec3()
            if (!constructOrdering(axis, iterationIndex))
                break
        }

        // save the block if necessary
        if (compareAnyLessThan(bestError, this.bestError)) {
            // remap the indices
            val order = this.order + 16 * bestIteration

            val unordered = UByteArray(16)
            for (m in 0 until besti)
                unordered[order[m].toInt()] = 0u
            for (m in besti until bestj)
                unordered[order[m].toInt()] = 2u
            for (m in bestj until bestk)
                unordered[order[m].toInt()] = 3u
            for (m in bestk until count)
                unordered[order[m].toInt()] = 1u

            colours.remapIndices(
                UBytePointer(unordered),
                UBytePointer(bestIndices)
            )

            // save the block
            ColourBlock.writeColourBlock4(bestStart.getVec3(), bestEnd.getVec3(),
                UBytePointer(bestIndices), block)

            // save the error
            this.bestError = bestError
        }
    }

}
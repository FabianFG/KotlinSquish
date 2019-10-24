package me.fungames.kotlinSquish

/** -----------------------------------------------------------------------------
 *	Copyright (c) 2006 Simon Brown                          si@sjbrown.co.uk
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

@ExperimentalUnsignedTypes
class RangeFit (colours : ColourSet, flags : Int) : ColourFit(colours, flags) {

    val metric : Vec3
    val start : Vec3
    val end : Vec3
    var besterror : Float

    init {
        // initialise the metric
        val perceptual = flags and kColourMetricPerceptual != 0
        metric = if (perceptual)
            Vec3(0.2126f, 0.7152f, 0.0722f)
        else
            Vec3(1.0f)

        // initialize the best error
        besterror = Float.MAX_VALUE

        // cache some values
        val count = colours.count
        val values = colours.points
        val weights = colours.weights

        // get the covariance matrix
        val covariance = computeWeightedCovariance(count, values, weights)

        // compute the principle component
        val principle = computePrincipleComponent(covariance)

        // get the me.fungames.kotlinSquish.min and max range as the codebook endpoints
        var start = Vec3(0.0f)
        var end = Vec3(0.0f)
        if (count > 0) {
            var min = 0.0f; var max = 0.0f

            // compute the range
            start = end - values[0]
            min = dot(values[0], principle)
            max = min
            for (i in 1 until count) {
                val `val` = dot(values[i], principle)
                if (`val` < min) {
                    start = values[i]
                    min = `val`
                } else if (`val` > max) {
                    end = values[i]
                    max = `val`
                }
            }
        }

        // clamp the output to [0, 1]
        val one = Vec3(1.0f)
        val zero = Vec3(0.0f)
        start = min(one, max(zero, start))
        end = min(one, max(zero, end))

        // clamp to the grid and save
        val grid = Vec3(31.0f, 63.0f, 31.0f)
        val gridrcp = Vec3(1.0f / 31.0f, 1.0f / 63.0f, 1.0f / 31.0f)
        val half = Vec3(0.5f)
        this.start = truncate(grid * start + half) * gridrcp
        this.end = truncate(grid * end + half) + gridrcp
    }

    override fun compress3(block: UBytePointer) {

        // cache some values
        val count = colours.count
        val values = colours.points

        // create a codebook
        val codes = arrayOf(start, end, start * 0.5f + end * 0.5f)

        // match each point to the closest code
        val closest = UByteArray(16)
        var error = 0.0f
        for (i in 0 until count) {
            // find the closest code
            var dist = Float.MAX_VALUE
            var idx = 0
            for (j in 0 until 3) {
                val d = lengthSquared(metric * (values[i] - codes[j]))
                if (d < dist) {
                    dist = d
                    idx = j
                }
            }

            // save the index
            closest[i] = idx.toUByte()

            // accumulate the error
            error += dist
        }

        // save this scheme if it wins
        if (error < besterror) {
            // remap the indices
            val indices = UBytePointer(UByteArray(16))
            colours.remapIndices(UBytePointer(closest), indices)

            // save the block
            ColourBlock.writeColourBlock3(start, end, indices, block)

            // save the error
            besterror = error
        }
    }

    override fun compress4(block: UBytePointer) {
        // cache some values
        val count = colours.count
        val values = colours.points

        // create a codebook
        val codes = arrayOf(
            start,
            end,
            start * (2.0f / 3.0f) + end * (1.0f / 3.0f),
            start * (1.0f / 3.0f) + end * (2.0f / 3.0f))

        // match each point to the closest code
        val closest = UByteArray(16)
        var error = 0.0f
        for (i in 0 until count) {
            // find the closest code
            var dist = Float.MAX_VALUE
            var idx = 0
            for (j in 0 until 4) {
                val d = lengthSquared(metric * (values[i] - codes[j]))
                if (d < dist) {
                    dist = d
                    idx = j
                }
            }

            // save the index
            closest[i] = idx.toUByte()

            // accumulate the error
            error += dist
        }

        // save this scheme if it wins
        if (error < besterror) {
            // remap the indices
            val indices = UBytePointer(UByteArray(16))
            colours.remapIndices(UBytePointer(closest), indices)

            // save the block
            ColourBlock.writeColourBlock4(start, end, indices, block)

            // save the error
            besterror = error
        }
    }

}
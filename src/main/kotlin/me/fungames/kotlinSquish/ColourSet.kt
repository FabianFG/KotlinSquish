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

import kotlin.math.sqrt

@ExperimentalUnsignedTypes
class ColourSet(val rgba : UBytePointer, val mask : Int, val flags : Int) {

    var count = 0
    val points = Pointer(Array(16) { Vec3(0.0f) })
    val weights = Pointer(FloatArray(16).toTypedArray())
    val remap = Pointer(IntArray(16).toTypedArray())
    var transparent = false

    init {
        // check the compression mode for dxt1
        val isDxt1 = flags and kDxt1 != 0
        val weightByAlpha = flags and kWeightColourByAlpha != 0

        // create the minimal set
        for (i in 0 until 16) {
            // check this pixel is enabled
            val bit = 1 shl i
            if (mask and bit == 0) {
                remap[i] = -1
                continue
            }

            // check for transparent pixels when using dxt1
            if (isDxt1 && rgba[4 * i + 3] < 128u) {
                remap[i] = -1
                transparent = true
                continue
            }

            // loop over previous points for a match
            var j = 0
            while (true) {
                // allocate a new point
                if (j == i) {
                    // normalise coordinated to [0,1]
                    val x = rgba[4 * i].toFloat() / 255.0f
                    val y = rgba[4 * i + 1].toFloat() / 255.0f
                    val z = rgba[4 * i + 2].toFloat() / 255.0f

                    // ensure there is always non-zero weight even for zero alpha
                    val w = (rgba[4 * i + 3] + 1u).toFloat() / 256.0f

                    // add the point
                    points[count] = Vec3(x, y, z)
                    weights[count] = if (weightByAlpha) w else 1.0f
                    remap[i] = count

                    // advance
                    ++count
                    break
                }

                // check for a match
                val oldbit = 1 shl j
                val match = (mask and oldbit != 0
                        && rgba[4 * i] == rgba[4 * j]
                        && rgba[4 * i + 1] == rgba[4 * j + 1]
                        && rgba[4 * i + 2] == rgba[4 * j + 2]
                        && (rgba[4 * j + 3] >= 128u || !isDxt1))
                if (match) {
                    // get the index of the match
                    val index = remap[j]

                    // ensure there is always non-zero weight even for zero alpha
                    val w = (rgba[4 * i + 3] + 1u).toFloat() / 256.0f

                    // map to this point and increase the weight
                    weights[index] += if (weightByAlpha) w else 1.0f
                    remap[i] = index
                    break
                }
                ++j
            }
        }

        // square root the weights
        for (i in 0 until count)
            weights[i] = sqrt(weights[i])
    }

    fun remapIndices(source : UBytePointer, target : UBytePointer) {
        for (i in 0 until 16) {
            val j = remap[i]
            if (j == -1)
                target[i] = 3u
            else
                target[i] = source[j]
        }
    }

}
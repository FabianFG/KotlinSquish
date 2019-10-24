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

import kotlin.math.max

object Alpha {
    @ExperimentalUnsignedTypes
    fun compressAlphaDxt3(rgba : UBytePointer, mask : Int, block : UBytePointer) {
        // quantise and pack the alpha values pairwise
        for (i in 0 until 8) {
            // quantise down to 4 bits
            val alpha1 = rgba[8 * i + 3].toFloat() * (15.0f/255.0f)
            val alpha2 = rgba[8 * i + 7].toFloat() * (15.0f/255.0f)
            var quant1 = floatToInt(alpha1, 15)
            var quant2 = floatToInt(alpha2, 15)

            // set alpha to zero where masked
            val bit1 = 1 shl (2 * i)
            val bit2 = 1 shl (2 * i + 1)
            if (mask and bit1 == 0)
                quant1 = 0
            if (mask and bit2 == 0)
                quant2 = 0

            // pack into the byte
            block[i] = (quant1 or (quant2 shl  4)).toUByte()
        }
    }

    @ExperimentalUnsignedTypes
    fun decompressAlphaDxt3(rgba: UBytePointer, block: UBytePointer) {
        // unpack the alpha values pairwise
        for (i in 0 until 8) {
            // quantise down to 4 bits
            val quant = block[i]

            // unpack the values
            val lo = quant and 0x0fu
            val hi = quant and 0xf0u

            // convert back up to bytes
            rgba[8 * i + 3] = lo or (lo.toInt() shl 4).toUByte()
            rgba[8 * i + 7] = hi or (hi.toInt() ushr 4).toUByte()
        }
    }

    fun fixRange(min : Int, max : Int, steps : Int) : Pair<Int, Int> {
        var newMax = max
        var newMin = min
        if (max - min < steps)
            newMax = kotlin.math.min(min + steps, 255)
        if (newMax - min < steps)
            newMin = max(0, max - steps)
        return newMin to newMax
    }

    @ExperimentalUnsignedTypes
    fun fitCodes(rgba: UBytePointer, mask: Int, codes : UBytePointer, indices : UBytePointer): Int {
        // fit each alpha value to the codebook
        var err = 0
        for (i in 0 until 16) {
            // check this pixel is valid
            val bit = 1 shl i
            if ((mask and bit) == 0) {
                // use the first code
                indices[i] = 0u
                continue
            }

            // find the least error and corresponding index
            val value = rgba[4 * i + 3]
            var least = Int.MAX_VALUE
            var index = 0
            for (j in 0 until 8) {
                // get the squared error from this code
                var dist = value.toInt() - codes[j].toInt()
                dist *= dist

                // compare with the best so far
                if (dist < least) {
                    least = dist
                    index = j
                }
            }

            // save this index and accumulate the error
            indices[i] = index.toUByte()
            err += least
        }

        // return the total error
        return err
    }

    @ExperimentalUnsignedTypes
    fun writeAlphaBlock(alpha0 : Int, alpha1 : Int, indices : UBytePointer, block : UBytePointer) {
        // write the first two bytes
        block[0] = alpha0.toUByte()
        block[1] = alpha1.toUByte()

        // pack the indices with 3 bits each
        val dest = block + 2
        val src = indices
        for (i in 0 until 2) {
            // pack 8 3-bit values
            var value = 0
            for (j in 0 until 8) {
                val index = src[0]
                src += 1
                value = value or (index.toInt() shl 3 * j)
            }

            // store in 3 bytes
            for (j in 0 until 3) {
                val byte = (value shr 8 * j) and 0xff
                dest[0] = byte.toUByte()
                dest += 1
            }
        }
    }

    @ExperimentalUnsignedTypes
    fun writeAlphaBlock5(alpha0 : Int, alpha1 : Int, indices : UBytePointer, block : UBytePointer) {
        // check the relative values of the endpoints
        if (alpha0 > alpha1) {
            // swap the indices
            val swapped = UBytePointer(UByteArray(16))
            for (i in 0 until 16) {
                val index = indices[i]
                when(index.toInt()) {
                    0 -> swapped[i] = 1u
                    1 -> swapped[i] = 0u
                    else -> swapped[i] = index
                }

                // write the block
                writeAlphaBlock(alpha1, alpha0, swapped, block)
            }
        } else {
            // write the block
            writeAlphaBlock(alpha0, alpha1, indices, block)
        }
    }

    @ExperimentalUnsignedTypes
    fun writeAlphaBlock7(alpha0 : Int, alpha1 : Int, indices : UBytePointer, block : UBytePointer) {
        // check the relative values of the endpoints
        if (alpha0 < alpha1) {
            //swap the indices
            val swapped = UBytePointer(UByteArray(16))
            for (i in 0 until 16) {
                val index = indices[i]
                when(index.toInt()) {
                    0 -> swapped[i] = 1u
                    1 -> swapped[i] = 0u
                    else -> swapped[i] = (9u - index).toUByte()
                }
            }

            // write the block
            writeAlphaBlock(alpha1, alpha0, indices, block)
        } else {
            // write the block
            writeAlphaBlock(alpha0, alpha1, indices, block)
        }
    }

    @ExperimentalUnsignedTypes
    fun compressAlphaDxt5(rgba: UBytePointer, mask: Int, block: UBytePointer) {
        // get the range for 5-alpha and 7-alpha interpolation
        var min5 = 255
        var max5 = 0
        var min7 = 255
        var max7 = 0
        for (i in 0 until 16) {
            // check this pixel is valid
            val bit = 1 shl i
            if (mask and bit == 0)
                continue

            // incorporate into the me.fungames.kotlinSquish.min/max
            val value = rgba[4 * i + 3].toInt()
            if (value < min7)
                min7 = value
            if (value > max7)
                max7 = value
            if (value != 0 && value < min5)
                min5 = value
            if (value != 255 && value > max5)
                max5 = value
        }

        // handle the case that no valid range was found
        if (min5 > max5)
            min5 = max5
        if (min7 > max7)
            min7 = max7

        // fix the range to be minimum in each case
        var fixed = fixRange(min5, max5, 5)
        min5 = fixed.first
        max5 = fixed.second
        fixed = fixRange(min7, max7, 7)
        min7 = fixed.first
        max7 = fixed.second

        // set up the 5-alpha code book
        val codes5 = UBytePointer(UByteArray(8))
        codes5[0] = min5.toUByte()
        codes5[1] = max5.toUByte()
        for (i in 1 until 5)
            codes5[1 + i] = (((5 - i) * min5 + i * max5) / 5).toUByte()
        codes5[6] = 0u
        codes5[7] = 255u

        // set up the 7-alpha code book
        val codes7 = UBytePointer(UByteArray(8))
        codes7[0] = min7.toUByte()
        codes7[1] = max7.toUByte()
        for (i in 1 until 7)
            codes7[1 + i] = (((7 - i) * min7 + i * max7) / 7).toUByte()

        // fit the data to both code books
        val indices5 = UBytePointer(UByteArray(16))
        val indices7 = UBytePointer(UByteArray(16))
        val err5 = fitCodes(rgba, mask, codes5, indices5)
        val err7 = fitCodes(rgba, mask, codes7, indices7)

        // save the block with least error
        if (err5 <= err7)
            writeAlphaBlock5(min5, max5, indices5, block)
        else
            writeAlphaBlock7(min7, max7, indices7, block)
    }

    @ExperimentalUnsignedTypes
    fun decompressAlphaDxt5(rgba: UBytePointer, block: UBytePointer) {
        // get the two alpha values
        val alpha0 = block[0].toInt()
        val alpha1 = block[1].toInt()

        // compare the values to build the codebook
        val codes = UBytePointer(UByteArray(8))
        codes[0] = alpha0.toUByte()
        codes[1] = alpha1.toUByte()
        if (alpha0 < alpha1) {
            // use 5-alpha codebook
            for (i in 1 until 5)
                codes[1 + i] = ((((5 - 1) * alpha0 + i * alpha1) / 5).toUByte())
            codes[6] = 0u
            codes[7] = 255u
        } else {
            // use 7-alpha codebook
            for (i in 1 until 7)
                codes[1 + i] = ((((7 - 1) * alpha0 + i * alpha1) / 7).toUByte())
        }

        // decode the indices
        val indices = UBytePointer(UByteArray(16))
        val src = block + 2
        val dest = indices + 0
        for (i in 0 until 2) {
            // grab 3 bytes
            var value = 0
            for (j in 0 until 3) {
                val byte = src[0]
                src+=1
                value = value or (byte.toInt() shl 8*j)
            }

            // unpack 8 3-bit values from it
            for (j in 0 until 8) {
                val index = (value shr 3*j) and 0x7
                dest[0] = index.toUByte()
                dest += 1
            }
        }

        // write out the indexed codebook values
        for (i in 0 until 16)
            rgba[4 * i + 3] = codes[indices[i].toInt()]
    }
}
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

object ColourBlock {

    @ExperimentalUnsignedTypes
    fun writeColourBlock(a : Int, b : Int, indices : UBytePointer, block : UBytePointer) {
        // write the endpoints
        block[0] = (a and 0xff).toUByte()
        block[1] = (a shr 8).toUByte()
        block[2] = (b and 0xff).toUByte()
        block[3] = (b shr 8).toUByte()

        // write the indices
        for (i in 0 until 4)
            block[4 + i] = indices[4*i] or (indices[4*i + 1].toInt() shl 2).toUByte() or (indices[4*i + 2].toInt() shl 4).toUByte() or (indices[4*i + 3].toInt() shl 6).toUByte()
    }

    @ExperimentalUnsignedTypes
    fun writeColourBlock3(start : Vec3, end : Vec3, indices: UBytePointer, block: UBytePointer) {
        // get the packed values
        var a = floatTo565(start)
        var b = floatTo565(end)

        // remap the indices
        val remapped = UByteArray(16)
        if (a <= b) {
            // use the indices directly
            for (i in 0 until 16)
                remapped[i] = indices[i]
        } else {
            // swap a and b
            val temp = a
            a = b
            b = temp
            for (i in 0 until 16)
                when(indices[i].toInt()) {
                    0 -> remapped[i] = 1u
                    1 -> remapped[i] = 0u
                    else -> remapped[i] = indices[i]
                }

        }

        // write the block
        writeColourBlock(a, b, UBytePointer(remapped), block)
    }

    @ExperimentalUnsignedTypes
    fun writeColourBlock4(start: Vec3, end: Vec3, indices: UBytePointer, block: UBytePointer) {
        // get the packed values
        var a = floatTo565(start)
        var b = floatTo565(end)

        // remap the indices
        val remapped = UByteArray(16)
        when {
            a < b -> {
                // swap a and b
                val temp = a
                a = b
                b = temp
                for (i in 0 until 16)
                    remapped[i] = (indices[i] xor 0x1u) and 0x3u
            }
            a == b -> // use index 0
                for (i in 0 until 16)
                    remapped[i] = 0u
            else -> // use the indices directly
                for (i in 0 until 16)
                    remapped[i] = indices[i]
        }

        // write the block
        writeColourBlock(a, b, UBytePointer(remapped), block)
    }

    @ExperimentalUnsignedTypes
    fun unpack565(packed : UBytePointer, colour : UBytePointer) : Int {

        // build the packed value
        val value = packed[0].toInt() or (packed[1].toInt() shl 8)

        // get the components in the stored range
        val red = ((value shr 11) and 0x1f).toUByte()
        val green = ((value shr 5) and 0x3f).toUByte()
        val blue = (value and 0x1f).toUByte()

        // scale up to 8 bits
        colour[0] = (red.toInt() shl 3).toUByte() or (red.toInt() ushr 2).toUByte()
        colour[1] = (green.toInt() shl 2).toUByte() or (green.toInt() ushr 4).toUByte()
        colour[2] = (blue.toInt() shl 3).toUByte() or (blue.toInt() ushr 2).toUByte()
        colour[3] = 255u

        // return the value
        return value
    }

    @ExperimentalUnsignedTypes
    fun decompressColour(rgba : UBytePointer, block: UBytePointer, isDxt1 : Boolean) {


        // unpack the endpoints
        val codes = UBytePointer(UByteArray(16))
        val a = unpack565(block, codes)
        val b = unpack565(block + 2, codes + 4)

        // generate the midpoints
        for (i in 0 until 3) {
            val c = codes[i]
            val d = codes[4 + i]

            if (isDxt1 && a <= b) {
                codes[8 + i] = ((c + d) / 2u).toUByte()
                codes[12 + i] = 0u
            } else {
                codes[8 + i] = ((2u * c + d) / 3u).toUByte()
                codes[12 + i] = ((c + 2u * d) / 3u).toUByte()
            }
        }

        // fill in alpha for the intermediate values
        codes[8 + 3] = 255u
        codes[12 + 3] = if (isDxt1 && a <= b) 0u else 255u

        // unpack the indices
        val indices = UByteArray(16)
        for (i in 0 until 4) {
            val packed = block[4 + i]

            indices[4*i] = packed and 0x3u
            indices[4*i + 1] = (packed.toInt() shr 2).toUByte() and 0x3u
            indices[4*i + 2] = (packed.toInt() shr 4).toUByte() and 0x3u
            indices[4*i + 3] = (packed.toInt() shr 6).toUByte() and 0x3u
        }

        // store out the colours
        for (i in 0 until 16) {
            val offset = 4 * indices[i].toInt()
            for (j in 0 until 4)
                rgba[4 * i + j] = codes[offset + j]
        }
    }
}
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

//! Use DXT1 compression.
const val kDxt1 = 1 shl 0

//! Use DXT3 compression.
const val kDxt3 = 1 shl 1

//! Use DXT5 compression.
const val kDxt5 = 1 shl 2

//! Use a very slow but very high quality colour compressor.
const val kColourIterativeClusterFit = 1 shl 8

//! Use a slow but high quality colour compressor (the default).
const val kColourClusterFit = 1 shl 3

//! Use a fast but low quality colour compressor.
const val kColourRangeFit = 1 shl 4

//! Use a perceptual metric for colour error (the default).
const val kColourMetricPerceptual = 1 shl 5

//! Use a uniform metric for colour error.
const val kColourMetricUniform = 1 shl 6

//! Weight the colour by alpha during cluster fit (disabled by default).
const val kWeightColourByAlpha = 1 shl 7

object Squish {
    enum class CompressionType(val value : Int) {
        DXT1(kDxt1),
        DXT3(kDxt3),
        DXT5(kDxt5)
    }

    enum class CompressionMethod(val value : Int) {
        ITERATIVE_CLUSTER_FIT(kColourIterativeClusterFit),
        CLUSTER_FIT(kColourClusterFit),
        RANGE_FIT(kColourRangeFit)
    }

    enum class ColourMetric(val value: Int) {
        UNIFORM(kColourMetricUniform),
        PERCEPTUAL(kColourMetricPerceptual)
    }

    private fun fixFlags(flags : Int) : Int {
        // grab the flag bits
        var method = flags and (kDxt1 or kDxt3 or kDxt5)
        var fit = flags and (kColourIterativeClusterFit or kColourClusterFit or kColourRangeFit)
        var metric = flags and (kColourMetricPerceptual or kColourMetricUniform)
        val extra = flags and kWeightColourByAlpha

        // set defaults
        if (method != kDxt3 && method != kDxt5)
            method = kDxt1
        if (fit != kColourRangeFit)
            fit = kColourClusterFit
        if (metric != kColourMetricUniform)
            metric = kColourMetricPerceptual

        // done
        return method or fit or metric or extra
    }

    @ExperimentalUnsignedTypes
    fun compress(rgba : UBytePointer, block : UBytePointer, flags: Int) {
        // compress with full mask
        compressMasked(rgba, 0xffff, block, flags)
    }

    @ExperimentalUnsignedTypes
    fun compressMasked(rgba: UBytePointer, mask : Int, block: UBytePointer, origFlags: Int) {
        // fix any bad flags
        val flags = fixFlags(origFlags)

        // get the block locations
        if (block.pos == 17184)
            println()
        var colourBlock = block
        if (flags and (kDxt3 or kDxt5) != 0)
            colourBlock = block + 8

        // create minimal point set
        val colours = ColourSet(rgba, mask, flags)

        // check the compression type and compress colour
        val fit = if (colours.count == 1) {
            // always do a single colour fit
            SingleColourFit(colours, flags)
        } else if ((flags and kColourRangeFit) != 0 || colours.count == 0) {
            // do a range fit
            RangeFit(colours, flags)
        } else {
            // default to a cluster fit (could be iterative or not)
            ClusterFit(colours, flags)
        }
        fit.compress(colourBlock)

        // compress alpha separately if necessary
        if (flags and kDxt3 != 0)
            Alpha.compressAlphaDxt3(rgba, mask, block)
        else if (flags and kDxt5 != 0)
            Alpha.compressAlphaDxt5(rgba, mask, block)
    }

    @ExperimentalUnsignedTypes
    fun decompress(rgba: UBytePointer, block: UBytePointer, origFlags: Int) {
        // fix any bad flags
        val flags = fixFlags(origFlags)

        // get the block locations
        var colourBlock = block
        if (flags and (kDxt3 or kDxt5) != 0)
            colourBlock = block + 8

        // decompress colour
        ColourBlock.decompressColour(rgba, colourBlock, (flags and kDxt1) != 0)

        // decompress alpha separately if necessary
        if (flags and kDxt3 != 0)
            Alpha.decompressAlphaDxt3(rgba, block)
        else if (flags and kDxt5 != 0)
            Alpha.decompressAlphaDxt5(rgba, block)
    }

    fun getStorageRequirements(width : Int, height : Int, origFlags: Int): Int {
        // fix any bad flags
        val flags = fixFlags(origFlags)

        // compute the storage requirements
        val blockcount = (width + 3) / 4 * ((height + 3) / 4)
        val blocksize = if (flags and kDxt1 != 0) 8 else 16
        return blockcount * blocksize
    }

    fun compressImage(width: Int, height: Int, source: ByteArray, type: CompressionType, method: CompressionMethod, metric : ColourMetric, weightAlpha : Boolean): ByteArray {
        var flags = type.value or method.value or metric.value
        if (weightAlpha)
            flags = flags or kWeightColourByAlpha
        return compressImage(width, height, source, flags)
    }
    fun compressImage(width: Int, height: Int, rgba: ByteArray, origFlags: Int) : ByteArray {
        val uRgba = UBytePointer(rgba.toUByteArray())
        val uBlocks =
            UBytePointer(UByteArray(getStorageRequirements(width, height, origFlags)))
        compressImage(uRgba, width, height, uBlocks, origFlags)
        return uBlocks.data.toByteArray()
    }

    @ExperimentalUnsignedTypes
    fun compressImage(rgba: UBytePointer, width: Int, height: Int, blocks: UBytePointer, origFlags: Int) {
        // fix any bad flags
        val flags = origFlags

        // initialise the block output
        var targetBlock = blocks + 0
        val bytesPerBlock = if (flags and kDxt1 != 0) 8 else 16

        // loop over blocks
        for (y in 0 until height step 4) {
            for (x in 0 until width step 4) {
                // build the 4x4 block of pixels
                val sourceRgba = UByteArray(16 * 4)
                var targetPixel = UBytePointer(sourceRgba)
                var mask = 0
                for (py in 0 until 4) {
                    for (px in 0 until 4) {
                        // get the source pixel in the image
                        val sx = x + px
                        val sy = y + py

                        // enable if we're in the image
                        if (sx < width && sy < height) {
                            // copy the rgba value
                            var sourcePixel = rgba + 4 * (width * sy + sx)
                            for (i in 0 until 4) {
                                targetPixel[0] = sourcePixel[0]
                                targetPixel = targetPixel + 1
                                sourcePixel = sourcePixel + 1
                            }

                            // enable this pixel
                            mask = mask or (1 shl (4 * py + px))
                        } else {
                            // skip this pixel as its outside the image
                            targetPixel = targetPixel + 4
                        }
                    }
                }

                // compress it into the output
                compressMasked(UBytePointer(sourceRgba), mask, targetBlock, flags)

                // advance
                targetBlock = targetBlock + bytesPerBlock
            }
        }
    }

    fun decompressImage(width: Int, height: Int, source: ByteArray, type: CompressionType) =
        decompressImage(width, height, source, type.value)
    fun decompressImage(width: Int, height: Int, source: ByteArray, origFlags: Int) : ByteArray {
        val uBlocks = UBytePointer(source.toUByteArray())
        val uRgba = UBytePointer(UByteArray(width * height * 4))
        decompressImage(uRgba, width, height, uBlocks, origFlags)
        return uRgba.data.toByteArray()
    }

    @ExperimentalUnsignedTypes
    fun decompressImage(rgba: UBytePointer, width: Int, height: Int, blocks: UBytePointer, origFlags: Int) {
        // fix any bad flags
        val flags = fixFlags(origFlags)

        // initialise the block input
        var sourceBlock = blocks + 0
        val bytesPerBlock = if (flags and kDxt1 != 0) 8 else 16

        // loop over blocks
        for (y in 0 until height step 4) {
            for (x in 0 until width step 4) {
                val targetRgba = UBytePointer(UByteArray(4 * 16))
                decompress(targetRgba, sourceBlock, flags)

                // write the decompressed pixels to the correct image locations
                var sourcePixel = targetRgba + 0
                for (py in 0 until 4) {
                    for (px in 0 until 4) {
                        // get the target location
                        val sx = x + px
                        val sy = y + py
                        if (sx < width && sy < height) {
                            var targetPixel = rgba + 4 * (width * sy + sx)

                            // copy the rgba value
                            for (i in 0 until 4) {
                                targetPixel[0] = sourcePixel[0]
                                targetPixel = targetPixel + 1
                                sourcePixel = sourcePixel + 1
                            }
                        } else {
                            // skip this pixel as its outside the image
                            sourcePixel = sourcePixel + 4
                        }
                    }
                }

                // advance
                sourceBlock = sourceBlock + bytesPerBlock
            }
        }
    }
}
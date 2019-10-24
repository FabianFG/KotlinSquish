package me.fungames.kotlinSquish

@ExperimentalUnsignedTypes
class SourceBlock(var start : UByte, var end : UByte, var error : UByte)
@ExperimentalUnsignedTypes
class SingleColourLookup(var sources : Array<SourceBlock>)

@ExperimentalUnsignedTypes
class SingleColourFit(colours : ColourSet, flags : Int) : ColourFit(colours, flags) {

    val colour = UByteArray(3)
    var start = Vec3()
    var end = Vec3()
    var index : UByte = 0u
    var error = 0
    var bestError : Int

    init {
        // grab the single colour
        val values = colours.points
        colour[0] = floatToInt(255.0f * values[0].x, 255).toUByte()
        colour[1] = floatToInt(255.0f * values[0].y, 255).toUByte()
        colour[2] = floatToInt(255.0f * values[0].z, 255).toUByte()

        // initialise the best error
        bestError = Int.MAX_VALUE
    }

    override fun compress3(block: UBytePointer) {
        // build the table of lookups
        val lookups = arrayOf(
            SingleColourLookupx3.lookup_5_3,
            SingleColourLookupx3.lookup_6_3,
            SingleColourLookupx3.lookup_5_3
        )

        // find the best end-points and index
        computeEndpoints(lookups)

        // build the block if we win
        if (error < bestError) {
            // remap the indices
            val indices = UBytePointer(UByteArray(16))
            colours.remapIndices(UBytePointer(ubyteArrayOf(index)), indices)

            // save the block
            ColourBlock.writeColourBlock3(start, end, indices, block)

            // save the error
            bestError = error
        }

    }

    override fun compress4(block: UBytePointer) {
        // build the table of lookups
        val lookups = arrayOf(
            SingleColourLookupx4.lookup_5_4,
            SingleColourLookupx4.lookup_6_4,
            SingleColourLookupx4.lookup_5_4
        )

        // find the best end-points and index
        computeEndpoints(lookups)

        // build the block if we win
        if (error < bestError) {
            // remap the indices
            val indices = UBytePointer(UByteArray(16))
            colours.remapIndices(UBytePointer(ubyteArrayOf(index)), indices)

            // save the block
            ColourBlock.writeColourBlock4(start, end, indices, block)

            // save the error
            bestError = error
        }
    }

    fun computeEndpoints(lookups : Array<Array<Array<IntArray>>>) {
        // check each index combination (endpoint or intermediate)
        error = Int.MAX_VALUE
        for (index in 0 until 2) {
            // check the error for this codebook index
            val sourcesList = mutableListOf<IntArray>()
            var error = 0
            for (channel in 0 until 3) {
                // grab the lookup table and index for this channel
                val lookup = lookups[channel]
                val target = colour[channel]

                // store a pointer to the source for this channel
                sourcesList.add(lookup[target.toInt()][index])

                // accumulate the error
                val diff = sourcesList[channel][2]
                error += diff * diff
            }

            // keep it if the error is lower
            if (error < this.error) {
                start = Vec3(
                    sourcesList[0][0].toUByte().toFloat() * (1.0f / 31.0f),
                    sourcesList[1][0].toUByte().toFloat() * (1.0f / 63.0f),
                    sourcesList[2][0].toUByte().toFloat() * (1.0f / 31.0f)
                )
                end = Vec3(
                    sourcesList[0][1].toUByte().toFloat() * (1.0f / 31.0f),
                    sourcesList[1][1].toUByte().toFloat() * (1.0f / 63.0f),
                    sourcesList[2][1].toUByte().toFloat() * (1.0f / 31.0f)
                )
                this.index = (2 * index).toUByte()
                this.error = error
            }
        }
    }
}

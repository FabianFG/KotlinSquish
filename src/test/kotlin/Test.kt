
import me.fungames.kotlinSquish.Squish
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

@ExperimentalUnsignedTypes
fun main() {
    val data = File("D:\\Fabian\\IDEA_WORKSPACE\\JFortniteParse\\origDxtBuffer").readBytes()
    val decompressed = Squish.decompressImage(512, 512, data, Squish.CompressionType.DXT5)
    val recompressed = Squish.compressImage(512, 512, decompressed, Squish.CompressionType.DXT5, Squish.CompressionMethod.CLUSTER_FIT, Squish.ColourMetric.UNIFORM, false)
    val redecompressed = Squish.decompressImage(512, 512, recompressed, Squish.CompressionType.DXT5)
    File("D:\\Fabian\\IDEA_WORKSPACE\\JFortniteParse\\recompressedDxtBuffer").writeBytes(recompressed)
    val img = BufferedImage(512, 512, BufferedImage.TYPE_4BYTE_ABGR)
    img.raster.setDataElements(0, 0, 512, 512, redecompressed)
    ImageIO.write(img, "png", File("D:\\Fabian\\IDEA_WORKSPACE\\JFortniteParse\\recompressedDxtBuffer.png"))
}
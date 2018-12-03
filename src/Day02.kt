import org.junit.jupiter.api.Test
import java.io.File

class Day02 {
    data class Box(val length: Int, val width: Int, val height: Int) {
        fun getSurfaceArea(): Int = listOf(length * width, width * height, height * length).let { x ->
            return 2 * x[0] + 2 * x[1] + 2 * x[2] + x.min()!!
        }

        fun getShortestCircumference(): Int =
            listOf(length * 2 + width * 2, width * 2 + height * 2, height * 2 + length * 2).min()!!

        fun getVolume(): Int = length * width * height
    }

    private val boxes: List<Box> = File(this.javaClass.getResource("02.txt").path).useLines {
        it.map { b ->
            b.split('x').map(String::toInt).let { dim -> Box(dim[0], dim[1], dim[2]) }
        }.toList()
    }

    @Test
    fun part1() {
        println(boxes.map(Box::getSurfaceArea).sum())
    }

    @Test
    fun part2() {
        println(boxes.map { it.getShortestCircumference() + it.getVolume() }.sum())
    }
}

import org.junit.jupiter.api.Test
import java.io.File

class Day01 {
    private val instructions: List<Int> = File(this.javaClass.getResource("01.txt").path).readText().map {
        when (it) {
            '(' -> 1
            ')' -> -1
            else -> 0
        }
    }

    @Test
    fun part1() {
        println(instructions.sum())
    }

    @Test
    fun part2() {
        var floor = 0
        instructions.withIndex().forEach {
            floor += it.value
            if (floor == -1) {
                println(it.index + 1)
                return
            }
        }
    }

}

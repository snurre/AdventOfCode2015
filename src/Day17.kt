import org.junit.jupiter.api.Test
import java.io.File

class Day17 {
    private val solutions: Map<Int, Int> = {
        val containers = File(this.javaClass.getResource("17.txt").path).readLines().map(String::toInt)
        val result = hashMapOf<Int, Int>()
        for (i in 0 until (1 shl containers.size)) {
            val p = containers.withIndex().filter { i and (1 shl it.index) > 0 }.map { it.value }
            if (p.sum() == 150) {
                result.putIfAbsent(p.size, 0)
                result[p.size] = result[p.size]!! + 1
            }
        }
        result
    }.invoke()

    @Test
    fun part1() {
        println(solutions.values.sum())
    }

    @Test
    fun part2() {
        println(solutions[solutions.keys.min()])
    }
}

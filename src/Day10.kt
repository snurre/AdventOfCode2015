import org.junit.jupiter.api.Test
import java.lang.StringBuilder

class Day10 {
    private val puzzleInput = "1321131112"

    private fun mutate(input: String): String {
        val sb = StringBuilder()
        var i = 0
        while (i < input.length) {
            val a = input[i]
            var j = 1
            while (i + j < input.length) {
                val b = input[i + j]
                if (a != b) {
                    sb.append(j).append(a)
                    break
                }
                j++
            }
            i += j
            if (i == input.length - 1) {
                sb.append(1).append(input[i])
            }
        }
        return sb.toString()
    }

    private fun solve(iterations: Int): Int {
        var value = puzzleInput
        for (i in 0 until iterations) {
            value = mutate(value)
        }
        return value.length
    }

    @Test
    fun part1() {
        println(solve(40))
    }

    @Test
    fun part2() {
        println(solve(50))
    }
}

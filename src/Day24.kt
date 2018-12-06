import org.junit.jupiter.api.Test
import java.io.File

class Day24 {
    private val packages =
        File(this.javaClass.getResource("24.txt").path).readLines().map(String::toInt).sortedDescending()

    private fun split(packages: List<Int>, i: Int, remaining: Int, qe: Long, count: Int): Pair<Long, Int> {
        if (remaining == 0) return qe to count
        if (remaining < 0 || i == packages.size) return Long.MAX_VALUE to Int.MAX_VALUE

        val included = split(packages, i + 1, remaining - packages[i], qe * packages[i], count + 1)
        val excluded = split(packages, i + 1, remaining, qe, count)

        return when {
            included.second < excluded.second -> included
            excluded.second < included.second -> excluded
            included.first < excluded.first -> included
            else -> excluded
        }
    }

    @Test
    fun part1() {
        println(split(packages, 0, packages.sum() / 3, 1, 0).first)
    }

    @Test
    fun part2() {
        println(split(packages, 0, packages.sum() / 4, 1, 0).first)
    }
}

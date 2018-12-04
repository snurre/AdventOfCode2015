import org.junit.jupiter.api.Test
import java.io.File

class Day18 {
    private val size = 100
    private val steps = 100
    private val initialMatrix = File(this.javaClass.getResource("18.txt").path).readLines().let {
        val matrix = Array(size) { Array(size) { 0 } }
        for (y in 0 until size) {
            for (x in 0 until size) {
                when (it[y][x]) {
                    '#' -> matrix[y][x] = 1
                    '.' -> matrix[y][x] = 0
                }
            }
        }
        matrix
    }

    private fun calculateLight(m: Array<Array<Int>>, y: Int, x: Int, overrides: Collection<Pair<Int, Int>>? = null): Int {
        if (overrides != null && overrides.any { it.first == y && it.second == x}) return 1
        var sum = 0
        for (y2 in -1..1) {
            for (x2 in -1..1) {
                if (y2 == 0 && x2 == 0) continue
                val y3 = y + y2
                val x3 = x + x2
                if (y3 in 0 until size && x3 in 0 until size) {
                    sum += m[y3][x3]
                }
            }
        }
        return if ((m[y][x] == 1 && sum in 2..3) || (m[y][x] == 0 && sum == 3)) 1 else 0
    }

    private fun cp(m: Array<Array<Int>>): Array<Array<Int>> {
        val n = Array(m.size) { Array(m.size) { 0 } }
        for (y in 0 until m.size) {
            for (x in 0 until m[y].size) {
                n[y][x] = m[y][x]
            }
        }
        return n
    }

    @Test
    fun part1() {
        var matrix = cp(initialMatrix)
        for (i in 0 until steps) {
            val newMatrix = cp(matrix)
            for (y in 0 until size) {
                for (x in 0 until size) {
                    newMatrix[y][x] = calculateLight(matrix, y, x)
                }
            }
            matrix = newMatrix
        }
        println(matrix.sumBy { it.sum() })
    }

    @Test
    fun part2() {
        var matrix = cp(initialMatrix)
        val overrides = listOf(
            0 to 0,
            0 to size - 1,
            size - 1 to 0,
            size - 1 to size - 1
        )
        for (i in 0 until steps) {
            val newMatrix = cp(matrix)
            for (y in 0 until size) {
                for (x in 0 until size) {
                    newMatrix[y][x] = calculateLight(matrix, y, x, overrides)
                }
            }
            matrix = newMatrix
        }
        println(matrix.sumBy { it.sum() })
    }
}

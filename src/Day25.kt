import org.junit.jupiter.api.Test

class Day25 {
    private val row = 2981
    private val col = 3075
    private val startValue = 20151125L

    private fun next(prev: Long): Long = (prev * 252533) % 33554393
    private fun count(r: Int, c: Int): Int = (0 until r + c - 1).sum() + c

    @Test
    fun part1() {
        var code = startValue
        for (i in 0 until count(row, col) - 1) {
            code = next(code)
        }
        println(code)
    }
}

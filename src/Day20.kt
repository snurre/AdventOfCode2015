import org.junit.jupiter.api.Test

class Day20 {
    private val puzzleInput = 34000000

    private fun isPrime(n: Int): Boolean {
        return when {
            n == 2 -> true
            n < 2 || n % 2 == 0 -> false
            else -> {
                var i = 3
                while (i * i <= n) {
                    if (n % i == 0) return true
                    i += 2
                }
                return false
            }
        }
    }

    private fun calc(n: Int): List<Int> {
        val v = arrayListOf<Int>()
        for (i in 1..n / 2) {
            if (n % i == 0) v.add(i)
        }
        return v
    }

    private fun solve(m: Int, p: ((Int) -> Boolean)? = null): Int {
        val houses = IntArray(1000000)
        for (i in 1 until houses.size) {
            var count = 0
            var j = i
            while (j < houses.size) {
                houses[j] += i * m
                count++
                if (p?.invoke(count) == true) break
                j += i
            }
        }
        for (i in houses.indices) {
            if (houses[i] >= puzzleInput) {
                return i
            }
        }
        return 0
    }
    @Test
    fun part1() {
        println(solve(10))
    }

    @Test
    fun part2() {
        println(solve(11) { n -> n==50})
    }
}

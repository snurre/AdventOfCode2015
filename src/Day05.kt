import org.junit.jupiter.api.Test
import java.io.File

class Day05 {
    private val vowels = "aeiou".toCharArray().toSet()
    private val bannedPairs = setOf("ab", "cd", "pq", "xy")

    private val strings: List<String> = File(this.javaClass.getResource("05.txt").path).readLines()

    private fun countNiceStrings(rules: Collection<(String) -> Boolean>): Int =
        strings.count { rules.all { r -> r.invoke(it) } }

    @Test
    fun part1() {
        val rules = listOf<(String) -> Boolean>(
            { it.count { c -> vowels.contains(c) } >= 3 },
            {
                var hasDoubleChar = false
                for (i in 0 until it.length - 1) {
                    if (it[i] == it[i + 1]) {
                        hasDoubleChar = true
                        break
                    }
                }
                hasDoubleChar
            },
            {
                var hasBannedPair = false
                bannedPairs.forEach { p ->
                    if (it.contains(p)) {
                        hasBannedPair = true
                    }
                }
                !hasBannedPair
            }
        )
        println(countNiceStrings(rules))
    }

    @Test
    fun part2() {
        val rules = listOf<(String) -> Boolean>(
            {
                var hasPair = false
                for (i in 0 until it.length - 1) {
                    val pair = it[i] to it[i + 1]
                    for (j in i + 2 until it.length - 1) {
                        if (pair == it[j] to it[j + 1]) {
                            hasPair = true
                        }
                    }
                }
                hasPair
            },
            {
                var hasPair = false
                for (i in 0 until it.length - 2) {
                    if (it[i] == it[i + 2]) {
                        hasPair = true
                    }
                }
                hasPair
            }
        )
        println(countNiceStrings(rules))
    }

}

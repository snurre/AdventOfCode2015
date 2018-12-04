import org.junit.jupiter.api.Test

class Day11 {
    private val puzzleInput = "hxbxwxba"
    private val illegalCharacters = "0123456789iol".toCharArray().toSet()

    private val rules = listOf<(String) -> Boolean>(
        { s ->
            var hasIncrementingSequence = false
            for (i in 0 until s.length - 2) {
                if (s[i] == s[i + 1] - 1 && s[i] == s[i + 2] - 2) {
                    hasIncrementingSequence = true
                    break
                }
            }
            hasIncrementingSequence
        },
        { it.toCharArray().toSet().intersect(illegalCharacters).isEmpty() },
        { s ->
            val doubleChars = hashSetOf<Char>()
            var i = -1
            while (++i < s.length - 1) {
                if (s[i] == s[i + 1]) {
                    doubleChars.add(s[i++])
                }
            }
            doubleChars.size >= 2
        }
    )

    private fun nextPassword(input: String): String {
        val p = input.toLong(36)
        return (p + (1.."zzzzzzz".toLong(36)).find { i ->
            rules.all { it.invoke((p + i).toString(36)) }
        }!!).toString(36)
    }

    @Test
    fun part1() {
        println(nextPassword(puzzleInput))
    }

    @Test
    fun part2() {
        println(nextPassword(nextPassword(puzzleInput)))
    }

}

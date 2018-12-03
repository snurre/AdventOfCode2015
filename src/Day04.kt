import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.security.MessageDigest

class Day04 {
    private val puzzleInput = "iwrupvqb"
    private val md: MessageDigest = MessageDigest.getInstance("MD5")

    private fun String.md5(): String {
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    @Test
    fun part1() {
        println(solve("00000"))
    }

    @Test
    fun part2() {
        println(solve("000000"))
    }

    private fun solve(prefix: CharSequence): Int {
        var i = 0
        while (true) {
            val hash = (puzzleInput + i).md5()
            if (hash.startsWith(prefix)) {
                return i
            }
            i++
        }
    }
}

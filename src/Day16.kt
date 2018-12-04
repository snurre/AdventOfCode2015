import org.junit.jupiter.api.Test
import java.io.File

class Day16 {
    private val sues = File(this.javaClass.getResource("16.txt").path).useLines {
        val rx = Regex("Sue (\\d+): (\\w+): (\\d+), (\\w+): (\\d+), (\\w+): (\\d+)")
        it.map { s ->
            rx.matchEntire(s)!!.groupValues.let { v ->
                v[1].toInt() to mapOf(v[2] to v[3].toInt(), v[4] to v[5].toInt(), v[6] to v[7].toInt())
            }
        }.toMap()
    }
    private val control = mapOf(
        "children" to 3,
        "cats" to 7,
        "samoyeds" to 2,
        "pomeranians" to 3,
        "akitas" to 0,
        "vizslas" to 0,
        "goldfish" to 5,
        "trees" to 3,
        "cars" to 2,
        "perfumes" to 1
    )

    @Test
    fun part1() {
        println(sues.entries.find { sue ->
            control.all { c -> sue.value.getOrDefault(c.key, c.value) == c.value }
        })
    }

    @Test
    fun part2() {
        println(sues.entries.find { sue ->
            control.all { c ->
                val v = sue.value.getOrDefault(c.key, c.value)
                when (c.key) {
                    "cats", "trees" -> v >= c.value
                    "pomeranians", "goldfish" -> v <= c.value
                    else -> v == c.value
                }
            }
        })
    }
}

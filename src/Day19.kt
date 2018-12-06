import org.junit.jupiter.api.Test
import java.io.File

class Day19 {
    private val replacements = File(this.javaClass.getResource("19a.txt").path).readLines().map { it.split(" => ") }
    private val molecule = File(this.javaClass.getResource("19b.txt").path).readText().trim()

    private fun mutate(m: String, input: String, output: String, pos: Int): String {
        return m.substring(0, pos) + output + m.substring(pos + input.length)
    }

    @Test
    fun part1() {
        val mutations = mutableSetOf<String>()
        replacements.forEach { r ->
            var pos = 0
            while ({ pos = molecule.indexOf(r[0], pos); pos }() >= 0) {
                mutations.add(mutate(molecule, r[0], r[1], pos))
                pos += r[0].length
            }
        }
        println(mutations.size)
    }

    @Test
    fun part22() {
        var count = 0
        var m = molecule
        while (m != "e") {
            replacements.forEach { r ->
                if (r[1] in m) {
                    m = mutate(m, r[1], r[0], m.indexOf(r[1]))
                    count++
                }
            }
        }
        println(count)
    }
}

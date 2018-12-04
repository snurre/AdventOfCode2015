import org.junit.jupiter.api.Test
import java.io.File

class Day13 {
    private val data = File(this.javaClass.getResource("13.txt").path).useLines {
        it.map { s ->
            s.split(" ").let { p -> Triple(p[0], p[10].trim('.'), (if (p[2] == "lose") -1 else 1) * p[3].toInt()) }
        }.toList()
    }

    private fun permute(list: List<String>): List<List<String>> {
        if (list.size == 1) return listOf(list)
        val perms = mutableListOf<List<String>>()
        val sub = list[0]
        for (perm in permute(list.drop(1)))
            for (i in 0..perm.size) {
                val newPerm = perm.toMutableList()
                newPerm.add(i, sub)
                perms.add(newPerm)
            }
        return perms
    }

    private fun solve(relationships: List<Triple<String, String, Int>>): Int {
        val changes = hashMapOf<String, HashMap<String, Int>>()
        relationships.forEach {
            changes.computeIfAbsent(it.first) { hashMapOf() }[it.second] = it.third
        }
        val happiness = arrayListOf<Int>()
        permute(relationships.map { it.first }.union(relationships.map { it.second }).toSet().toList()).forEach { p ->
            var sum = 0
            for (i in 0 until p.size) {
                val center = p[i]
                val left = p[if (i < p.size - 1) i + 1 else 0]
                val right = p[if (i > 0) i - 1 else p.size - 1]
                sum += changes[center]!![left]!!
                sum += changes[center]!![right]!!
            }
            happiness.add(sum)
        }
        return happiness.max()!!
    }

    @Test
    fun part1() {
        println(solve(data))
    }

    @Test
    fun part2() {
        val relationships = data.toMutableList()
        data.map { it.first }.union(data.map { it.second }).toSet().forEach {
            relationships.add(Triple("me", it, 0))
            relationships.add(Triple(it, "me", 0))
        }
        println(solve(relationships))
    }
}

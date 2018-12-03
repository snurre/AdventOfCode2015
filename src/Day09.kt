import org.junit.jupiter.api.Test
import java.io.File
import java.util.*
import java.util.ArrayList

class Day09 {
    private val routes = {
        val distances = hashMapOf<String, HashMap<String, Int>>()
        File(this.javaClass.getResource("09.txt").path).forEachLine { s ->
            s.split(" ").let {
                val a = it[0]
                val b = it[2]
                val dist = it[4].toInt()
                distances.computeIfAbsent(a) { hashMapOf() }[b] = dist
                distances.computeIfAbsent(b) { hashMapOf() }[a] = dist
            }

        }
        distances
    }.invoke()

    private fun computeDistance(routes: Map<String, Map<String, Int>>, cities: List<String>): Int {
        var dist = 0
        for (i in 0 until cities.size - 1) {
            dist += routes[cities[i]]!![cities[i + 1]]!!
        }
        return dist
    }

    private fun generatePermutations(original: Collection<String>): List<List<String>> {
        if (original.isEmpty()) {
            val result = ArrayList<List<String>>()
            result.add(ArrayList())
            return result
        }
        val o2 = original.toSet().toMutableList()
        val firstElement = o2.removeAt(0)
        val returnValue = ArrayList<List<String>>()
        val permutations = generatePermutations(o2)
        for (smallerPermutated in permutations) {
            for (index in 0..smallerPermutated.size) {
                val temp = ArrayList(smallerPermutated)
                temp.add(index, firstElement)
                returnValue.add(temp)
            }
        }
        return returnValue
    }

    private fun solve(): List<Int> {
        return generatePermutations(routes.keys).map { computeDistance(routes, it) }
    }

    @Test
    fun part1() {
        println(solve().min())
    }

    @Test
    fun part2() {
        println(solve().max())
    }
}

import org.junit.jupiter.api.Test
import java.io.File

class Day14 {
    data class Reindeer(val name: String, val speed: Int, val duration: Int, val cooldown: Int) {
        fun move(time: Int): Int {
            val distancePerCycle = speed * duration
            val timePerCycle = duration + cooldown
            val cycles = time / timePerCycle
            val distanceAfterCompletedCycles = distancePerCycle * cycles
            val timeLeft = time % timePerCycle
            return distanceAfterCompletedCycles + (speed * Math.min(timeLeft, duration))
        }
    }

    private val endTime = 2503
    private val reindeers = File(this.javaClass.getResource("14.txt").path).useLines {
        it.map { s ->
            s.split(" ").let { p -> Reindeer(p[0], p[3].toInt(), p[6].toInt(), p[13].toInt()) }
        }.toList()
    }

    @Test
    fun part1() {
        println(reindeers.map { it.move(endTime) }.max())
    }

    @Test
    fun part2() {
        val scores = hashMapOf<String, Int>()
        for (t in 1..endTime) {
            val timeScore = hashMapOf<Int, ArrayList<String>>()
            reindeers.forEach {
                timeScore.computeIfAbsent(it.move(t)) { arrayListOf() }.add(it.name)
            }
            timeScore[timeScore.keys.max()]!!.forEach { n ->
                scores.computeIfAbsent(n) { 0 }
                scores[n] = scores[n]!! + 1
            }
        }
        println(scores.values.max())
    }
}

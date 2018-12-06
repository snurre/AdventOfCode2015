import org.junit.jupiter.api.Test
import kotlin.math.max

class Day21 {
    data class Item(val name: String, val cost: Int, val damage: Int, val armor: Int)
    data class Loadout(private val items: Collection<Item>) {
        val cost by lazy { items.sumBy { it.cost } }
        val damage by lazy { items.sumBy { it.damage } }
        val armor by lazy { items.sumBy { it.armor } }
    }

    private val shop = listOf(
        listOf(Item("Dagger", 8, 4, 0), Item("Shortsword", 10, 5, 0), Item("Warhammer", 25, 6, 0), Item("Longsword", 40, 7, 0), Item("Greataxe", 75, 8, 0)),
        listOf(Item("none", 0, 0, 0), Item("Leather", 13, 0, 1), Item("Chainmail", 31, 0, 2), Item("Splintmail", 53, 0, 3), Item("Bandedmail", 75, 0, 4), Item("Platemail", 102, 0, 5)),
        listOf(Item("none", 0, 0, 0), Item("Damage +1", 25, 1, 0), Item("Damage +2", 50, 2, 0), Item("Damage +3", 100, 3, 0), Item("Defense +1", 20, 0, 1), Item("Defense +2", 40, 0, 2), Item("Defense +3", 80, 0, 3))
    )
    private val loadouts: List<Loadout> = shop[0].flatMap { weapon -> shop[1].flatMap { armor -> shop[2].flatMap { ring1 -> shop[2].filter { ring2 -> ring1 != ring2 && ring1.name != "none" }.map { ring2 -> Loadout(listOf(weapon, armor, ring1, ring2)) } } } }

    private fun fight(loadout: Loadout): Pair<Boolean, Int> {
        var enemyHp = 104
        val enemyDamage = 8
        val enemyArmor = 1
        var playerHp = 100
        while (true) {
            enemyHp -= max(loadout.damage - enemyArmor, 1)
            if (enemyHp <= 0) break
            playerHp -= max(enemyDamage - loadout.armor, 1)
            if (playerHp <= 0) break
        }
        return (playerHp > 0) to loadout.cost
    }

    @Test
    fun part1() {
        println(loadouts.map { fight(it) }.filter { it.first }.minBy { it.second }!!.second)
    }

    @Test
    fun part2() {
        println(loadouts.map { fight(it) }.filter { !it.first }.maxBy { it.second }!!.second)
    }
}

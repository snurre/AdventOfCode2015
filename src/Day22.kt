import org.junit.jupiter.api.Test
import kotlin.math.max

class Day22 {
    enum class Spell(val duration: Int, val cost: Int, val effect: Int, val apply: (State) -> Unit) {
        MagicMissile(0, 53, 4, { state -> state.enemyHp -= Spell.MagicMissile.effect }),
        Drain(0, 73, 2, { state -> state.enemyHp -= Spell.Drain.effect; state.hp += Spell.Drain.effect }),
        Shield(6, 113, 7, { state -> state.shieldDuration = Spell.Shield.duration; state.armor = Spell.Shield.effect }),
        Poison(6, 173, 3, { state -> state.poisonDuration = Spell.Poison.duration }),
        Regen(5, 229, 101, { state -> state.regenDuration = Spell.Regen.duration })
    }

    private val bossdamage = 9
    private var minmana = Int.MAX_VALUE

    data class State(
        var hp: Int,
        var enemyHp: Int,
        var mana: Int,
        var armor: Int,
        var shieldDuration: Int,
        var poisonDuration: Int,
        var regenDuration: Int,
        var manaUsed: Int,
        val hardMode: Boolean,
        val spellSequence: List<Spell> = listOf()
    ) {
        fun next(spell: Spell? = null): State {
            val s = spellSequence.toMutableList()
            var mana2 = mana
            var manaUsed2 = manaUsed
            if (spell != null) {
                s.add(spell)
                mana2 -= spell.cost
                manaUsed2 += spell.cost
            }
            return State(hp, enemyHp, mana2, armor, shieldDuration, poisonDuration, regenDuration, manaUsed2, hardMode, s)
        }
    }

    private fun fight(state: State) {
        if (state.hardMode && --state.hp <= 0) return
        state.shieldDuration--
        if (state.shieldDuration == 0) state.armor = 0
        if (state.poisonDuration-- > 0) state.enemyHp -= Spell.Poison.effect
        if (state.regenDuration-- > 0) state.mana += Spell.Regen.effect
        if (state.enemyHp <= 0) {
            if (state.manaUsed < minmana) minmana = state.manaUsed
            return
        }
        loop@ for (spell in Spell.values()) {
            when {
                spell == Spell.Shield && state.shieldDuration > 0 -> continue@loop
                spell == Spell.Poison && state.poisonDuration > 0 -> continue@loop
                spell == Spell.Regen && state.regenDuration > 0 -> continue@loop
                state.mana < spell.cost -> continue@loop
                else -> enemyTurn(state.next(spell), spell)
            }
        }
    }

    private fun enemyTurn(state: State, spell: Spell) {
        if (state.manaUsed >= minmana) return
        spell.apply(state)
        state.shieldDuration--
        if (state.poisonDuration-- > 0) state.enemyHp -= Spell.Poison.effect
        if (state.regenDuration-- > 0) state.mana += Spell.Regen.effect
        if (state.enemyHp <= 0) {
            if (state.manaUsed < minmana) minmana = state.manaUsed
            return
        }
        state.hp -= max(bossdamage - state.armor, 1)
        if (state.hp > 0) fight(state.next())
    }

    @Test
    fun part1() {
        minmana = Int.MAX_VALUE
        fight(State(50, 51, 500, 0, 0, 0, 0, 0, false))
        println(minmana)
    }

    @Test
    fun part2() {
        minmana = Int.MAX_VALUE
        fight(State(50, 51, 500, 0, 0, 0, 0, 0, true))
        println(minmana)
    }
}

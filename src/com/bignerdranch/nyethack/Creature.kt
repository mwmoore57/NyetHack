package com.bignerdranch.nyethack

import com.bignerdranch.nyethack.extensions.random
import java.util.*

interface Fightable {
    var healthPoints: Int
    val diceCount: Int
    val diceSides: Int
    val damageRoll: Int
        get() = (0 until diceCount).map {
            Random().nextInt(diceSides + 1)
        }.sum()
    val rarity: Int

//    var toHitArmorClassZero: Int

    fun generateMonsterCount(): Int = (1..rarity).random()

    fun attack(opponent: Fightable): Int
}

abstract class Monster(val name: String,
                       val description: String,
                       override var healthPoints: Int,
                       override val rarity: Int) : Fightable {

    override fun attack(opponent: Fightable): Int {
        val damageDealt = damageRoll
        opponent.healthPoints -= damageDealt
        return damageDealt
    }
}

class Goblin(name: String = "Goblin",
             description: String = "nasty looking Goblins",
             healthPoints: Int = 30,
             rarity: Int = 3) : Monster(name, description, healthPoints, rarity) {
    override val diceCount = 2
    override val diceSides = 8
}

class Orc(name: String = "Orc",
          description: String = "filthy Orcs",
          healthPoints: Int = 35,
          rarity: Int = 3) : Monster(name, description, healthPoints, rarity) {
    override val diceCount = 3
    override val diceSides = 6
}

class GiantSpider(name: String = "The Giant Spider, She-Lob",
                  description: String = "A Giant Spider with ichor dripping from its mouth and many black eyes staring back at you",
                  healthPoints: Int = 75,
                  rarity: Int = 1) : Monster(name, description, healthPoints, rarity) {
    override val diceCount = 5
    override val diceSides = 8
}

class Hobgoblin(name: String = "Hobgoblin",
             description: String = "brutish looking Hobgoblins",
             healthPoints: Int = 50,
             rarity: Int = 2) : Monster(name, description, healthPoints, rarity) {
    override val diceCount = 3
    override val diceSides = 8
}

class Dragon(name: String = "Smuag, the Terrible",
             description: String = "A towering dragon covered in thick scales with a penetrating gaze",
             healthPoints: Int = 300,
             rarity: Int = 1) : Monster(name, description, healthPoints, rarity) {
    override val diceCount = 8
    override val diceSides = 12
}
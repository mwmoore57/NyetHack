package com.bignerdranch.nyethack

open class Room(val name: String) {
    protected open val dangerLevel = 5
    var monster: Monster? = Goblin()
    var monsterCount: Int = monster?.generateMonsterCount() ?: 0

    fun description() = "Room: $name\n" +
            "Danger level: $dangerLevel\n" +
            "Creature: There are $monsterCount ${monster?.description ?: "none."}"

    open fun load() = "Nothing much to see here..."
}

open class TownSquare : Room("Town Square") {
    override val dangerLevel = super.dangerLevel - 3 + 2 * monsterCount
    private var bellSound = "GWONG"

    final override fun load() = "The villagers rally and cheer as you enter!"

    private fun ringBell() = "The bell tower announces your arrival. $bellSound"
}
package com.bignerdranch.nyethack

import kotlin.system.exitProcess

fun main(args: Array<String>) {
    Game.play()
}

object Game {
    private val player1 = Player("Madrigal")
    private var currentRoom: Room = TownSquare()

    private var worldMap = listOf(
            listOf(currentRoom, Room("Tavern"), Room("Back Room")),
            listOf(Room("Long Corridor"), Room("Generic Room")))

    init {
        println("Welcome, adventurer.")
        player1.castFireball()
    }

    fun play() {
        while (true) {
            // Play NyetHack
            println(currentRoom.description())
            println(currentRoom.load())

            // Player Status
            printPlayerStatus(player1)

            print("Enter your command: ")
            println(GameInput(readLine()).processCommand())
        }
    }

    private fun move(directionInput: String) =
            try {
                val direction = Direction.valueOf(directionInput.toUpperCase())
                val newPosition = direction.updateCoordinate(player1.currentPosition)
                if (!newPosition.isInBounds) {
                    throw IllegalStateException("$direction is out of bounds.")
                }

                val newRoom = worldMap[newPosition.y][newPosition.x]
                player1.currentPosition = newPosition
                currentRoom = newRoom
                "Ok, you move $direction to the ${newRoom.name}.\n${newRoom.load()}"
            } catch(e: Exception) {
                "Invalid direction: $directionInput"
            }

    private fun printPlayerStatus(player: Player) {
        println("(Aura: ${player.auraColor()}) " + "(Blessed: ${if (player.isBlessed) "YES" else "NO"})")
        println("${player.name} ${player.formatHealthStatus()}")
    }

    private fun fight() = currentRoom.monster?.let {
        while (player1.healthPoints > 0 && it.healthPoints > 0) {
            slay(it)
            Thread.sleep(1000)
        }

        "Combat complete."
    } ?: "There's nothing here to fight."

    private fun slay(monster: Monster) {
        println("${monster.name} did ${monster.attack(player1)} damage!")
        println("${player1.name} did ${player1.attack(monster)} damage!")

        if (player1.healthPoints <= 0) {
            println(">>>> You have been defeated! Thanks for playing. <<<<")
            exitProcess(0)
        }

        if (monster.healthPoints <= 0) {
            println(">>>> ${monster.name} has been defeated! <<<<")
            currentRoom.monster = null
        }
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1, { "" })

        fun  processCommand() = when (command.toLowerCase()) {
            "quit" -> exitProcess(0)
            "flee" -> move(argument)
            "fight" -> fight()
            "move" -> move(argument)
            else -> commandNotFound()
        }

        private fun commandNotFound() = "I'm not quite sure what you are trying to do!"
    }
}



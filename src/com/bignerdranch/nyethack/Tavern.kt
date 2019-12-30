package com.bignerdranch.nyethack

import com.bignerdranch.nyethack.extensions.random
import java.io.File

const val TAVERN_NAME = "Taernyl's Folly"

val patronList = mutableListOf("Eli","Mordoc","Sophie")
val lastName = listOf("Proudfoot","Baggins","Took")
val uniquePatrons: Set<String> = generateSequence { "${patronList.random()} ${lastName.random()}"
    }.distinct().take(9).toSet()

val menuList = File("data/tavern-menu-items.txt")
        .readText()
        .split("\n")

val patronGold: MutableMap<String, Double> = uniquePatrons.map { it to 16.00 }.toMap().toMutableMap()

fun main(args: Array<String>) {
    if (patronList.contains("Eli")) {
        println("The tavern master says: Eli's in the back playing cards")
    } else {
        println("The tavern master says: Eli isn't here.")
    }

    if (patronList.containsAll(listOf("Sophie","Mordoc"))) {
        println("The tavern master says: Yes they're seated by the stew kettle.")
    } else {
        println("The tavern master says: Nay, they departed hours ago.")
    }

    println(uniquePatrons)
    displayPatronBalances()

    var orderCount = 0
    while (orderCount <= 9) {
        placeOrder(uniquePatrons.random(),
                menuList.random())
        orderCount++
    }

    displayPatronBalances()
}

private fun performPurchase(price: Double, patronName: String) {
    val totalPurse = patronGold.getValue(patronName)
    patronGold[patronName] = totalPurse - price
}

private fun displayPatronBalances() {
    patronGold.forEach{ patron, balance ->
        println("$patron, balance: ${"%.2f".format(balance)}")
    }
}

private fun toDragonSpeak(phrase: String) =
        phrase.replace(Regex("[AEIOUaeiou]")) {
            when (it.value) {
                "a" -> "4"
                "A" -> "4"
                "e" -> "3"
                "E" -> "3"
                "i" -> "1"
                "I" -> "1"
                "o" -> "0"
                "O" -> "0"
                "u" -> "|_|"
                "U" -> "|_|"
                else -> it.value
            }
        }

private fun placeOrder(patronName: String, menuData: String) {
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    println("$patronName speaks with $tavernMaster about their order.")

    val (type,name,price) = menuData.split(',')
    val message = "$patronName buys a $name ($type) for $price."
    println(message)

    performPurchase(price.toDouble(), patronName)

    val phrase = if (name == "Dragon's Breath") {
        "$patronName exclaims: ${toDragonSpeak("DRAGON'S BREATH!: IT'S GOT WHAT ADVENTURERS CRAVE!")}"
    } else {
        "$patronName says: Thanks for the $name"
    }

    println(phrase)
}
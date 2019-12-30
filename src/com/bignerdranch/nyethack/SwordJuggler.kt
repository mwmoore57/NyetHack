package com.bignerdranch.nyethack

fun main(args: Array<String>) {
    var swordsJuggling: Int? = null
    val isJugglingProficient = (1..3).shuffled().last() == 3
    if (isJugglingProficient) {
        swordsJuggling = 2
    }

    try {
        proficienyCheck(swordsJuggling)
        swordsJuggling = swordsJuggling!!.plus(1)
    } catch (e: Exception) {
        println(e)
    }

    println("You juggle $swordsJuggling swords!")
}

fun proficienyCheck(swordsJuggling: Int?) {
    swordsJuggling ?: throw UnskilledSwordJugglerException()
//    checkNotNull(swordsJuggling, {"com.bignerdranch.nyethack.Player cannot juggle swords"})
}

class UnskilledSwordJugglerException() :
        IllegalStateException("com.bignerdranch.nyethack.Player cannot juggle swords")
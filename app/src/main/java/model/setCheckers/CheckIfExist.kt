package org.example.setCheckers

import model.Card
import model.Color
import model.Figure
import org.example.Player

abstract class CheckIfExist {

    var listOfPlayers = ArrayList<Player>()
    var listOfCards = ArrayList<Card>()
    fun getAllPlayedCards(listOfPlayers: ArrayList<Player>): ArrayList<Card> {
        for (player in listOfPlayers) {
            for (card in player.hand) {
                listOfCards.add(card)
            }
        }
        return listOfCards
    }

    fun validatorFigure(answer1: String): Figure {

        val figure: Figure = if (answer1 == "9") {
            Figure.valueOf("NINE")
        } else if (answer1 == "10") {
            Figure.valueOf("TEN")
        } else {
            Figure.valueOf(answer1.uppercase())
        }
        return figure;
    }

    fun validatorColor(answer1: String): Color {


        val color: Color = Color.valueOf(answer1.uppercase())

        return color;
    }

    abstract fun check(listOfCards: ArrayList<Card>, answer1: String, answer2: String): Boolean
}
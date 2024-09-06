package org.example.setCheckers

import model.Card
import model.Color
import model.Figure
import model.Rankable
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

    abstract fun check(listOfCards: ArrayList<Card>, answer1: Rankable, answer2: Rankable): Boolean
    fun biggerOrSmallerSet(before: model.Set, new: model.Set): Int {
        var rankBefore = 0;
        var rankNew = 0;

        rankBefore = before.rank;
        rankNew = new.rank;
        if (rankBefore > rankNew) {
            return 0
        } else if (rankBefore == rankNew) {
            return 1
        } else {
            return 2
        }

    }

    fun biggerOrSmallerCard(before: Figure, new: Figure): Int {
        var rankBefore = 0;
        var rankNew = 0;
        rankBefore = before!!.rank;
        rankNew = new!!.rank;
        if (rankBefore > rankNew) {
            return 0
        } else if (rankBefore == rankNew) {
            return 1
        } else {
            return 2
        }
    }

    fun biggerOrSmallerColor(before: model.Color, new: model.Color): Int {
        var rankBefore = 0;
        var rankNew = 0;
        rankBefore = before.rank;
        rankNew = new.rank;
        if (rankBefore >= rankNew) {
            return 0
        } else {
            return 1
        }
    }
}

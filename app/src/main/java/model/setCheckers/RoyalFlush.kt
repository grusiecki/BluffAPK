package org.example.setCheckers

import model.Card
import model.Color
import model.Figure
import model.Rankable
import model.SmallOrBig

class RoyalFlush: CheckIfExist() {
    override fun check(listOfCards: ArrayList<Card>, answer1: Rankable, answer2: Rankable): Boolean {

        var contain = false
        val color: Color = answer2 as Color
        val nine  = Card(answer2, Figure.NINE,0)
        val ten = Card(answer2,Figure.TEN,0)
        val jack = Card(answer2, Figure.JACK,0)
        val queen = Card(answer2, Figure.QUEEN,0)
        val king = Card(answer2, Figure.KING,0)
        val ace = Card(answer2, Figure.ACE,0)

        if (listOfCards.contains(ten) && listOfCards.contains(jack) && listOfCards.contains(queen) && listOfCards.contains(king
            )){
                if (answer1.str == "Small") {
                    contain = listOfCards.contains(nine)
                } else if (answer1.str == "Big") {
                    contain = listOfCards.contains(ace)
                }
            }else {
            contain = false

        }
        return contain
    }
}
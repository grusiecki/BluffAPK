package org.example.setCheckers

import model.Card
import model.Color
import model.Figure

class RoyalFlush: CheckIfExist() {
    override fun check(listOfCards: ArrayList<Card>, answer1: String, answer2: String): Boolean {

        var contain = false
        val color: Color = validatorColor(answer2)
        val nine  = Card(color, Figure.NINE,0)
        val ten = Card(color,Figure.TEN,0)
        val jack = Card(color, Figure.JACK,0)
        val queen = Card(color, Figure.QUEEN,0)
        val king = Card(color, Figure.KING,0)
        val ace = Card(color, Figure.ACE,0)

        if (listOfCards.contains(ten) && listOfCards.contains(jack) && listOfCards.contains(queen) && listOfCards.contains(king
            )){
                if (answer1 == "small") {
                    contain = listOfCards.contains(nine)
                } else if (answer1 == "big") {
                    contain = listOfCards.contains(ace)
                }
            }else {
            contain = false

        }
        return contain
    }
}
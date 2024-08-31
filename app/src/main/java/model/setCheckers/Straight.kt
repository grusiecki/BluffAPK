package org.example.setCheckers

import model.Card
import model.Figure

class Straight : CheckIfExist() {
    override fun check(listOfCards: ArrayList<Card>, answer1: String, answer2: String): Boolean {
        val listOfFigures = ArrayList<Figure>()
        var contain: Boolean = false
        for (card1 in listOfCards) {
            listOfFigures.add(card1.figure)
        }
        val nine: Figure = Figure.NINE
        val ten: Figure = Figure.TEN
        val jack: Figure = Figure.JACK
        val queen: Figure = Figure.QUEEN
        val king: Figure = Figure.KING
        val ace: Figure = Figure.ACE

        if (listOfFigures.contains(ten) && listOfFigures.contains(jack) && listOfFigures.contains(queen) && listOfFigures.contains(
                king
            )
        ) {
            if (answer1 == "small") {
                contain = listOfFigures.contains(nine)
            } else if (answer1 == "big") {
                contain = listOfFigures.contains(ace)
            }
        } else {
            contain = false

        }
        return contain
    }
}
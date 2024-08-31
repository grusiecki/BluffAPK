package org.example.setCheckers

import model.Card
import model.Figure

class ThreeCards : CheckIfExist() {
    override fun check(listOfCards: ArrayList<Card>, answer1: String, answer2: String): Boolean {
        val listOfFigures = ArrayList<Figure>()
        val contain :Boolean
        for (card1 in listOfCards) {
            listOfFigures.add(card1.figure)
        }
        val figure: Figure = validatorFigure(answer1)
        val occurrences = listOfFigures.count{it == figure}
        contain = occurrences >= 3
        return contain    }
}
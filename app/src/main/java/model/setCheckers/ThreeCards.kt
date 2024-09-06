package org.example.setCheckers

import model.Card
import model.Figure
import model.Rankable

class ThreeCards : CheckIfExist() {
    override fun check(listOfCards: ArrayList<Card>, answer1: Rankable, answer2: Rankable): Boolean {
        val listOfFigures = ArrayList<Figure>()
        val contain :Boolean
        for (card1 in listOfCards) {
            listOfFigures.add(card1.figure)
        }

        val occurrences = listOfFigures.count{it == answer1}
        contain = occurrences >= 3
        return contain    }
}
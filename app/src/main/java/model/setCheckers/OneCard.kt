package org.example.setCheckers

import model.Card
import model.Figure

class OneCard : CheckIfExist() {


    override fun check(listOfCards: ArrayList<Card>, answer1: String, answer2: String): Boolean {
        val listOfFigures = ArrayList<Figure>()
        for (cards in listOfCards) {
            listOfFigures.add(cards.figure)
        }
        val figure: Figure = validatorFigure(answer1)

        val contain = listOfFigures.contains(figure)
        return contain
    }
}
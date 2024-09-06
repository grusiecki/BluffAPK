package org.example.setCheckers

import model.Card
import model.Figure
import model.Rankable

class OneCard : CheckIfExist() {


    override fun check(
        listOfCards: ArrayList<Card>,
        answer1: Rankable,
        answer2: Rankable
    ): Boolean {
        val listOfFigures = ArrayList<Figure>()
        for (cards in listOfCards) {
            listOfFigures.add(cards.figure)
        }


        return listOfFigures.contains(answer1)
    }
}
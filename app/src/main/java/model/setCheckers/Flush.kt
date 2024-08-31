package org.example.setCheckers

import model.Card
import model.Color

class Flush: CheckIfExist() {
    override fun check(listOfCards: ArrayList<Card>, answer1: String, answer2: String): Boolean {
        val listOfFigures = ArrayList<Color>()
        val contain :Boolean
        for (card1 in listOfCards) {
            listOfFigures.add(card1.color)
        }
        val color: Color = validatorColor(answer1)
        val occurrences = listOfFigures.count{it == color}
        contain = occurrences >= 5
        return contain    }
    }

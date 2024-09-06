package org.example.setCheckers

import model.Card
import model.Color
import model.Rankable

class Flush: CheckIfExist() {
    override fun check(listOfCards: ArrayList<Card>, answer1: Rankable, answer2: Rankable): Boolean {
        val listOfFigures = ArrayList<Color>()
        val contain :Boolean
        for (card1 in listOfCards) {
            listOfFigures.add(card1.color)
        }

        val occurrences = listOfFigures.count{it == answer1}
        contain = occurrences >= 5
        return contain    }
    }

package org.example.setCheckers

import model.Card
import model.Figure
import model.Rankable

class Full: CheckIfExist() {
    override fun check(listOfCards: ArrayList<Card>, answer1: Rankable, answer2: Rankable): Boolean {
        val listOfFigures = ArrayList<Figure>()
        val contain: Boolean
        for (card1 in listOfCards) {
            listOfFigures.add(card1.figure)
        }

        val occurrences1 = listOfFigures.count{it == answer1}
        val occurrences2 = listOfFigures.count{it == answer2}

        contain = if(occurrences1 >= 3 && occurrences2>=2){
            true
        }else{
            false
        }
        return contain
    }
}
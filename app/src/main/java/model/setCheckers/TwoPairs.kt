package org.example.setCheckers

import model.Card
import model.Figure

class TwoPairs: CheckIfExist() {
    override fun check(listOfCards: ArrayList<Card>, answer1: String, answer2: String): Boolean {
        val listOfFigures = ArrayList<Figure>()
        val contain: Boolean
        for (card1 in listOfCards) {
            listOfFigures.add(card1.figure)
        }
        val figure1: Figure = validatorFigure(answer1)
        val figure2: Figure = validatorFigure(answer2)
        val occurrences1 = listOfFigures.count{it == figure1}
        val occurrences2 = listOfFigures.count{it == figure2}

        contain = if(occurrences1 >= 2 && occurrences2>=2){
            true
        }else{
            false
        }
        return contain
    }
}
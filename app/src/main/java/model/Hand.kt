package model

open class Hand {

    var hand: ArrayList<Card> = arrayListOf()

    fun clear() {
        hand.clear()
    }

    fun add(card: Card) {
        hand.add(card)
    }

    fun show(): String {
        var str: String = ""
        for (c in hand) {
            str += "${c.figure.name} ${c.color.name} "
        }
        return str
    }


}
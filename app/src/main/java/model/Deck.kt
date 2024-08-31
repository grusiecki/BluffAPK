package model

import com.example.bluff.R

class Deck() : Hand() {
    val deck: ArrayList<Card> = arrayListOf(
        Card(Color.HEART, Figure.NINE, R.drawable.nine_kier),
        Card(Color.HEART, Figure.TEN, R.drawable.ten_kier),
        Card(Color.HEART, Figure.JACK, R.drawable.jack_kier),
        Card(Color.HEART, Figure.QUEEN, R.drawable.queen_kier),
        Card(Color.HEART, Figure.KING, R.drawable.king_kier),
        Card(Color.HEART, Figure.ACE, R.drawable.ace_kier),
        Card(Color.SPADE, Figure.NINE, R.drawable.nine_pik),
        Card(Color.SPADE, Figure.TEN, R.drawable.ten_pik),
        Card(Color.SPADE, Figure.JACK, R.drawable.jack_pik),
        Card(Color.SPADE, Figure.QUEEN, R.drawable.queen_pik),
        Card(Color.SPADE, Figure.KING, R.drawable.king_pik),
        Card(Color.SPADE, Figure.ACE, R.drawable.ace_pik),
        Card(Color.DIAMOND, Figure.NINE, R.drawable.nine_karo),
        Card(Color.DIAMOND, Figure.TEN, R.drawable.ten_karo),
        Card(Color.DIAMOND, Figure.JACK, R.drawable.jack_karo),
        Card(Color.DIAMOND, Figure.QUEEN, R.drawable.queen_karo),
        Card(Color.DIAMOND, Figure.KING, R.drawable.king_karo),
        Card(Color.DIAMOND, Figure.ACE, R.drawable.ace_karo),
        Card(Color.CLUB, Figure.NINE, R.drawable.nine_trefl),
        Card(Color.CLUB, Figure.TEN, R.drawable.ten_trefl),
        Card(Color.CLUB, Figure.JACK, R.drawable.jack_trefl),
        Card(Color.CLUB, Figure.QUEEN, R.drawable.queen_trefl),
        Card(Color.CLUB, Figure.KING, R.drawable.king_trefl),
        Card(Color.CLUB, Figure.ACE, R.drawable.ace_trefl),

    )
    fun create() {
        for (color in Color.entries) {
            for (figure in Figure.entries.drop(1)) {
                val c1: Card = Card(
                    color, figure, 0
                )
                add(c1);
            }
        }
    }

    fun shuffle() {
        hand.shuffle()
    }



}
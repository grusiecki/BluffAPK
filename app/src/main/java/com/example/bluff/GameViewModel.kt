package com.example.bluff

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import model.Card
import model.Deck
import org.example.Player

class GameViewModel : ViewModel() {
//    private val _playerNames = mutableStateListOf<String>()
//    val playerNames: List<String> get() = _playerNames
    private var _currentUserIndex: Int = 0
    val currentUserIndex: Int get() = _currentUserIndex
    private var _listOfPlayers = mutableStateListOf<Player>()
    var listOfPlayers: List<Player> = _listOfPlayers
    val listOfCards: ArrayList<Card> = ArrayList()




    fun setCurrentUserIndex(index: Int) {
        _currentUserIndex = index
    }
    fun setListOfPlayers(playerName: String ){

            val player = Player()
            player.active= true
            player.name = playerName
            player.numberOfCards = 1
            _listOfPlayers.add(player)
    }
     fun setCards(deck: Deck) {
        deck.deck.shuffle()

        for (player in _listOfPlayers) {
            player.clear()
            for (card in 1..player.numberOfCards) {
                player.hand.add(deck.deck[card])
                listOfCards.add(deck.deck[card])
                deck.deck.add(deck.deck[card])
                deck.deck.removeAt(0)

            }
        }

    }

}
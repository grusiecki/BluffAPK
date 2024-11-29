package com.example.bluff


import androidx.compose.runtime.mutableStateListOf

import androidx.lifecycle.ViewModel
import model.Card
import model.Deck
import model.Figure
import model.Rankable
import model.SmallOrBig
import org.example.Player

class GameViewModel : ViewModel() {
//    private val _playerNames = mutableStateListOf<String>()
//    val playerNames: List<String> get() = _playerNames
    private var _currentUserIndex: Int = 0
    val currentUserIndex get() = _currentUserIndex
    private var _previousUserIndex: Int = 0
    val previousUserIndex: Int get() = _previousUserIndex
    private var _listOfPlayers = mutableStateListOf<Player>()
    val listOfPlayers: List<Player> get() = _listOfPlayers
    var listOfCards: ArrayList<Card> = ArrayList()
    var lastSet: Rankable = model.Set.ONECARD
    var lastFirstAnswer: Rankable = model.Figure.EIGHT
    var lastSecondAnswer: Rankable = model.Figure.EIGHT
    var currentSet: Rankable = model.Set.ONECARD
    var equalSet: Boolean = false
    var setExist: Boolean = false
    var loosingPlayer: Player = Player()
    var tempFirstAnswer: Rankable = Figure.EIGHT
    var tempFirstAnswerInFlush: SmallOrBig = SmallOrBig.SMALLER

    var resetState: Boolean = true

    fun setCurrentUserIndex(index: Int) {
        _currentUserIndex = index
    }

    fun setPreviousUserIndex(index: Int) {
        _previousUserIndex = index
    }

    fun setListOfPlayers(playerName: String) {

        val player = Player()
        player.active = true
        player.name = playerName
        player.numberOfCards = 1
        _listOfPlayers.add(player)
    }

    fun removePlayer(player: Player) {
        loosingPlayer = listOfPlayers[0]
        _listOfPlayers.remove(player)
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

    fun resetData() {
        setCurrentUserIndex(0)
        setPreviousUserIndex(0)

        listOfCards = ArrayList()
        lastSet = model.Set.ONECARD
        lastFirstAnswer = model.Figure.EIGHT
        lastSecondAnswer = model.Figure.EIGHT
        currentSet = model.Set.ONECARD
        loosingPlayer = Player()
        resetState = true
    }
    fun resetListOfPlayers(){
        _listOfPlayers.clear()

    }
}
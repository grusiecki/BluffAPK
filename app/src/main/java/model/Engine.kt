package org.example

import model.Card
import org.example.setCheckers.*
import model.*

class Game() {
    private var numberOfPlayersStr = "0"
    private var numberOfPlayers = 0
    var listOfPlayers = ArrayList<Player>()
    var listOfCards = ArrayList<Card>()
    private var deck = Deck()
    var currentAnswer1 = "9"
    var currentAnswer2 = "9"
    var previousSet = "1 card"
    var previousPlayer = Player()
    var actualPlayer = Player()
    var actualLoosingPlayer = Player()
    var previousLoosingPlayer = Player()


    fun beforeFirstGame() {
        do {
            println("Enter number of players (2-4)")
            numberOfPlayersStr = readln()
            if (numberOfPlayersStr != "2" && numberOfPlayersStr != "3" && numberOfPlayersStr != "4") {
                println("Wrong quantity - select number from 2 to 4")
            }
        } while (numberOfPlayersStr != "2" && numberOfPlayersStr != "3" && numberOfPlayersStr != "4")
        deck.create()
        numberOfPlayers = numberOfPlayersStr.toInt()
    }

    fun enterName() {
        for (i in 1..numberOfPlayers) {
            val player = Player()
            println("Enter name of Player $i")
            readln().also { player.name = it }
            player.active = true
            listOfPlayers.add(player)
        }
    }

    private fun setCards() {
        deck.shuffle()
        for (player in listOfPlayers) {
            player.hand.clear()
            for (card in 1..player.numberOfCards) {
                player.add(deck.hand[card])
                listOfCards.add(deck.hand[card])
                println(deck.hand[card].figure)
                println(deck.hand[card].color)
                deck.hand.add(deck.hand[card])
                deck.hand.removeAt(0)

            }
        }

    }

    fun round() {
        var typeSet = "1 card"
        var beforeSet = "1 card"
        var previousAnswer1 = "8"
        var previousAnswer2 = "8"
        var checker = Pair(false, false)
        var check = false
        var pairAnswer: Pair<Boolean, Pair<String, String>>
        var biggerOrSmaller: Int
        while (listOfPlayers.size > 1) {
            beforeSet = "1 card"
            previousAnswer1 = "8"
            previousAnswer2 = "8"
            setCards()
            var interactions = 0
            while (!check) {


                
//


            }
            checker = Pair(false, false)
            check = false
            listOfCards.clear()
            if(previousLoosingPlayer != actualLoosingPlayer){rotatePlayers(listOfPlayers, actualLoosingPlayer)}
        }
        println("GAME OVER")


    }

    private fun falseInfo(): Pair<Boolean, Boolean> {
        println("Set cannot be smaller then previous player")
        return Pair(false, false)
    }

    private fun checkIfExist(
        typeOfSet: String,
        answer1: String,
        answer2: String,
        previousPlayer: Player,
        actualPlayer: Player,
        listOfCards: ArrayList<Card>
    ): Player {
        var exist = false
        var player = Player()
        when (typeOfSet) {
            "1 card" -> {
                val oneCard = OneCard()
                exist = oneCard.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            "pair" -> {
                val pair = OnePair()
                exist = pair.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            "two pairs" -> {
                val twoPairs = TwoPairs()
                exist = twoPairs.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            "three" -> {
                val three = ThreeCards()
                exist = three.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            "straight" -> {
                val straight = Straight()
                exist = straight.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            "full" -> {
                val full = Full()
                exist = full.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            "four" -> {
                val four = FourCards()
                exist = four.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            "flush" -> {
                val flush = Flush()
                exist = flush.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            "royal flush" -> {
                val royalFlush = RoyalFlush()
                exist = royalFlush.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }
        }
        return player
    }

    private fun whoGetsTheCard(exist: Boolean, actualPlayer: Player, previousPlayer: Player): Player {
        if (exist) {
            actualPlayer.numberOfCards++
            return actualPlayer
        } else {
            previousPlayer.numberOfCards++
            return previousPlayer
        }
    }

     fun rotatePlayers(players: ArrayList<Player>, loser: Player) {
        val loserIndex = players.indexOf(loser)
        if (loserIndex != -1) {

            val newOrder = players.subList(loserIndex, players.size) + players.subList(0, loserIndex)
            players.clear()
            players.addAll(newOrder)
        }
    }
//TODO adding test for ending game,
}
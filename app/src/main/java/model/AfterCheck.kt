package model

import org.example.Player
import org.example.setCheckers.Flush
import org.example.setCheckers.FourCards
import org.example.setCheckers.Full
import org.example.setCheckers.OneCard
import org.example.setCheckers.OnePair
import org.example.setCheckers.RoyalFlush
import org.example.setCheckers.Straight
import org.example.setCheckers.ThreeCards
import org.example.setCheckers.TwoPairs

class AfterCheck {
     fun checkIfExist(
        typeOfSet: Rankable,
        answer1: Rankable,
        answer2: Rankable,
        previousPlayer: Player,
        actualPlayer: Player,
        listOfCards: ArrayList<Card>
    ): Pair<Player, Boolean> {
        var exist = false
        var player = Player()
        when (typeOfSet) {
            Set.ONECARD -> {
                val oneCard = OneCard()
                exist = oneCard.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            Set.PAIR -> {
                val pair = OnePair()
                exist = pair.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            Set.TWOPAIRS -> {
                val twoPairs = TwoPairs()
                exist = twoPairs.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            Set.THREE -> {
                val three = ThreeCards()
                exist = three.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            Set.STRAIGHT -> {
                val straight = Straight()
                exist = straight.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            Set.FULL -> {
                val full = Full()
                exist = full.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            Set.FOUR -> {
                val four = FourCards()
                exist = four.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            Set.FLUSH -> {
                val flush = Flush()
                exist = flush.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }

            Set.ROYALFLUSH -> {
                val royalFlush = RoyalFlush()
                exist = royalFlush.check(listOfCards, answer1, answer2)
                player = whoGetsTheCard(exist, actualPlayer, previousPlayer)
            }
        }
        return Pair(player, exist)
    }

    private fun whoGetsTheCard(
        exist: Boolean,
        actualPlayer: Player,
        previousPlayer: Player
    ): Player {
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

            val newOrder =
                players.subList(loserIndex, players.size) + players.subList(0, loserIndex)
            players.clear()
            players.addAll(newOrder)
        }
    }
}
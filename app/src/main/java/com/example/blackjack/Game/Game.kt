package com.example.blackjack.Game

import com.example.blackjack.Cards.CardDeck
import com.example.blackjack.Players.Dealer
import com.example.blackjack.Players.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Game {

    private var deck = CardDeck()
    private val player = Player()
    private val dealer = Dealer()

    private fun dealtCards() {
        playerDraw()
        playerDraw()
        dealerDraw()
        dealerDraw()
    }

    suspend fun createNewGame() {
            withContext(Dispatchers.Default){

            deck.clearDeck()
            playerClearHand()
            playerClearScore()
            dealerClearHand()
            dealerClearScore()

            deck.initDeck()
            dealtCards()

            if (checkStartBust(playerGetScore(), dealerGetScore())){ createNewGame() }
        }
    }


    fun playerDraw() = player.draw(deck)
    private fun playerClearScore() = player.clearScore()
    private fun dealerClearScore() = dealer.clearScore()
    private fun dealerDraw() = dealer.draw(deck)
    fun playerGetScore() = player.getScore()
    fun dealerGetScore() = dealer.getScore()
    fun playerGetHand() = player.getHand()
    fun dealerGetHand() = dealer.getHand()
    private fun playerClearHand() = player.clearHand()
    private fun dealerClearHand() = dealer.clearHand()
    fun dealerMakeDecision() = dealer.makeDecision(deck)

    fun checkWinner(): GameResult {
        return when {
            player.getScore() == 21 && dealer.getScore() == 21 -> GameResult.TIE
            player.getScore() > 21 && dealer.getScore() > 21 -> GameResult.TIE
            player.getScore() == 21 -> GameResult.PLAYER_WIN
            dealer.getScore() == 21 -> GameResult.DEALER_WIN
            player.getScore() > 21 -> GameResult.PLAYER_BUST
            dealer.getScore() > 21 -> GameResult.DEALER_BUST
            player.getScore() > dealer.getScore() -> GameResult.PLAYER_WIN
            player.getScore() < dealer.getScore() -> GameResult.DEALER_WIN
            else -> GameResult.TIE
        }
    }

    private fun checkStartBust(plHand: Int, dlHand: Int): Boolean {
        return plHand >= 21 || dlHand >= 21
    }
}

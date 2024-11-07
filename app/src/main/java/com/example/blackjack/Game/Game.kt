package com.example.blackjack.Game

import com.example.blackjack.Cards.CardDeck
import com.example.blackjack.Players.Dealer
import com.example.blackjack.Players.Player

class Game {

     var deck = CardDeck()

     val player = Player()
     val dealer = Dealer()

    private fun dealtCards(){
        playerHit()
        playerHit()
        dealerHit()
        dealerHit()
    }
    fun createNewGame(): Game {
        val game = Game()
        game.dealtCards()

        while (checkStartBust(game.player.getScore(), game.dealer.getScore())) {
            game.deck = CardDeck()
            game.player.clearHand()
            game.dealer.clearHand()
            game.dealtCards()
        }

        return game
    }
    fun playerHit() = player.draw(deck)
    fun dealerHit() = dealer.draw(deck)
    fun dealerMakeDecision() = dealer.makeDecision(deck)
    fun checkWinner(): GameResult {
        return when {
            player.getScore() == 21 && dealer.getScore() == 21 -> GameResult.TIE
            player.getScore() == 21 -> GameResult.PLAYER_WIN
            dealer.getScore() == 21 -> GameResult.DEALER_WIN
            player.getScore() > 21 -> GameResult.PLAYER_BUST
            dealer.getScore() > 21 -> GameResult.DEALER_BUST
            player.getScore() > dealer.getScore() -> GameResult.PLAYER_WIN
            player.getScore() < dealer.getScore() -> GameResult.DEALER_WIN
            else -> GameResult.TIE
        }
    }
    private fun checkStartBust(plHand:Int, dlHand:Int): Boolean{
        return plHand >= 21 || dlHand >= 21
    }
}
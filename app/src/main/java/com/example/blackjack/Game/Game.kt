package com.example.blackjack.Game

import com.example.blackjack.Cards.CardDeck
import com.example.blackjack.Players.Dealer
import com.example.blackjack.Players.Player

class Game {

    private var playerTurn = true

     var deck = CardDeck()

     val player = Player()
     val dealer = Dealer()

    fun startGame(){
        playerHit()
        playerHit()
        dealerHit()
        dealerHit()
    }
    fun playerHit() = player.draw(deck)

    fun playerStand() = player.stand()
    fun dealerHit() = dealer.draw(deck)
    fun dealerMakeDecision() = dealer.makeDecision(deck)
    fun dealerStand() = dealer.stand()
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
    fun playerTurn(): Boolean{
        playerTurn = !playerTurn
        return playerTurn
    }

}
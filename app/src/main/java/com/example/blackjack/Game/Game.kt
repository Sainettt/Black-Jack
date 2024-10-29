package com.example.blackjack.Game

import com.example.blackjack.Cards.CardDeck
import com.example.blackjack.Players.Dealer
import com.example.blackjack.Players.Player

class Game {
    val deck = CardDeck()
    val player = Player()
    val dealer = Dealer()

    fun startGame(){
        playerHit()
        playerHit()
        dealerHit()
        dealerHit()
    }
    fun playerHit() = player.hit(deck)

    fun playerStand() = player.stand()
    fun dealerHit() = dealer.hit(deck)
    fun makeDecision() = dealer.makeDecision(deck)
    fun dealerStand() = dealer.stand()
    fun checkWinner(){
        //logic
    }

}
package com.example.blackjack.Players

import com.example.blackjack.Cards.Card
import com.example.blackjack.Cards.CardDeck

class Player : Players {
    private val playerHand = mutableListOf<Card>()
    private var playerScore = 0
    override fun hit(deck:CardDeck) {
        deck.drawCard()?.let {
            playerHand.add(it)
            playerScore += it.rank.value
        }
    }
    override fun stand(){

    }
}
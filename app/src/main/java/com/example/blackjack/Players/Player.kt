package com.example.blackjack.Players

import com.example.blackjack.Cards.Card
import com.example.blackjack.Cards.CardDeck

class Player : IPlayer {
    private val playerHand = mutableListOf<Card>()
    private var playerScore = 0
    override fun draw(deck:CardDeck) {
        deck.drawCard()?.let {
            playerHand.add(it)
            playerScore += it.rank.value
        }
    }
    override fun getScore(): Int {
        return playerScore
    }

    override fun clearHand() {
        playerHand.clear()
    }
    fun getHand(): MutableList<Card> = playerHand
}
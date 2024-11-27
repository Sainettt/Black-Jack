package com.example.blackjack.Players

import com.example.blackjack.Cards.Card
import com.example.blackjack.Cards.CardDeck
import kotlin.random.Random

class Dealer : IPlayer {
    private val dealerHand = mutableListOf<Card>()
    private var dealerScore = 0
    override fun draw(deck: CardDeck) {
        deck.drawCard()?.let {
            dealerHand.add(it)
            dealerScore += it.rank.value
        }
    }

    override fun getScore(): Int {
        return dealerScore
    }
    override fun clearHand() {
        dealerHand.clear()
    }

    override fun clearScore() {
        dealerScore = 0
    }
     fun makeDecision (deck: CardDeck){
       if (dealerScore < 11 || (dealerScore in 11 .. 14 && riskOverCard())) draw(deck)
    }
    private fun riskOverCard(): Boolean{
        val chance = Random

        return if (dealerScore <= 13) chance.nextInt(100) < 75
        else if (dealerScore <= 15) chance.nextInt(100) < 50
        else chance.nextInt(100) < 25
    }
    fun getHand(): MutableList<Card> = dealerHand
}
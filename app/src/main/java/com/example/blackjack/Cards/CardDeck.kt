package com.example.blackjack.Cards

import com.example.blackjack.R

class CardDeck {
    private val cardDeck: MutableList<Card> = mutableListOf()

    init {
        for (suit in CardSuit.values()){
            for (rank in CardRank.values()){
                 val card = Card(suit,rank)
                cardDeck.add(card)
            }
        }
        cardDeck.shuffle()
    }
     fun drawCard(): Card? = if (cardDeck.isNotEmpty()) cardDeck.removeAt(0)
     else null
}
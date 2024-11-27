package com.example.blackjack.Cards

import com.example.blackjack.R

class CardDeck {
    private val cardDeck: MutableList<Card> = mutableListOf()

    fun initDeck():MutableList<Card>{
        repeat(4){
            for (suit in CardSuit.values()){
                for (rank in CardRank.values()){
                    val card = Card(suit,rank)
                    cardDeck.add(card)
                }
            }
        }
        cardDeck.shuffle()
        return cardDeck
    }
    fun clearDeck(){
        cardDeck.clear()
    }
     fun drawCard(): Card? = if (cardDeck.isNotEmpty()) cardDeck.removeAt(0)
     else null
}
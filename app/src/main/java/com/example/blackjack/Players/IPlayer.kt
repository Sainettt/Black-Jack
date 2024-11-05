package com.example.blackjack.Players

import com.example.blackjack.Cards.CardDeck

interface IPlayer {
    fun draw(deck:CardDeck)
    fun stand()
    fun getScore():Int
}
package com.example.blackjack.Players

import com.example.blackjack.Cards.CardDeck

interface Players {
    fun hit(deck:CardDeck){}
    fun stand(){}
}
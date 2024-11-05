package com.example.blackjack.Game

enum class GameResult(state: String) {
    PLAYER_WIN("Player Win"),
    DEALER_WIN("Dealer WIN"),
    PLAYER_BUST("Player BUST"),
    DEALER_BUST("Dealer BUST"),
    TIE("TIE")
}
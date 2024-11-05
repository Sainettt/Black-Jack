package com.example.blackjack.Game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.blackjack.Cards.CardDeck
import com.example.blackjack.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    var game = Game()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        game.startGame()
        updateScores()

        with(binding){
            hitBt.setOnClickListener{
                game.playerHit()
                game.dealerMakeDecision()
                updateScores()

                if (bust()) {
                    checkGameEnd()
                    createNewGame()
                }

            }
            stayBt.setOnClickListener {
                game.playerStand()
                checkGameEnd()
                createNewGame()
            }
         }
        }
    private fun updateScores() {
        binding.plScore.text = game.player.getScore().toString()
        binding.dlScore.text = game.dealer.getScore().toString()
    }
    private fun checkGameEnd() {
        val result = game.checkWinner().name
        Toast.makeText(this@GameActivity, result, Toast.LENGTH_SHORT).show()
    }
    private fun createNewGame(){
        game = Game()
        updateScores()
    }
    private fun bust(): Boolean{
        return game.player.getScore() >= 21 || game.dealer.getScore() >= 21
    }
}
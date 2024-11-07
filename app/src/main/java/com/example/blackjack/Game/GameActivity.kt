package com.example.blackjack.Game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.blackjack.Adapter.CardAdapter
import com.example.blackjack.Cards.CardDeck
import com.example.blackjack.Fragments.GameResultFragment
import com.example.blackjack.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var playerAdapter: CardAdapter
    private lateinit var dealerAdapter: CardAdapter

    var game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)


        game = game.createNewGame()
        initAdapters()
        updateScores()

        with(binding){

            hitBt.setOnClickListener{
                game.playerHit()
                game.dealerMakeDecision()

                updateScores()
                updateRecycleViews()


                if (bust()) {
                    checkGameEnd()

                    updateScores()
                    updateRecycleViews()
                }

            }
            stayBt.setOnClickListener {
                game.dealerMakeDecision()
                checkGameEnd()

                updateScores()
                updateRecycleViews()
            }
         }
        }
    fun restartGame() {
        game = game.createNewGame()
        updateScores()
        updateRecycleViews()
    }
    private fun updateScores() {
        binding.plScore.text = game.player.getScore().toString()
        binding.dlScore.text = game.dealer.getScore().toString()
    }
    private fun checkGameEnd() {
        val result = "${game.checkWinner().name}\n" +
                "Dealer score: ${game.dealer.getScore()}\n" +
                "Player score: ${game.player.getScore()}"
        showGameResult(result)
    }

    private fun bust(): Boolean{
        return game.player.getScore() >= 21 || game.dealer.getScore() >= 21
    }
    private fun updateRecycleViews(){
        playerAdapter.updateData(game.player.getHand())
        dealerAdapter.updateData(game.dealer.getHand())
    }
    private fun initAdapters(){
        playerAdapter = CardAdapter(game.player.getHand())
        dealerAdapter = CardAdapter(game.dealer.getHand())

        binding.deckPlayer.apply {
            layoutManager = LinearLayoutManager(this@GameActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = playerAdapter
        }
        binding.deckDealer.apply {
            layoutManager = LinearLayoutManager(this@GameActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = dealerAdapter
        }
    }
    private fun showGameResult(result: String) {
        val fragment = GameResultFragment()
        val args = Bundle()
        args.putString("game_result", result)
        fragment.arguments = args
        fragment.show(supportFragmentManager, "GameResultFragment")
    }
}
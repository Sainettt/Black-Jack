package com.example.blackjack.Game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blackjack.Adapter.CardAdapter
import com.example.blackjack.Fragments.GameResultFragment
import com.example.blackjack.databinding.ActivityGameBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var playerAdapter: CardAdapter
    private lateinit var dealerAdapter: CardAdapter

    var game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapters()
        startNewGame()

        with(binding) {
            hitBt.setOnClickListener {
                game.playerHit()
                game.dealerMakeDecision()
                updateScores()
                updateRecycleViews()

                if (bust()) checkGameEnd()
            }

            stayBt.setOnClickListener {
                game.dealerMakeDecision()
                checkGameEnd()
                updateScores()
                updateRecycleViews()
            }
        }
    }

     fun startNewGame() {
        lifecycleScope.launch {
            game = withContext(Dispatchers.Default) {
                Game().createNewGame()
            }
            updateScores()
            updateRecycleViews()
        }
    }

    private fun updateScores() {
        with(binding){
            plScore.text = game.playerGetScore().toString()
            dlScore.text = game.dealerGetScore().toString()
        }
    }

    private fun checkGameEnd() {
        val result = "${game.checkWinner().name}\n" +
                "Dealer score: ${game.dealerGetScore()}\n" +
                "Player score: ${game.playerGetScore()}"
        showGameResult(result)
    }

    private fun bust(): Boolean {
        return game.playerGetScore() >= 21 || game.dealerGetScore() >= 21
    }

    private fun updateRecycleViews() {
        playerAdapter.updateData(game.playerGetHand())
        dealerAdapter.updateData(game.dealerGetHand())
    }

    private fun initAdapters() {
        playerAdapter = CardAdapter(game.playerGetHand())
        dealerAdapter = CardAdapter(game.dealerGetHand())

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

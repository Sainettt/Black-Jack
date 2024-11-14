package com.example.blackjack.Game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blackjack.Adapter.DealerCardsAdapter
import com.example.blackjack.Adapter.PlayerCardsAdapter
import com.example.blackjack.Fragments.GameResultFragment
import com.example.blackjack.databinding.ActivityGameBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var playerCardsAdapter: PlayerCardsAdapter
    private lateinit var dealerAdapter: DealerCardsAdapter

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
                if (bust()){
                    updateRecycleViewPlayer()
                    showDealerCards()
                    updateScores()
                    checkGameEnd()
                } else{
                    updateRecycleViewPlayer()
                    updateRecycleViewPDealer()
                    updateScores()
                }
            }

            stayBt.setOnClickListener {
                game.dealerMakeDecision()
                updateRecycleViewPlayer()
                showDealerCards()
                updateScores()
                checkGameEnd()
            }
        }
    }

     fun startNewGame() {
        lifecycleScope.launch {
            game = withContext(Dispatchers.Default) {
                Game().createNewGame()
            }
            updateScores()
            updateRecycleViewPlayer()
            updateRecycleViewPDealer()
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
    private fun updateRecycleViewPlayer(){
        playerCardsAdapter.updateData(game.playerGetHand())
    }
    private fun updateRecycleViewPDealer(){
        dealerAdapter.updateData(game.dealerGetHand(), false)
    }
    private fun showDealerCards(){
        dealerAdapter.updateData(game.dealerGetHand(), true)
    }

    private fun initAdapters() {
        playerCardsAdapter = PlayerCardsAdapter(game.playerGetHand())
        dealerAdapter = DealerCardsAdapter(game.dealerGetHand(), false)

        binding.deckPlayer.apply {
            layoutManager = LinearLayoutManager(this@GameActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = playerCardsAdapter
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

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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var playerCardsAdapter: PlayerCardsAdapter
    private lateinit var dealerAdapter: DealerCardsAdapter

    private var game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startNewGame()
        initAdapters()

        with(binding) {
            hitBt.setOnClickListener {
                game.playerDraw()
                game.dealerMakeDecision()
                if (bust()){
                    updateRecycleViewPlayer()
                    showDealerCards()
                    checkGameEnd()
                } else{
                    updateRecycleViewPlayer()
                    updateRecycleViewPDealer()
                }
            }

            stayBt.setOnClickListener {
                game.dealerMakeDecision()
                updateRecycleViewPlayer()
                showDealerCards()
                checkGameEnd()
            }
        }
    }

    fun startNewGame() {
            lifecycleScope.launch {
                // Выполняем создание игры на фоне
                withContext(Dispatchers.Default){
                    game.createNewGame()
                }

                // После завершения обновляем UI
                withContext(Dispatchers.Main){
                    updateTextScores()
                    updateRecycleViewPlayer()
                    updateRecycleViewPDealer()
                }
            }
    }
    private suspend fun updateTextScores() = withContext(Dispatchers.Main){
        with(binding){
            dlScore.text = game.dealerGetScore().toString()
            plScore.text = game.playerGetScore().toString()
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
        lifecycleScope.launch { playerCardsAdapter.updateData(game.playerGetHand()) }
    }
    private fun updateRecycleViewPDealer() {
        lifecycleScope.launch {
            val dealerHand = game.dealerGetHand()
            val visibilityFlags = MutableList(dealerHand.size) { it <= dealerHand.size - 2 } // Все, кроме последней карты видимы
            dealerAdapter.updateData(dealerHand, visibilityFlags)
        }
    }

    private fun showDealerCards() {
        lifecycleScope.launch {
            val dealerHand = game.dealerGetHand()
            val visibilityFlags = MutableList(dealerHand.size) { true } // Все карты видимы
            dealerAdapter.updateData(dealerHand, visibilityFlags)
        }
    }


    private fun initAdapters() {
        playerCardsAdapter = PlayerCardsAdapter(game.playerGetHand())
        dealerAdapter = DealerCardsAdapter(
            game.dealerGetHand(),
            MutableList(game.dealerGetHand().size) { it == 0 } // Первая карта видима, остальные скрыты
        )

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

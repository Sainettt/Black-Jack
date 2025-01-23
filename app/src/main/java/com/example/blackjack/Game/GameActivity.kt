package com.example.blackjack.Game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blackjack.Adapter.DealerCardsAdapter
import com.example.blackjack.Adapter.PlayerCardsAdapter
import com.example.blackjack.Fragments.ControllerButtonsListener
import com.example.blackjack.Fragments.GameResultFragment
import com.example.blackjack.Fragments.PauseFragment
import com.example.blackjack.Fragments.SettingsFragment
import com.example.blackjack.databinding.ActivityGameBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameActivity : AppCompatActivity(), ControllerButtonsListener {

    private lateinit var binding: ActivityGameBinding
    private lateinit var playerCardsAdapter: PlayerCardsAdapter
    private lateinit var dealerAdapter: DealerCardsAdapter

    private var game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            pauseButton.setOnClickListener{
                val pauseFragment = PauseFragment()
                pauseFragment.show(supportFragmentManager,"PauseFragment")
            }
            settingsButton.setOnClickListener{
                Toast.makeText(this@GameActivity, "GG", Toast.LENGTH_SHORT)
                val settingsFragment = SettingsFragment()
                supportFragmentManager.beginTransaction()
                    .replace(binding.settingsFragment.id, settingsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
        startNewGame()
        initAdapters()
    }
    override fun onHitButtonClicked() {
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
    override fun onStayButtonClicked() {
            game.dealerMakeDecision()
            updateRecycleViewPlayer()
            showDealerCards()
            checkGameEnd()
    }
    fun startNewGame() {
            lifecycleScope.launch {
                // Выполняем создание игры на фоне
                withContext(Dispatchers.Default){
                    game.createNewGame()
                }

                // После завершения обновляем UI
                withContext(Dispatchers.Main){
                    updateRecycleViewPlayer()
                    updateRecycleViewPDealer()
                }
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

package com.example.blackjack.Game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.blackjack.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    // player
    //game
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            hitBt.setOnClickListener{
                //player.hit()
            }
            stayBt.setOnClickListener {
                //player.stay
            }
        }
        //game.start
    }
}
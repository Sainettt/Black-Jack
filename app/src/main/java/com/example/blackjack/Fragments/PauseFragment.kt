package com.example.blackjack.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.blackjack.Game.GameActivity
import com.example.blackjack.R
import com.example.blackjack.databinding.FragmentControllButtonsBinding
import com.example.blackjack.databinding.FragmentPauseBinding

class PauseFragment: DialogFragment() {
    private lateinit var binding:FragmentPauseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPauseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            resumeButton.setOnClickListener {
                dismiss()
                (activity as? GameActivity)
            }
            restartButton.setOnClickListener {
                dismiss()
                (activity as? GameActivity)?.startNewGame()
            }
        }
    }
}
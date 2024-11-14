package com.example.blackjack.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.blackjack.Game.GameActivity
import com.example.blackjack.databinding.FragmentGameResultBinding


class GameResultFragment : DialogFragment() {

    private var binding: FragmentGameResultBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameResultBinding.inflate(inflater)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем аргумент с результатом игры
        val result = arguments?.getString("game_result") ?: "Tie"

        binding?.plScore?.text = result

        // Кнопка для перезапуска игры
        binding?.btRestart?.setOnClickListener {
            dismiss()
            (activity as? GameActivity)?.startNewGame()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
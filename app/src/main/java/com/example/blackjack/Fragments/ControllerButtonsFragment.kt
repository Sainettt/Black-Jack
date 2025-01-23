package com.example.blackjack.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.blackjack.databinding.FragmentControllButtonsBinding

class ControllerButtonsFragment: Fragment() {
    private lateinit var binding: FragmentControllButtonsBinding
    private var listener: ControllerButtonsListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ControllerButtonsListener){
            listener = context
        } else throw RuntimeException("$context must implement ControllerButtonsListener")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentControllButtonsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

            hitBt.setOnClickListener {
                listener?.onHitButtonClicked()
            }
            stayBt.setOnClickListener{
                listener?.onStayButtonClicked()
            }
        }
    }
}
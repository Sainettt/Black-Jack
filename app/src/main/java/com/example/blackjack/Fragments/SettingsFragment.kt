package com.example.blackjack.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.blackjack.databinding.FragmentSettingsBinding

class SettingsFragment: Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            cancelButton.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this@SettingsFragment)
                .commit()
            }
        }
    }
}
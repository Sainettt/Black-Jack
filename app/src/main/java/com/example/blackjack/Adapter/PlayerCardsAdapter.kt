package com.example.blackjack.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blackjack.Cards.Card
import com.example.blackjack.R
import com.example.blackjack.databinding.CardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerCardsAdapter(private var cardDeck: MutableList<Card>) : RecyclerView.Adapter<PlayerCardsAdapter.CardViewHolder>() {

     inner class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
         val binding = CardBinding.bind(itemView)
        fun bind (card: Card) = with(binding){
            cardSuitIconTopLeft.setImageResource(card.suit.icon)
            cardSuitIconBottomRight.setImageResource(card.suit.icon)
            cardRankText.text = setTextCard(card)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cardDeck[position])
    }

    override fun getItemCount(): Int = cardDeck.size
    private fun setTextCard(card:Card): String =
        when(card.rank.value.toString()){
            "1" -> "A"
            "11" -> "J"
            "12" -> "Q"
            "13" -> "K"
            else -> card.rank.value.toString()
        }
    suspend fun updateData(newDeck: MutableList<Card>) =
        withContext(Dispatchers.Main){
        cardDeck = newDeck
        notifyDataSetChanged()
    }
}
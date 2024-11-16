package com.example.blackjack.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.blackjack.Cards.Card
import com.example.blackjack.R
import com.example.blackjack.databinding.CardBinding

class DealerCardsAdapter(
    private var cardDeck: MutableList<Card>,
    private var isDealerCardsVisible: Boolean = false
) : RecyclerView.Adapter<DealerCardsAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CardBinding.bind(itemView)

        fun bind(card: Card, position: Int) = with(binding) {
            if (isDealerCardsVisible || position == 0) {

                cardSuitIconTopLeft.setImageResource(card.suit.icon)
                cardSuitIconBottomRight.setImageResource(card.suit.icon)
                cardRankText.text = setTextCard(card)
                cardSuitIconTopLeft.isInvisible = false
                cardSuitIconBottomRight.isInvisible = false
            } else {

                cardSuitIconTopLeft.isInvisible = true
                cardSuitIconBottomRight.isInvisible = true
                cardRankText.text = ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cardDeck[position], position)
    }

    override fun getItemCount(): Int = cardDeck.size

    private fun setTextCard(card: Card): String =
        when (card.rank.value.toString()) {
            "1" -> "A"
            "11" -> "J"
            "12" -> "Q"
            "13" -> "K"
            else -> card.rank.value.toString()
        }

    fun updateData(newDeck: MutableList<Card>, showAllCards: Boolean) {
        cardDeck = newDeck
        isDealerCardsVisible = showAllCards
        notifyDataSetChanged()
    }
}

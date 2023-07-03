package com.github.amvito.cards.cards.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.amvito.cards.R
import com.github.amvito.cards.core.Update
import com.google.android.material.textview.MaterialTextView

class CardsAdapter(
    private val onItemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<CardsAdapter.CardsViewHolder>(), Update<List<CardUi>> {

    private var list = mutableListOf<CardUi>()

    override fun update(data: List<CardUi>) {
        val diffUtil = CardsDiffUtil(list, data)
        val result = DiffUtil.calculateDiff(diffUtil)
        list.clear()
        list.addAll(data)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        return CardsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class CardsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: MaterialTextView = itemView.findViewById(R.id.ownerTextView)
        private val mapper = CardUi.Mapper.MapItem(textView)

        fun bind(item: CardUi) {
            item.map(mapper)

            itemView.setOnClickListener {
                onItemClickListener.onClick(item)
            }
        }
    }
}

class CardsDiffUtil(
    private val oldList: List<CardUi>,
    private val newList: List<CardUi>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].map(CardUi.Mapper.Compare(newList[newItemPosition]))
    }
}
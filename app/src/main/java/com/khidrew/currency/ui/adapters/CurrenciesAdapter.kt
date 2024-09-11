package com.khidrew.currency.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.khidrew.currency.R
import com.khidrew.currency.databinding.ItemSymbolBinding
import com.khidrew.domain.entities.CurrencyModel

class CurrenciesAdapter(
    private val items: List<CurrencyModel>,
    private val onItemClick: (CurrencyModel) -> Unit
) :
    RecyclerView.Adapter<CurrenciesAdapter.SymbolViewHolder>() {
    private var filteredItems: List<CurrencyModel> = items.toList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymbolViewHolder {

        val binding: ItemSymbolBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_symbol,
            parent,
            false
        )

        return SymbolViewHolder(binding)
    }

    fun filter(query: String) {
        filteredItems = if (query.isEmpty()) {
            items
        } else {
            val filteredList = items.filter {
                it.symbol.contains(query, ignoreCase = true)
            }
            filteredList
        }


        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = filteredItems.size

    override fun onBindViewHolder(holder: SymbolViewHolder, position: Int) {
        holder.onBind(filteredItems[position], onItemClick)
    }


    class SymbolViewHolder(private val binding: ItemSymbolBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CurrencyModel, onItemClick: (CurrencyModel) -> Unit) {
            binding.tvName.text = item.symbol
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }

    }

}
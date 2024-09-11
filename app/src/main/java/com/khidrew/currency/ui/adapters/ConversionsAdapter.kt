package com.khidrew.currency.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.khidrew.currency.R
import com.khidrew.currency.databinding.ItemConversionBinding
import com.khidrew.currency.databinding.ItemDateHeaderBinding
import com.khidrew.currency.models.ConversionListItem

class ConversionsAdapter(
    private val items: List<ConversionListItem>,
    private val onItemClick: (ConversionListItem.ConversionItem) -> Unit
) :
    RecyclerView.Adapter<BaseViewHolder>() {
    companion object {
        private const val VIEW_TYPE_DATE_HEADER = 0
        private const val VIEW_TYPE_CONVERSION_ITEM = 1
    }
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ConversionListItem.DateHeader -> VIEW_TYPE_DATE_HEADER
            is ConversionListItem.ConversionItem -> VIEW_TYPE_CONVERSION_ITEM
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATE_HEADER -> {
                val binding: ItemDateHeaderBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_date_header,
                    parent,
                    false
                )
                DateHeaderViewHolder(binding)
            }

            VIEW_TYPE_CONVERSION_ITEM -> {
                val binding: ItemConversionBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_conversion,
                    parent,
                    false
                )
                ConversionItemViewHolder(binding, onItemClick)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
       holder.onBind(items[position])
    }




    class DateHeaderViewHolder(private val binding: ItemDateHeaderBinding) : BaseViewHolder(binding.root) {
        override fun onBind(item: Any) {
            if(item is ConversionListItem.DateHeader){
                binding.tvDate.text = item.date
            }
        }

    }

    class ConversionItemViewHolder(
        private val binding: ItemConversionBinding,
        private val onItemClick: (ConversionListItem.ConversionItem) -> Unit
    ) : BaseViewHolder(binding.root) {
        override fun onBind(item: Any) {
            if(item is ConversionListItem.ConversionItem){
                binding.root.setOnClickListener { onItemClick(item) }
                binding.tvFromSymbol.text = item.conversion.from.symbol
                binding.tvToSymbol.text = item.conversion.to.symbol
                binding.tvFromAmount.text = "${item.conversion.fromAmount}"
                binding.tvToAmount.text = "${item.conversion.toAmount}"
            }
        }


    }
}
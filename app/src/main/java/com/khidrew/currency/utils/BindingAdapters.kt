package com.khidrew.currency.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khidrew.currency.models.ConversionListItem
import com.khidrew.currency.ui.adapters.ConversionsAdapter

@BindingAdapter("dataList")
fun applyDataList(rv: RecyclerView, data: List<ConversionListItem>) {
    rv.adapter = ConversionsAdapter(data)


}
package com.khidrew.currency.utils

import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khidrew.currency.models.ConversionListItem
import com.khidrew.currency.ui.adapters.ConversionsAdapter

@BindingAdapter("conversionsList")
fun applyDataList(rv: RecyclerView, data: List<ConversionListItem>) {
    rv.adapter = ConversionsAdapter(data)


}

@BindingAdapter("listener")
fun applyClick(tv: TextView, clickListener: OnClickListener) {
    tv.setOnClickListener(clickListener)


}

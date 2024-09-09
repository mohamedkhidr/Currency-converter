package com.khidrew.currency.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(position:Int)
}
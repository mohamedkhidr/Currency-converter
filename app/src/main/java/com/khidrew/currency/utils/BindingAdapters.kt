package com.khidrew.currency.utils

import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khidrew.currency.R
import com.khidrew.currency.models.ConversionListItem
import com.khidrew.currency.ui.adapters.ConversionsAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@BindingAdapter("listener")
fun applyClick(tv: TextView, clickListener: OnClickListener) {
    tv.setOnClickListener(clickListener)
}

@BindingAdapter("text")
fun handleNullString(tv: TextView, value:String?) {
    tv.text = value?:tv.context.resources.getString(R.string.select_currency)

}

package com.khidrew.currency.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khidrew.currency.models.ConversionListItem
import com.khidrew.data.utils.TimeUtils.formatDateToBeReadable
import com.khidrew.domain.entities.ConversionModel
import com.khidrew.domain.usecases.GetConversionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val getConversionsUseCase: GetConversionsUseCase) :
    ViewModel() {
    private val _conversions: MutableStateFlow<List<ConversionListItem>> =
        MutableStateFlow(emptyList())
    val conversions = _conversions.asStateFlow()
    private fun getAllConversions() {
        viewModelScope.launch(Dispatchers.IO) {
            _conversions.value = groupItemsByDate(getConversionsUseCase.invoke())
        }
    }

    init {
        getAllConversions()
    }

    private fun groupItemsByDate(conversions: List<ConversionModel>): List<ConversionListItem> {
        val groupedItems = mutableListOf<ConversionListItem>()
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        var lastDate: String? = null

        conversions.forEach { conversion ->
            val currentDate = formatDateToBeReadable(conversion.timeStamp, dateFormat)

            if (currentDate != lastDate) {
                groupedItems.add(ConversionListItem.DateHeader(currentDate))
                lastDate = currentDate
            }

            groupedItems.add(ConversionListItem.ConversionItem(conversion))
        }

        return groupedItems
    }

}
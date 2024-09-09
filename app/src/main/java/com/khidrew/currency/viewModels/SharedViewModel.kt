package com.khidrew.currency.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow


class SharedViewModel : ViewModel() {
    val fromCurrency: MutableStateFlow<String> = MutableStateFlow("AED")

    val fromAmount: MutableStateFlow<String> = MutableStateFlow("20")

    val toCurrency: MutableStateFlow<String> = MutableStateFlow("EGP")
    val toAmount: MutableStateFlow<String> = MutableStateFlow("3450")


    fun swapCurrencies() {
        val fromCurrencyValue = fromCurrency.value
        val toCurrencyValue = toCurrency.value
        val fromAmountValue = fromAmount.value
        val toAmountValue = toAmount.value
        fromCurrency.value = toCurrencyValue
        toCurrency.value = fromCurrencyValue
        fromAmount.value = toAmountValue
        toAmount.value = fromAmountValue
    }
}
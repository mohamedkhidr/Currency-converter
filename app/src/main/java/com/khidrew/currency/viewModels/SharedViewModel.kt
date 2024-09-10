package com.khidrew.currency.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khidrew.domain.entities.ConversionModel
import com.khidrew.domain.usecases.GetLatestConversion
import com.khidrew.domain.usecases.InsertOrUpdateConversion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getLatestConversionUseCase: GetLatestConversion,
    private val insertOrUpdateConversion: InsertOrUpdateConversion
) :
    ViewModel() {
    val fromCurrency: MutableStateFlow<String> = MutableStateFlow("")

    val fromAmount: MutableStateFlow<String> = MutableStateFlow("")

    val toCurrency: MutableStateFlow<String> = MutableStateFlow("")
    val toAmount: MutableStateFlow<String> = MutableStateFlow("")


    private fun observeAllFields() {
        viewModelScope.launch {
            combine(
                fromCurrency,
                toCurrency,
                fromAmount,
                toAmount
            ) { fromCurrencyValue, toCurrencyValue, fromAmountValue, toAmountValue ->
                if(fromCurrencyValue.isEmpty() || toCurrencyValue.isEmpty() ||
                    fromCurrencyValue == toCurrencyValue || fromAmountValue == toAmountValue){
                    null
                }else {
                    ConversionModel(
                        from = fromCurrencyValue,
                        to = toCurrencyValue,
                        fromAmount = fromAmountValue.toDoubleOrNull() ?: 0.0,
                        toAmount = toAmountValue.toDoubleOrNull() ?: 0.0,
                        timeStamp = System.currentTimeMillis()
                    )
                }
            }
                .distinctUntilChanged()
                .debounce(2000)
                .onEach {model->
                    model?.let { conversion -> updateDB(conversion) }
                }
                .flowOn(Dispatchers.IO)
                .launchIn(this)
        }
    }

    private fun updateDB(model: ConversionModel) {
        viewModelScope.launch(Dispatchers.IO) {
            insertOrUpdateConversion.invoke(model)
        }
    }

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

    fun getLatestConversion() {
        viewModelScope.launch(Dispatchers.IO) {
            updateWithConversion(getLatestConversionUseCase.invoke())
            observeAllFields()
        }
    }

    private fun updateWithConversion(conversion: ConversionModel) {
        fromCurrency.value = conversion.from
        toCurrency.value = conversion.to
        fromAmount.value = "${conversion.fromAmount}"
        toAmount.value = "${conversion.toAmount}"
    }

}
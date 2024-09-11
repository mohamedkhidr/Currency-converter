package com.khidrew.currency.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khidrew.domain.entities.ConversionModel
import com.khidrew.domain.entities.CurrencyModel
import com.khidrew.domain.usecases.GetLatestConversionUseCase
import com.khidrew.domain.usecases.InsertOrUpdateConversionUseCase
import com.khidrew.domain.usecases.SyncLatestRatesUseCase
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
    private val getLatestConversionUseCase: GetLatestConversionUseCase,
    private val insertOrUpdateConversionUseCase: InsertOrUpdateConversionUseCase,
    private val syncLatestRatesUseCase: SyncLatestRatesUseCase,
) :
    ViewModel() {
    val fromCurrency: MutableStateFlow<CurrencyModel?> = MutableStateFlow(null)

    val toCurrency: MutableStateFlow<CurrencyModel?> = MutableStateFlow(null)

    val fromAmount: MutableStateFlow<String> = MutableStateFlow("0.0")
    val toAmount: MutableStateFlow<String> = MutableStateFlow("0.0")


    init {
        getLatestRates()
        getLatestConversion()
    }

    private fun observeAllFields() {
        viewModelScope.launch {
            combine(
                fromCurrency,
                toCurrency,
                fromAmount,
                toAmount
            ) { fromCurrencyValue, toCurrencyValue, fromAmountValue, toAmountValue ->
                if (fromCurrencyValue == null ||
                    toCurrencyValue == null ||
                    fromCurrencyValue == toCurrencyValue ||
                    fromAmountValue == toAmountValue
                ) {
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
            insertOrUpdateConversionUseCase.invoke(model)
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

    private fun getLatestConversion() {
        viewModelScope.launch(Dispatchers.IO) {
            val conv = getLatestConversionUseCase.invoke()
            conv?.let {
                updateWithConversion(it)
                observeAllFields()
            }

        }
    }

    private fun getLatestRates() {
        viewModelScope.launch(Dispatchers.IO) {
            syncLatestRatesUseCase.invoke()
        }
    }



    private fun updateWithConversion(conversion: ConversionModel) {
        fromCurrency.value = conversion.from
        toCurrency.value = conversion.to
        fromAmount.value = "${conversion.fromAmount}"
        toAmount.value = "${conversion.toAmount}"
    }

}
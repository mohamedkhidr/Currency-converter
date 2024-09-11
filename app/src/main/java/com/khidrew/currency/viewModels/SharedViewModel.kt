package com.khidrew.currency.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khidrew.currency.utils.CurrencyUtils
import com.khidrew.domain.apiStates.ApiStates
import com.khidrew.domain.entities.ConversionModel
import com.khidrew.domain.entities.CurrencyModel
import com.khidrew.domain.usecases.GetConversionBySymbols
import com.khidrew.domain.usecases.GetLatestConversionUseCase
import com.khidrew.domain.usecases.InsertOrUpdateConversionUseCase
import com.khidrew.domain.usecases.SyncLatestRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    private val getConversionBySymbols: GetConversionBySymbols
) :
    ViewModel() {
    val fromCurrency: MutableStateFlow<CurrencyModel?> = MutableStateFlow(null)

    val toCurrency: MutableStateFlow<CurrencyModel?> = MutableStateFlow(null)

    val fromAmount: MutableStateFlow<String> = MutableStateFlow("1.0")
    val toAmount: MutableStateFlow<String> = MutableStateFlow("0.0")

    private val _errorMassager: MutableSharedFlow<Throwable?> = MutableSharedFlow()
    val errorMassager = _errorMassager.asSharedFlow()
    val networkIndicator: MutableSharedFlow<Boolean> = MutableSharedFlow()


    init {
        getLatestRates()
        getLatestConversion()
        observeAllFields()
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
                        fromAmount = fromAmountValue.toDoubleOrNull() ?: 1.0,
                        toAmount = toAmountValue.toDoubleOrNull() ?: 0.0,
                        timeStamp = System.currentTimeMillis()
                    )
                }
            }
                .distinctUntilChanged()
                .onEach { model ->
                    model?.let { conversion ->
                        toAmount.value = String.format(
                            "%.2f",
                            CurrencyUtils.convertFromToCurrency(
                                fromAmount.value.toDoubleOrNull() ?: 1.0,
                                conversion.from.price,
                                conversion.to.price
                            )
                        )
                    }
                }
                .debounce(2000)
                .onEach { model ->
                    model?.let {
                        updateDB(it)
                    }
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
            }

        }
    }

    fun getLatestRates() {
        viewModelScope.launch(Dispatchers.IO) {
            handleApiResult(syncLatestRatesUseCase.invoke())
        }
    }

    private fun handleApiResult(apiState: ApiStates) {
        if (apiState is ApiStates.Failure) {
            viewModelScope.launch {
                _errorMassager.emit(apiState.error)
            }

        }
    }


    private fun updateWithConversion(conversion: ConversionModel) {
        fromCurrency.value = conversion.from
        toCurrency.value = conversion.to
        fromAmount.value = "${conversion.fromAmount}"
        toAmount.value = "${conversion.toAmount}"
    }

    fun getConversionBySymbols(fromSymbol: String, toSymbol: String) {
        viewModelScope.launch {
            val conv = getConversionBySymbols.invoke(fromSymbol, toSymbol)
            conv?.let {
                updateWithConversion(it)
            }
        }
    }

}
package com.khidrew.currency.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khidrew.domain.entities.CurrencyModel
import com.khidrew.domain.usecases.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(private val getCurrenciesUseCase: GetCurrenciesUseCase) :
    ViewModel() {
    private val _currencies: MutableStateFlow<List<CurrencyModel>> = MutableStateFlow(emptyList())
    val currencies = _currencies.asStateFlow()
    fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            _currencies.value = getCurrenciesUseCase.invoke()
        }
    }
}
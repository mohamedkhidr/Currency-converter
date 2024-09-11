package com.khidrew.currency.utils

object CurrencyUtils {

    // We are using EUR as our base as its the default base of Fixer API
    fun convertFromToCurrency(
        amount: Double,
        firstCurrencyPriceInEuros: Double,
        secondCurrencyPriceInEuros: Double,
    ): Double {
        if (firstCurrencyPriceInEuros == 0.0 || secondCurrencyPriceInEuros == 0.0) throw IllegalArgumentException(
            "Currency price cannot be zero."
        )
        return String.format(
            "%.2f",
            (amount / firstCurrencyPriceInEuros) * secondCurrencyPriceInEuros
        ).toDouble()
    }
}
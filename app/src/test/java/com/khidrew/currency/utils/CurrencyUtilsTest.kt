package com.khidrew.currency.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class CurrencyUtilsTest(
    private val amount: Double,
    private val firstCurrencyPrice: Double,
    private val secondCurrencyPrice: Double,
    private val resultAmount:Double
) {


    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(1, 2.1, 0.53, 0.25),
            arrayOf(0.25, 0.53, 2.1 ,0.99),
        )
    }

    @Test
    fun `conversion is working fine`() {
        val result = CurrencyUtils.convertFromToCurrency(
            amount, firstCurrencyPrice, secondCurrencyPrice
        )
        assertThat(result).isEqualTo(resultAmount)
    }
}
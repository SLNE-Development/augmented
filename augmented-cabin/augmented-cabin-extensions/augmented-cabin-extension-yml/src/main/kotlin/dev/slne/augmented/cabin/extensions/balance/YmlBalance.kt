package dev.slne.augmented.cabin.extensions.balance

import dev.slne.augmented.cabin.api.balance.Balance
import dev.slne.augmented.cabin.api.currency.currencyProvider
import kotlinx.serialization.Serializable

@Serializable
data class YmlBalance(
    val currency: String,
    var balance: Double
) {
    fun toBalance(): Balance {
        val currencyObject =
            currencyProvider.getCurrency(currency) ?: error("Currency $currency not found")

        return Balance(balance, currencyObject)
    }
}

fun Balance.toYmlBalance(): YmlBalance {
    return YmlBalance(currency.name, amount)
}
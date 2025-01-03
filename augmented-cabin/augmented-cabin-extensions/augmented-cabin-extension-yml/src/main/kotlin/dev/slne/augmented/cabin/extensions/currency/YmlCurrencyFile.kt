package dev.slne.augmented.cabin.extensions.currency

import kotlinx.serialization.Serializable

@Serializable
data class YmlCurrencyFile(
    val currencies: List<YmlCurrency>
)

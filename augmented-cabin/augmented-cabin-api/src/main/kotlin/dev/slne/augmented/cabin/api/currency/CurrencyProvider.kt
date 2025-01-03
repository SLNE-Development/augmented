package dev.slne.augmented.cabin.api.currency

import dev.slne.augmented.cabin.api.internalCabinInstance
import it.unimi.dsi.fastutil.objects.ObjectSet

interface CurrencyProvider {

    fun getDefaultCurrency(): Currency?
    fun getCurrency(currencyName: String): Currency?
    suspend fun fetchCurrencies(): ObjectSet<Currency>

}

val currencyProvider: CurrencyProvider
    get() = internalCabinInstance.activeCabin.currencyProvider
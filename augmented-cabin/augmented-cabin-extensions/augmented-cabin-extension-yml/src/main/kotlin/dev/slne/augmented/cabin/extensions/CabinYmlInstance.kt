package dev.slne.augmented.cabin.extensions

import dev.slne.augmented.cabin.api.CabinInstance
import dev.slne.augmented.cabin.api.balance.BalanceProvider
import dev.slne.augmented.cabin.extensions.currency.YmlCurrencyProvider

class CabinYmlInstance : CabinInstance {
    override val currencyProvider = YmlCurrencyProvider
    override val balanceProvider: BalanceProvider
        get() = TODO("Not yet implemented")
}
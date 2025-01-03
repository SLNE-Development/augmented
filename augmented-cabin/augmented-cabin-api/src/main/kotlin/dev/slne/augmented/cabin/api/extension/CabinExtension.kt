package dev.slne.augmented.cabin.api.extension

import dev.slne.augmented.cabin.api.balance.BalanceProvider
import dev.slne.augmented.cabin.api.currency.CurrencyProvider
import dev.slne.augmented.cabin.api.internalCabinInstance
import it.unimi.dsi.fastutil.objects.ObjectList
import net.kyori.adventure.text.Component

abstract class CabinExtension {

    abstract val name: String
    abstract val displayName: Component

    abstract val authors: ObjectList<String>
    abstract val currencyProvider: CurrencyProvider
    abstract val balanceProvider: BalanceProvider

    open suspend fun onLoad() {}
    open suspend fun onEnable() {}
    open suspend fun onDisable() {}

    fun getDataPath() = internalCabinInstance.dataPath.resolve(name)

}
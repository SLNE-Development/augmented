package dev.slne.augmented.cabin.extensions

import dev.slne.augmented.cabin.api.balance.BalanceProvider
import dev.slne.augmented.cabin.api.extension.Cabin
import dev.slne.augmented.cabin.api.extension.CabinExtension
import dev.slne.augmented.cabin.api.extension.CabinExtensionManager
import dev.slne.augmented.cabin.extensions.currency.YmlCurrencyProvider
import dev.slne.augmented.common.base.core.extensions.objectListOf
import net.kyori.adventure.text.Component

val cabinYmlExtension: CabinYmlExtension
    get() = CabinExtensionManager["cabin-yml"] as CabinYmlExtension

@Cabin
class CabinYmlExtension : CabinExtension() {

    override val name = "cabin-yml"
    override val displayName = Component.text("Cabin YML")
    override val authors = objectListOf("SLNE Development", "Ammo")
    override val currencyProvider = YmlCurrencyProvider
    override val balanceProvider: BalanceProvider
        get() = TODO("Not yet implemented")

}
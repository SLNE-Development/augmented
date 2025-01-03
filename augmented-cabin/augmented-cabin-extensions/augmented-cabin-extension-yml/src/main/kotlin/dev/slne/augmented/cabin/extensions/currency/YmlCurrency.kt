package dev.slne.augmented.cabin.extensions.currency

import dev.slne.augmented.cabin.api.currency.Currency
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component

@Serializable
class YmlCurrency(
    override val name: String,
    override val displayName: Component,
    override val decimalPlaces: Int,
    override val symbol: String,
    override val symbolDisplay: Component,
    override val symbolPlacement: Currency.SymbolPlacement,
    override val defaultCurrency: Boolean,
) : Currency
package dev.slne.augmented.cabin.api.currency

import dev.slne.augmented.common.base.core.serializers.ComponentKSerializer
import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component

interface Currency {

    val name: String

    @Serializable(with = ComponentKSerializer::class)
    val displayName: Component
    val defaultCurrency: Boolean

    val symbolPlacement: SymbolPlacement
    val symbol: String

    @Serializable(with = ComponentKSerializer::class)
    val symbolDisplay: Component

    val decimalPlaces: Int

    @Serializable
    enum class SymbolPlacement {
        BEFORE,
        AFTER
    }

}
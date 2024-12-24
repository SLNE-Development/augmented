package dev.slne.augmented.shop.core

import dev.slne.augmented.common.base.core.extensions.requiredService
import net.kyori.adventure.text.Component
import java.util.*

interface ShopInstance {

    fun getPlayerDisplayName(uuid: UUID): Component

    companion object {
        val instance = requiredService<ShopInstance>()
    }

}

val shopInstance: ShopInstance
    get() = ShopInstance.instance
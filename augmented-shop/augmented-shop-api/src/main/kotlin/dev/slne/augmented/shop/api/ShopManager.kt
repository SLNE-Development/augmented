package dev.slne.augmented.shop.api

import dev.slne.augmented.common.base.core.extensions.requiredService
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.jetbrains.annotations.Unmodifiable

interface ShopManager {

    val isFetched: Boolean

    val shops: @Unmodifiable ObjectSet<Shop>

    suspend fun fetchShops(): @Unmodifiable ObjectSet<Shop>

    fun addShop(shop: Shop): Boolean
    fun removeShop(shop: Shop): Boolean

    companion object {
        val instance = requiredService<ShopManager>()
    }

}

val shopManager: ShopManager
    get() = ShopManager.instance
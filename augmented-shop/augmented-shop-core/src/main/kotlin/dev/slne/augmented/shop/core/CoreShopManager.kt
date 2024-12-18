package dev.slne.augmented.shop.core

import com.google.auto.service.AutoService
import dev.slne.augmented.common.base.core.extensions.freeze
import dev.slne.augmented.common.base.core.extensions.mutableObjectSetOf
import dev.slne.augmented.common.base.core.extensions.synchronize
import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.api.ShopManager
import dev.slne.augmented.shop.core.service.ShopService
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.util.Services

@AutoService(ShopManager::class)
class CoreShopManager : ShopManager, Services.Fallback {

    override var isFetched: Boolean = false
    override val shops: ObjectSet<Shop> get() = _shops.freeze()
    private val _shops = mutableObjectSetOf<Shop>().synchronize()

    override fun addShop(shop: Shop): Shop {
        _shops.add(shop)

        return shop
    }

    override fun removeShop(shop: Shop): Shop {
        _shops.remove(shop)

        return shop
    }

    override suspend fun saveShop(shop: Shop) = ShopService.saveShop(addShop(shop))
    override suspend fun deleteShop(shop: Shop) = ShopService.deleteShop(removeShop(shop))

    override suspend fun fetchShops(): ObjectSet<Shop> {
        isFetched = false

        _shops.clear()
        _shops.addAll(ShopService.findAllShops())

        isFetched = true

        return shops
    }
}
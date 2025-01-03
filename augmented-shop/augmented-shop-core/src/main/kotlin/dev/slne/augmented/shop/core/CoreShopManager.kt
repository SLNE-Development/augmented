package dev.slne.augmented.shop.core

import com.google.auto.service.AutoService
import dev.slne.augmented.common.base.core.block.BlockPosition
import dev.slne.augmented.common.base.core.extensions.freeze
import dev.slne.augmented.common.base.core.extensions.mutableObjectSetOf
import dev.slne.augmented.common.base.core.extensions.synchronize
import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.api.ShopManager
import dev.slne.augmented.shop.core.service.ShopService
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.util.Services
import java.util.*

@AutoService(ShopManager::class)
class CoreShopManager : ShopManager, Services.Fallback {

    override var isFetched: Boolean = false
    override val shops: ObjectSet<Shop> get() = _shops.freeze()
    private val _shops = mutableObjectSetOf<Shop>().synchronize()

    private val locationLocks = mutableObjectSetOf<Pair<UUID, BlockPosition>>().synchronize()

    fun isLocationLocked(world: UUID, location: BlockPosition) =
        locationLocks.contains(world to location)

    suspend fun withLocationLock(shop: Shop, block: suspend () -> Unit): Boolean {
        val pair = shop.world!! to shop.location!!

        if (!locationLocks.add(pair)) {
            return false
        }

        block()
        locationLocks.remove(pair)

        return true
    }

    override fun addShop(shop: Shop): Shop {
        _shops.add(shop)

        return shop
    }

    override fun removeShop(shop: Shop): Shop {
        _shops.remove(shop)

        return shop
    }

    override suspend fun saveShop(shop: Shop) = withLocationLock(shop) {
        addShop(ShopService.saveShop(shop))
    }

    override suspend fun deleteShop(shop: Shop) = withLocationLock(shop) {
        removeShop(ShopService.deleteShop(shop))
    }

    override suspend fun fetchShops(): ObjectSet<Shop> {
        isFetched = false

        _shops.clear()
        _shops.addAll(ShopService.findAllShops())

        isFetched = true

        return shops
    }
}
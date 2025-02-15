package dev.slne.augmented.shop.core

import com.google.auto.service.AutoService
import dev.slne.augmented.common.base.core.block.BlockPosition
import dev.slne.augmented.common.base.core.extensions.freeze
import dev.slne.augmented.common.base.core.extensions.mutableObjectSetOf
import dev.slne.augmented.common.base.core.extensions.synchronize
import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.api.ShopManager
import dev.slne.augmented.shop.api.shopManager
import dev.slne.augmented.shop.core.service.ShopService
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.util.Services
import java.util.*

val coreShopManager = shopManager as CoreShopManager

@AutoService(ShopManager::class)
class CoreShopManager : ShopManager, Services.Fallback {

    override var isFetched: Boolean = false
    override val shops: ObjectSet<Shop> get() = _shops.freeze()
    private val _shops = mutableObjectSetOf<Shop>().synchronize()

    val locationLocks = mutableObjectSetOf<Pair<UUID, BlockPosition>>().synchronize()

    fun isLocationLocked(world: UUID, location: BlockPosition) =
        locationLocks.contains(world to location)

    inline fun withLocationLock(
        location: Pair<UUID, BlockPosition>,
        block: (Boolean, () -> Unit) -> Unit
    ) {
        block(!locationLocks.add(location)) {
            println("Removing lock")
            locationLocks.remove(location)
        }
    }


    override fun addShop(shop: Shop) = _shops.add(shop)
    override fun removeShop(shop: Shop) = _shops.remove(shop)

    override suspend fun fetchShops(): ObjectSet<Shop> {
        isFetched = false

        _shops.clear()
        _shops.addAll(ShopService.findAllShops())

        isFetched = true

        return shops
    }
}
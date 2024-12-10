package dev.slne.augmented.shop.core.service

import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.core.repository.ShopRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ShopService {

    suspend fun saveShop(shop: Shop) =
        withContext(Dispatchers.IO) { ShopRepository.saveShop(shop) }

    suspend fun deleteShop(shop: Shop) =
        withContext(Dispatchers.IO) { ShopRepository.deleteShop(shop) }

    suspend fun findAllShops(): List<Shop> =
        withContext(Dispatchers.IO) { ShopRepository.findAllShops() }
}
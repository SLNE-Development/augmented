package dev.slne.augmented.shop.core.service

import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.core.CoreShop
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

object ShopService {

    suspend fun findAllShops(): List<Shop> =
        suspendedTransactionAsync(Dispatchers.IO) { CoreShop.all().toList() }.await()
}
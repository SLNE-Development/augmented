package dev.slne.augmented.shop.api.exception

import dev.slne.augmented.shop.api.Shop

class ShopSaveException(
    val shop: Shop,
    override val message: String = "The shop ${shop.shopKey} is currently in another database operation and thus can neither be saved nor deleted."
) : RuntimeException(message)
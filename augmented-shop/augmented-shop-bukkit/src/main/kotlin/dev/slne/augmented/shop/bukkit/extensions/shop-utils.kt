package dev.slne.augmented.shop.bukkit.extensions

import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.api.shopManager
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

fun ItemStack.isShopItem(): Boolean {
    return this.itemMeta?.persistentDataContainer?.has(
        NamespacedKey.fromString(Shop.SHOP_KEY.asString())!!,
        PersistentDataType.BOOLEAN
    ) ?: false
}

fun Block.isShop(): Boolean {
    return shopManager.shops.any { it.location == this.location.toBlockLocation() }
}

fun Block.getShop(): Shop? {
    return shopManager.shops.firstOrNull { it.location == this.location.toBlockLocation() }
}
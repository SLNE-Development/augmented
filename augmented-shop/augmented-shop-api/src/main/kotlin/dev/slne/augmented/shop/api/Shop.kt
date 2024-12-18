package dev.slne.augmented.shop.api

import dev.slne.augmented.common.base.core.block.BlockPosition
import net.kyori.adventure.key.Key
import java.util.*

interface Shop {

    var id: Long?

    var shopKey: UUID?
    var shopOwner: UUID?
    var material: String?

    var permittedUsers: Set<UUID>?

    var server: String?
    var world: UUID?
    var location: BlockPosition?

    var sellPrice: Double?
    var buyPrice: Double?

    var buyLimit: Int?
    var sellLimit: Int?

    var sellPaused: Boolean?
    var buyPaused: Boolean?

    var stockAmount: Int?

    fun add(): Shop
    fun remove(): Shop

    suspend fun save(): Shop
    suspend fun delete(): Shop

    companion object {
        val SHOP_KEY = Key.key("augmented", "shop")
    }
}
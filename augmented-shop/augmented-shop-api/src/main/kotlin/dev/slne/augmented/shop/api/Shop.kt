package dev.slne.augmented.shop.api

import dev.slne.augmented.common.base.core.block.BlockPosition
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import java.util.*

interface Shop {

    var id: Long?

    val shopKey: UUID?
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

    fun add(): Boolean
    fun remove(): Boolean

    suspend fun save(): Boolean
    suspend fun delete(): Boolean

    fun getShopOwnerDisplayName(): Component

    companion object {
        val SHOP_KEY = Key.key("augmented", "shop")
    }
}
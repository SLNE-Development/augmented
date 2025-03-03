package dev.slne.augmented.shop.api

import dev.slne.augmented.common.base.core.block.BlockPosition
import net.kyori.adventure.key.Key
import java.util.*

interface Shop {

    val shopKey: UUID
    var shopOwner: UUID
    var material: String

    val permittedUsers: Set<UUID>

    var server: String
    var world: UUID
    var location: BlockPosition

    var sellPrice: Double
    var buyPrice: Double

    var buyLimit: Int
    var sellLimit: Int

    var sellPaused: Boolean
    var buyPaused: Boolean

    var stockAmount: Int

    companion object {
        val SHOP_KEY = Key.key("augmented", "shop")
    }
}
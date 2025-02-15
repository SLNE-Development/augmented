package dev.slne.augmented.shop.core

import dev.slne.augmented.common.base.core.block.BlockPosition
import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.api.shopManager
import dev.slne.augmented.shop.core.tables.ShopPermittedUsersTable
import dev.slne.augmented.shop.core.tables.ShopTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class CoreShop(id: EntityID<Long>) : LongEntity(id), Shop {

    companion object : LongEntityClass<CoreShop>(ShopTable)

    override var material by ShopTable.material
    override var shopKey by ShopTable.shopKey
    override var shopOwner by ShopTable.shopOwner
    override var server by ShopTable.server
    override var world by ShopTable.location_world
    private var locationX by ShopTable.locationX
    private var locationY by ShopTable.locationY
    private var locationZ by ShopTable.locationZ
    override var location: BlockPosition
        get() = BlockPosition(locationX, locationY, locationZ)
        set(value) {
            locationX = value.x
            locationY = value.y
            locationZ = value.z
        }

    override var sellPrice by ShopTable.sellPrice
    override var buyPrice by ShopTable.buyPrice
    override var buyLimit by ShopTable.buyLimit
    override var sellLimit by ShopTable.sellLimit
    override var sellPaused by ShopTable.sellPaused
    override var buyPaused by ShopTable.buyPaused
    override var stockAmount by ShopTable.stockAmount

    private val permittedUsersModels by CoreShopPermittedUser referrersOn ShopPermittedUsersTable.shopId
    override val permittedUsers: Set<UUID>
        get() = permittedUsersModels.map { it.userUuid }.toSet()

    override fun delete() {
        super.delete()

        shopManager.removeShop(this)
    }

    override fun toString(): String {
        return "CoreShop(id=$id, material=$material, shopKey=$shopKey, shopOwner=$shopOwner, permittedUsers=$permittedUsers, server=$server, world=$world, location=$location, sellPrice=$sellPrice, buyPrice=$buyPrice, buyLimit=$buyLimit, sellLimit=$sellLimit, sellPaused=$sellPaused, buyPaused=$buyPaused, stockAmount=$stockAmount)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CoreShop

        if (id != other.id) return false
        if (shopKey != other.shopKey) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode() ?: 0

        result = 31 * result + shopKey.hashCode()

        return result
    }

}
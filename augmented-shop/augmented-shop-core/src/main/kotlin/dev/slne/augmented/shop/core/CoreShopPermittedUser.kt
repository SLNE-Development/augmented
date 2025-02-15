package dev.slne.augmented.shop.core

import dev.slne.augmented.shop.core.tables.ShopPermittedUsersTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class CoreShopPermittedUser(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CoreShopPermittedUser>(ShopPermittedUsersTable)

    var shop by CoreShop referencedOn ShopPermittedUsersTable.shopId
    var userUuid by ShopPermittedUsersTable.userId
}
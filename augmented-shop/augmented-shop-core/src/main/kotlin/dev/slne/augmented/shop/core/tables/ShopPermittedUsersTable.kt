package dev.slne.augmented.shop.core.tables

import dev.slne.augmented.common.database.core.types.uuidChar
import org.jetbrains.exposed.dao.id.LongIdTable

object ShopPermittedUsersTable : LongIdTable("augmented_shop_permitted_users") {
    val shopId = reference("shop_id", ShopTable)
    val userId = uuidChar("user_id")
}
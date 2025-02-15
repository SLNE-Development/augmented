package dev.slne.augmented.shop.core.tables

import dev.slne.augmented.common.database.core.types.uuidChar
import org.jetbrains.exposed.dao.id.LongIdTable

object ShopTable : LongIdTable("augmented_shops") {
    val material = varchar("material", 255)
    val shopKey = uuidChar("shop_key").uniqueIndex()
    val shopOwner = uuidChar("shop_owner")
    val server = varchar("location_server", 255)
    val location_world = uuidChar("location_world")
    val locationX = integer("location_x")
    val locationY = integer("location_y")
    val locationZ = integer("location_z")
    val sellPrice = double("sell_price")
    val buyPrice = double("buy_price")
    val buyLimit = integer("buy_limit")
    val sellLimit = integer("sell_limit")
    val sellPaused = bool("sell_paused")
    val buyPaused = bool("buy_paused")
    val stockAmount = integer("stock_amount")
}
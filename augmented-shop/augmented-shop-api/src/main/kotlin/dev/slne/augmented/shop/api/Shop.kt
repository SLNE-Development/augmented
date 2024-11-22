package dev.slne.augmented.shop.api

import java.util.*

interface Shop {

    var id: Long?

    var shopKey: UUID?
    var shopOwner: UUID?
    var material: String?

    var permittedUsers: Set<UUID>?

    var server: String?
    var world: String?
    var x: Double?
    var y: Double?
    var z: Double?

    var sellPrice: Double?
    var buyPrice: Double?

    var buyLimit: Int?
    var sellLimit: Int?

    var sellPaused: Boolean?
    var buyPaused: Boolean?

    var stockAmount: Int?
}
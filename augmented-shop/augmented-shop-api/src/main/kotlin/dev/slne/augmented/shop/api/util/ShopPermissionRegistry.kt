package dev.slne.augmented.shop.api.util

object ShopPermissionRegistry {

    val BASE = "augmented.shop.command.shop.base"

    val ADMIN = "augmented.shop.admin"

    object Admin {
        val GET = "augmented.shop.admin.get"
        val LIST_BROKEN = "augmented.shop.admin.list-broken"
    }

}
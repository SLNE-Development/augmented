package dev.slne.augmented.shop.bukkit.commands.subcommands.admin

import dev.jorel.commandapi.kotlindsl.booleanArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.augmented.common.base.core.extensions.mutableObjectSetOf
import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.api.shopManager
import dev.slne.augmented.shop.api.util.ShopPermissionRegistry
import dev.slne.augmented.shop.bukkit.extensions.getBukkitLocation
import dev.slne.augmented.shop.bukkit.extensions.getBukkitWorld
import dev.slne.augmented.shop.bukkit.extensions.getMaterial

val ShopAdminListBrokenCommand = subcommand("list-broken") {
    withPermission(ShopPermissionRegistry.Admin.LIST_BROKEN)

    booleanArgument("fix", true)

    playerExecutor { player, args ->
        val fix = args.getOptional("fix").orElse(false) as Boolean

        val shops = shopManager.shops
        val brokenShops = mutableObjectSetOf<Pair<Shop, BrokenReason>>()

        shops.forEach { shop ->
            val world = shop.getBukkitWorld()
            val location = shop.getBukkitLocation()

            if (world == null) {
                brokenShops.add(shop to BrokenReason.NO_WORLD)
                return@forEach
            } else if (location == null) {
                brokenShops.add(shop to BrokenReason.NO_LOCATION)
                return@forEach
            }

            if (location.block.type.isAir) {
                brokenShops.add(shop to BrokenReason.NO_BLOCK)
                return@forEach
            }
        }

        player.sendMessage("Broken Shops:")
        brokenShops.forEach { (shop, reason) ->
            player.sendMessage("Shop: ${shop.shopKey} Reason: $reason")
        }

        if (fix) {
            brokenShops.forEach { (shop, _) ->
                val location = shop.getBukkitLocation() ?: return@forEach
                location.block.type = shop.getMaterial() ?: return@forEach

                player.sendMessage("Fixed shop: ${shop.shopKey} at ${location.world.name} - ${location.blockX}, ${location.blockY}, ${location.blockZ}")
            }
        }
    }
}

enum class BrokenReason {
    NO_BLOCK,
    NO_WORLD,
    NO_LOCATION
}

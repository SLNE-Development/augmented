package dev.slne.augmented.shop.bukkit.commands.subcommands

import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.augmented.shop.api.util.ShopPermissionRegistry
import dev.slne.augmented.shop.bukkit.commands.subcommands.admin.ShopAdminGetCommand
import dev.slne.augmented.shop.bukkit.commands.subcommands.admin.ShopAdminListBrokenCommand

val ShopAdminCommand = subcommand("admin") {
    withPermission(ShopPermissionRegistry.ADMIN)

    withSubcommands(ShopAdminGetCommand)
    withSubcommands(ShopAdminListBrokenCommand)
}

package dev.slne.augmented.shop.bukkit.commands

import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.slne.augmented.shop.api.util.ShopPermissionRegistry
import dev.slne.augmented.shop.bukkit.commands.subcommands.ShopGetCommand

object ShopCommand {
    init {
        commandAPICommand("shop") {
            withAliases("augmentedshop")
            withPermission(ShopPermissionRegistry.COMMAND_SHOP)
            withSubcommands(ShopGetCommand)
        }
    }
}
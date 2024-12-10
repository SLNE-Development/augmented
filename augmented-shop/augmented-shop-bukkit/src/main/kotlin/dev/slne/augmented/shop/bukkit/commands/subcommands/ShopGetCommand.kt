package dev.slne.augmented.shop.bukkit.commands.subcommands

import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.integerArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.subcommand
import dev.slne.augmented.common.base.bukkit.extensions.sendMessage
import dev.slne.augmented.common.base.bukkit.plugin.plugin
import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.api.util.ShopPermissionRegistry
import dev.slne.augmented.shop.bukkit.extensions.giveItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

val ShopGetCommand = subcommand("get") {
    withPermission(ShopPermissionRegistry.COMMAND_SHOP_GET)

    integerArgument("amount", 1, 64)

    playerExecutor { player, args ->
        val amount: Int by args

        plugin.launch {
            Shop.giveItem(player, amount)

            player.sendMessage(Component.text("Du hast einen Shop erhalten.", NamedTextColor.GREEN))
        }
    }
}

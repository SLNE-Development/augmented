package dev.slne.augmented.shop.bukkit.commands.subcommands.admin

import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.*
import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.api.util.ShopPermissionRegistry
import dev.slne.augmented.shop.bukkit.extensions.giveItem
import dev.slne.augmented.shop.bukkit.plugin
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material

val ShopAdminGetCommand = subcommand("get") {
    withPermission(ShopPermissionRegistry.Admin.GET)

    integerArgument("amount", 1, 64)
    stringArgument("material", true) {
        replaceSuggestions(ArgumentSuggestions.stringCollection { _ ->
            Material.entries.filter { it.isSolid }.map { it.name }.toList()
        })
    }

    playerExecutor { player, args ->
        val amount: Int by args
        val material = args.getOrDefaultUnchecked("material", "CHEST")
        val materialBukkit = runCatching { Material.valueOf(material) }.getOrElse { Material.CHEST }

        plugin.launch {
            Shop.giveItem(player, materialBukkit, amount)

            player.sendMessage(Component.text("Du hast einen Shop erhalten.", NamedTextColor.GREEN))
        }
    }
}

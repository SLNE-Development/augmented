package dev.slne.augmented.shop.bukkit.menu

import com.github.retrooper.packetevents.protocol.item.type.ItemTypes
import net.craftoriya.packetuxui.menu.button.Button
import net.craftoriya.packetuxui.menu.item.ItemBuilder
import net.kyori.adventure.text.Component

val paneButton = Button(
    item = ItemBuilder().itemType(ItemTypes.BLACK_STAINED_GLASS_PANE).name(Component.space())
        .build()
)
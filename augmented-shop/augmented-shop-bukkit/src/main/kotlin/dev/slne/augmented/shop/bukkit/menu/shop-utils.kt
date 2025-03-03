package dev.slne.augmented.shop.bukkit.menu

import com.github.retrooper.packetevents.protocol.item.type.ItemTypes
import dev.slne.surf.gui.menu.button.Button
import dev.slne.surf.gui.menu.item.ItemBuilder
import net.kyori.adventure.text.Component

val paneButton = Button(
    item = ItemBuilder()
        .itemType(ItemTypes.BLACK_STAINED_GLASS_PANE)
        .name(Component.space())
        .build()
)
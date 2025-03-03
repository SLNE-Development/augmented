package dev.slne.augmented.shop.bukkit.menu

import com.github.shynixn.mccoroutine.folia.scope
import dev.slne.augmented.common.base.core.extensions.int2ObjectMapOf
import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.bukkit.extensions.getMaterial
import dev.slne.augmented.shop.bukkit.plugin
import dev.slne.surf.gui.menu.button.Button
import dev.slne.surf.gui.menu.item.ItemBuilder
import dev.slne.surf.gui.menu.menu.Menu
import dev.slne.surf.gui.menu.menu.MenuType
import dev.slne.surf.gui.menu.utils.position
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import net.kyori.adventure.text.Component
import org.jetbrains.annotations.Unmodifiable

fun generateShopButtons(shop: Shop): @Unmodifiable Int2ObjectMap<Button> {
    val buttons = int2ObjectMapOf<Button>()

    for (slot in 0 until MenuType.GENERIC9X5.size) {
        when (slot) {
            in position(0, 0).toSlot()..position(8, 0).toSlot() -> paneButton
            in position(0, 4).toSlot()..position(8, 4).toSlot() -> paneButton
            position(4, 0).toSlot() -> Button(
                item = ItemBuilder()
                    .itemType(SpigotConversionUtil.fromBukkitItemMaterial(shop.getMaterial()))
                    .name(
                        Component.text(
                            shop.getMaterial().name
                        )
                    )
                    .build()
            )
        }
    }

    return buttons
}

class ShopMenu(
    shop: Shop
) : Menu(
    Component.text("Shop"),
    MenuType.GENERIC9X5,
    generateShopButtons(shop),
    coroutineScope = plugin.scope
)
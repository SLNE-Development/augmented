package dev.slne.augmented.common.base.bukkit.item

import dev.slne.augmented.common.base.bukkit.extensions.toLegacy
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

@Target(AnnotationTarget.TYPE)
@DslMarker
annotation class ItemMarker

@Target(AnnotationTarget.TYPE)
@DslMarker
annotation class MetaMarker

@Target(AnnotationTarget.TYPE)
@DslMarker
annotation class PersistentDataMarker

fun buildItem(
    material: Material,
    amount: Int = 1,
    init: (@ItemMarker ItemStack).() -> Unit
): ItemStack {
    val item = ItemStack(material, amount)
    item.init()
    return item
}

inline fun <reified M : ItemMeta> ItemStack.meta(crossinline init: (@MetaMarker M).() -> Unit) {
    val meta = itemMeta as? M ?: return
    meta.init()
    itemMeta = meta
}

fun ItemStack.displayName(name: Component) {
    meta<ItemMeta> {
        setDisplayName(
            name.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE).toLegacy()
        )
    }
}

fun ItemStack.lore(vararg lore: Component) {
    meta<ItemMeta> {
        setLore(lore.map {
            it.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE).toLegacy()
        })
    }
}

fun ItemStack.persistentData(init: (@PersistentDataMarker PersistentDataContainer).() -> Unit) {
    meta<ItemMeta> {
        persistentDataContainer.init()
    }
}
package dev.slne.augmented.shop.bukkit.extensions

import dev.slne.augmented.shop.api.Shop
import org.bukkit.Material

fun Shop.getMaterial(): Material? = material?.let { Material.matchMaterial(it) }
fun Shop.setMaterial(material: Material) {
    this.material = material.name
}
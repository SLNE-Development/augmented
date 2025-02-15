package dev.slne.augmented.shop.bukkit.extensions

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import dev.slne.augmented.common.base.bukkit.extensions.setBoolean
import dev.slne.augmented.common.base.bukkit.extensions.toLocation
import dev.slne.augmented.common.base.bukkit.item.buildItem
import dev.slne.augmented.common.base.bukkit.item.displayName
import dev.slne.augmented.common.base.bukkit.item.persistentData
import dev.slne.augmented.common.base.core.block.BlockPosition
import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.bukkit.plugin
import dev.slne.augmented.shop.core.CoreShop
import kotlinx.coroutines.withContext
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.Player
import java.util.*

fun Shop.getMaterial(): Material? = material?.let { Material.matchMaterial(it) }
fun Shop.setMaterial(material: Material) {
    this.material = material.name
}

fun Shop.getBukkitLocation(): Location? {
    val shopWorld = getBukkitWorld() ?: return null

    return location?.toLocation(shopWorld)
}

fun Shop.getBukkitWorld(): World? = world?.let { Bukkit.getWorld(it) }

suspend fun Shop.Companion.giveItem(player: Player, amount: Int = 1) =
    withContext(plugin.entityDispatcher(player)) {
        val item = buildItem(Material.CHEST, amount) {
            displayName(Component.text("Shop", NamedTextColor.GOLD))

            persistentData {
                setBoolean(SHOP_KEY, true)
            }
        }

        player.inventory.addItem(item)
    }

fun CoreShop(
    material: Material,
    shopOwner: UUID,
    server: String,
    world: World,
    location: BlockPosition
): CoreShop {
    val shopId = UUID.randomUUID() // Generate a new shop id

    return CoreShop.new {
        this.material = material.name
        this.shopKey = shopId
        this.shopOwner = shopOwner

        this.server = server
        this.world = world.uid
        this.location = location

        this.sellPrice = 0.0
        this.buyPrice = 0.0
        this.buyLimit = 0
        this.sellLimit = 0

        this.sellPaused = false
        this.buyPaused = false

        this.stockAmount = 0
    }
}

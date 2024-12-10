package dev.slne.augmented.common.base.bukkit.extensions

import dev.slne.augmented.common.base.core.block.BlockLocation
import org.bukkit.Location
import org.bukkit.World

fun Location.toBlockLocation() = BlockLocation(this.blockX, this.blockY, this.blockZ)
fun BlockLocation.toLocation(world: World) =
    Location(world, x.toDouble(), y.toDouble(), z.toDouble())
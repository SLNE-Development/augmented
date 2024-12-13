package dev.slne.augmented.common.base.bukkit.extensions

import dev.slne.augmented.common.base.core.block.BlockPosition
import org.bukkit.Location
import org.bukkit.World

fun Location.toPosition() = BlockPosition(this.blockX, this.blockY, this.blockZ)
fun BlockPosition.toLocation(world: World) =
    Location(world, x.toDouble(), y.toDouble(), z.toDouble())
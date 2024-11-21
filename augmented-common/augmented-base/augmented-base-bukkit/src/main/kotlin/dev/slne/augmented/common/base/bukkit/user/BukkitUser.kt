package dev.slne.augmented.common.base.bukkit.user

import dev.slne.augmented.common.base.core.user.User
import dev.slne.augmented.common.base.core.user.UserManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class BukkitUser(override val uuid: UUID) : User {

    fun getPlayer() = Bukkit.getPlayer(uuid)
    fun getOfflinePlayer() = Bukkit.getOfflinePlayer(uuid)

}

fun Player.getUser() = UserManager[uniqueId]
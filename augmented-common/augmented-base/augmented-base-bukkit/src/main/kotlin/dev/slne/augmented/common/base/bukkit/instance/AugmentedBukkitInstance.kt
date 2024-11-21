package dev.slne.augmented.common.base.bukkit.instance

import dev.slne.augmented.common.base.bukkit.user.BukkitUser
import dev.slne.augmented.common.base.core.instance.AugmentedInstance
import dev.slne.augmented.common.base.core.user.User
import net.kyori.adventure.util.Services.Fallback
import java.util.*

class AugmentedBukkitInstance : AugmentedInstance, Fallback {

    @Suppress("UNCHECKED_CAST")
    override fun <U : User> constructNewUserObject(uuid: UUID): U {
        return BukkitUser(uuid) as U
    }
}
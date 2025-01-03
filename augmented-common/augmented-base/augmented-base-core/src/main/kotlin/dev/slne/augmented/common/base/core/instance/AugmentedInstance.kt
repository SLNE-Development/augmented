package dev.slne.augmented.common.base.core.instance

import dev.slne.augmented.common.base.core.extensions.requiredService
import dev.slne.augmented.common.base.core.user.User
import java.util.*

interface AugmentedInstance {

    fun <U : User> constructNewUserObject(uuid: UUID): U

    companion object {
        val INSTANCE = requiredService<AugmentedInstance>()
    }

}

val augmentedInstance: AugmentedInstance
    get() = AugmentedInstance.INSTANCE
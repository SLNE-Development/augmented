package dev.slne.augmented.common.base.core.instance

import dev.slne.augmented.common.base.core.user.User
import java.util.*

interface AugmentedInstance {

    fun <U : User> constructNewUserObject(uuid: UUID): U

    object AugmentedInstanceHolder {
        lateinit var instance: AugmentedInstance
    }

}
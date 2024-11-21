package dev.slne.augmented.common.base.core.user

import net.kyori.adventure.audience.Audience
import java.util.*

interface User : Audience {

    val uuid: UUID

}
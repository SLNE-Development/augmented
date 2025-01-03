package dev.slne.augmented.common.base.core.user

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import dev.slne.augmented.common.base.core.extensions.asObjectMap
import dev.slne.augmented.common.base.core.instance.augmentedInstance
import java.util.*
import kotlin.time.Duration.Companion.hours
import kotlin.time.toJavaDuration

object UserManager {

    operator fun get(uuid: UUID): User = _users.get(uuid)

    private val _users: LoadingCache<UUID, User> = Caffeine.newBuilder()
        .expireAfterWrite(1.hours.toJavaDuration())
        .build { augmentedInstance.constructNewUserObject(it) }

    val users = _users.asObjectMap()
}
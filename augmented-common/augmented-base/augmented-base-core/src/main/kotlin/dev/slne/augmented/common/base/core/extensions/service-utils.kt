package dev.slne.augmented.common.base.core.extensions

import net.kyori.adventure.util.Services

inline fun <reified T> requiredService(): T = Services.serviceWithFallback(T::class.java)
    .orElseThrow { Error("Service ${T::class.java.name} not available") }
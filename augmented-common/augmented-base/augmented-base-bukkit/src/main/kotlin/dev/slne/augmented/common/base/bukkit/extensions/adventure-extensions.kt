package dev.slne.augmented.common.base.bukkit.extensions

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.NamespacedKey

fun NamespacedKey.toAdventure() = Key.key(namespace, key)

fun Key.toNamespacedKey() = NamespacedKey.fromString(toString())!!

fun Sound.Builder.type(sound: org.bukkit.Sound) = type(sound.key())

fun Component.toLegacy() = LegacyComponentSerializer.legacySection().serialize(this)
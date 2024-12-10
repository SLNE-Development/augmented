package dev.slne.augmented.common.base.bukkit.extensions

import net.kyori.adventure.key.Key
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

fun PersistentDataContainer.setBoolean(key: Key, value: Boolean) {
    set(key.toNamespacedKey(), PersistentDataType.BOOLEAN, value)
}

fun PersistentDataContainer.getBoolean(key: Key): Boolean? {
    return get(key.toNamespacedKey(), PersistentDataType.BOOLEAN)
}

fun PersistentDataContainer.setByte(key: Key, value: Byte) {
    set(key.toNamespacedKey(), PersistentDataType.BYTE, value)
}

fun PersistentDataContainer.getByte(key: Key): Byte? {
    return get(key.toNamespacedKey(), PersistentDataType.BYTE)
}

fun PersistentDataContainer.setShort(key: Key, value: Short) {
    set(key.toNamespacedKey(), PersistentDataType.SHORT, value)
}

fun PersistentDataContainer.getShort(key: Key): Short? {
    return get(key.toNamespacedKey(), PersistentDataType.SHORT)
}

fun PersistentDataContainer.setInt(key: Key, value: Int) {
    set(key.toNamespacedKey(), PersistentDataType.INTEGER, value)
}

fun PersistentDataContainer.getInt(key: Key): Int? {
    return get(key.toNamespacedKey(), PersistentDataType.INTEGER)
}

fun PersistentDataContainer.setLong(key: Key, value: Long) {
    set(key.toNamespacedKey(), PersistentDataType.LONG, value)
}

fun PersistentDataContainer.getLong(key: Key): Long? {
    return get(key.toNamespacedKey(), PersistentDataType.LONG)
}

fun PersistentDataContainer.setFloat(key: Key, value: Float) {
    set(key.toNamespacedKey(), PersistentDataType.FLOAT, value)
}

fun PersistentDataContainer.getFloat(key: Key): Float? {
    return get(key.toNamespacedKey(), PersistentDataType.FLOAT)
}

fun PersistentDataContainer.setDouble(key: Key, value: Double) {
    set(key.toNamespacedKey(), PersistentDataType.DOUBLE, value)
}

fun PersistentDataContainer.getDouble(key: Key): Double? {
    return get(key.toNamespacedKey(), PersistentDataType.DOUBLE)
}

fun PersistentDataContainer.setString(key: Key, value: String) {
    set(key.toNamespacedKey(), PersistentDataType.STRING, value)
}

fun PersistentDataContainer.getString(key: Key): String? {
    return get(key.toNamespacedKey(), PersistentDataType.STRING)
}

fun PersistentDataContainer.setByteArray(key: Key, value: ByteArray) {
    set(key.toNamespacedKey(), PersistentDataType.BYTE_ARRAY, value)
}

fun PersistentDataContainer.getByteArray(key: Key): ByteArray? {
    return get(key.toNamespacedKey(), PersistentDataType.BYTE_ARRAY)
}

fun PersistentDataContainer.setIntArray(key: Key, value: IntArray) {
    set(key.toNamespacedKey(), PersistentDataType.INTEGER_ARRAY, value)
}

fun PersistentDataContainer.getIntArray(key: Key): IntArray? {
    return get(key.toNamespacedKey(), PersistentDataType.INTEGER_ARRAY)
}

fun PersistentDataContainer.setLongArray(key: Key, value: LongArray) {
    set(key.toNamespacedKey(), PersistentDataType.LONG_ARRAY, value)
}

fun PersistentDataContainer.getLongArray(key: Key): LongArray? {
    return get(key.toNamespacedKey(), PersistentDataType.LONG_ARRAY)
}

fun PersistentDataContainer.setTagContainer(key: Key, value: PersistentDataContainer) {
    set(key.toNamespacedKey(), PersistentDataType.TAG_CONTAINER, value)
}

fun PersistentDataContainer.getTagContainer(key: Key): PersistentDataContainer? {
    return get(key.toNamespacedKey(), PersistentDataType.TAG_CONTAINER)
}

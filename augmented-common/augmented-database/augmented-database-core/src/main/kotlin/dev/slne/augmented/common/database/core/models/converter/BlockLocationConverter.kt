package dev.slne.augmented.common.database.core.models.converter

import dev.slne.augmented.common.base.core.block.BlockLocation
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.nio.ByteBuffer

@Converter(autoApply = true)
class BlockLocationConverter : AttributeConverter<BlockLocation, ByteArray> {

    override fun convertToDatabaseColumn(attribute: BlockLocation): ByteArray {
        val buffer = ByteBuffer.allocate(12)

        buffer.putInt(attribute.x)
        buffer.putInt(attribute.y)
        buffer.putInt(attribute.z)

        return buffer.array()
    }

    override fun convertToEntityAttribute(dbData: ByteArray): BlockLocation {
        val buffer = ByteBuffer.wrap(dbData)

        val x = buffer.int
        val y = buffer.int
        val z = buffer.int

        return BlockLocation(x, y, z)
    }
}
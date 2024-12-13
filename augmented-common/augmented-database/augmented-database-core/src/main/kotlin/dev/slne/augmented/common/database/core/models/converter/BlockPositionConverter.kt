package dev.slne.augmented.common.database.core.models.converter

import dev.slne.augmented.common.base.core.block.BlockPosition
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.nio.ByteBuffer

@Converter(autoApply = true)
class BlockPositionConverter : AttributeConverter<BlockPosition, ByteArray> {

    override fun convertToDatabaseColumn(attribute: BlockPosition): ByteArray {
        val buffer = ByteBuffer.allocate(12)

        buffer.putInt(attribute.x)
        buffer.putInt(attribute.y)
        buffer.putInt(attribute.z)

        return buffer.array()
    }

    override fun convertToEntityAttribute(dbData: ByteArray): BlockPosition {
        val buffer = ByteBuffer.wrap(dbData)

        val x = buffer.int
        val y = buffer.int
        val z = buffer.int

        return BlockPosition(x, y, z)
    }
}
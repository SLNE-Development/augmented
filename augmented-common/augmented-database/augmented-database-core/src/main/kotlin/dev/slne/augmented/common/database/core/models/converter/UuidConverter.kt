package dev.slne.augmented.common.database.core.models.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.util.*

@Converter(autoApply = true)
class UuidConverter : AttributeConverter<UUID, String> {

    override fun convertToDatabaseColumn(attribute: UUID) = attribute.toString()

    override fun convertToEntityAttribute(dbData: String): UUID = UUID.fromString(dbData)
}
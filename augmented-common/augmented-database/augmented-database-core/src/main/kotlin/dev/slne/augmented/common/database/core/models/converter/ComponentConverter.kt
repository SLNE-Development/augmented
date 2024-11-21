package dev.slne.augmented.common.database.core.models.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

@Converter(autoApply = true)
class ComponentConverter : AttributeConverter<Component, String> {
    override fun convertToDatabaseColumn(attribute: Component): String {
        return GsonComponentSerializer.gson().serialize(attribute)
    }

    override fun convertToEntityAttribute(dbData: String): Component {
        return GsonComponentSerializer.gson().deserialize(dbData)
    }
}
package dev.slne.augmented.common.base.core.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

class ComponentKSerializer : KSerializer<Component> {
    override val descriptor = String.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Component) =
        encoder.encodeString(GsonComponentSerializer.gson().serialize(value))

    override fun deserialize(decoder: Decoder) =
        GsonComponentSerializer.gson().deserialize(decoder.decodeString())
}
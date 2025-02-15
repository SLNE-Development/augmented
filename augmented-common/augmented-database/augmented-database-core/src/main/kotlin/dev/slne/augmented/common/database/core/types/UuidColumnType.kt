package dev.slne.augmented.common.database.core.types

import org.jetbrains.exposed.sql.CharColumnType
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.api.PreparedStatementApi
import java.util.*

class UuidColumnType : ColumnType<UUID>() {
    override fun sqlType(): String = "CHAR(36)"

    override fun setParameter(stmt: PreparedStatementApi, index: Int, value: Any?) {
        when (value) {
            null -> stmt.setNull(index, CharColumnType())
            is UUID -> stmt[index] = value.toString()
            is String -> stmt[index] = value
            else -> error("Unexpected UUID value: $value | type ${value::class.simpleName}")
        }
    }

    override fun valueFromDB(value: Any): UUID = when (value) {
        is UUID -> value
        is String -> UUID.fromString(value)
        else -> error("Unexpected value: $value")
    }

    override fun notNullValueToDB(value: UUID) = value.toString()
    override fun valueToString(value: UUID?) = value?.toString() ?: "NULL"
}

fun Table.uuidChar(name: String): Column<UUID> = registerColumn(name, UuidColumnType())
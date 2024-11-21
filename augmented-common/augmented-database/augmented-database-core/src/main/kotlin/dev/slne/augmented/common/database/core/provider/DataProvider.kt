package dev.slne.augmented.common.database.core.provider

import java.nio.file.Path

interface DataProvider {

    fun getDataDirectory(): Path
}

object DataProviderHolder {
    lateinit var dataProvider: DataProvider
}
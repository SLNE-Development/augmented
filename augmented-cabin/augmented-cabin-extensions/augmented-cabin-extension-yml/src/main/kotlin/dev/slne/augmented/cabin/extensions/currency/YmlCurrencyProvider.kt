package dev.slne.augmented.cabin.extensions.currency

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import dev.slne.augmented.cabin.api.currency.Currency
import dev.slne.augmented.cabin.api.currency.CurrencyProvider
import dev.slne.augmented.cabin.extensions.cabinYmlExtension
import dev.slne.augmented.common.base.core.extensions.mutableObjectSetOf
import it.unimi.dsi.fastutil.objects.ObjectSet
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.FileInputStream

object YmlCurrencyProvider : CurrencyProvider {

    private val fetchMutex = Mutex()
    private val currencies = mutableObjectSetOf<Currency>()

    override fun getDefaultCurrency(): Currency? = currencies.firstOrNull { it.defaultCurrency }

    override fun getCurrency(currencyName: String): Currency? =
        currencies.firstOrNull { it.name == currencyName }

    override suspend fun fetchCurrencies(): ObjectSet<Currency> = fetchMutex.withLock {
        currencies.clear()

        val currenciesFile = cabinYmlExtension.getDataPath().resolve("currencies.yml").toFile()
        if (!currenciesFile.exists()) {
            currenciesFile.createNewFile()
            return currencies
        }

        FileInputStream(currenciesFile).use { inputStream ->
            val config = Yaml.default.decodeFromStream<YmlCurrencyFile>(inputStream)

            currencies.addAll(config.currencies)
        }

        return currencies
    }
}
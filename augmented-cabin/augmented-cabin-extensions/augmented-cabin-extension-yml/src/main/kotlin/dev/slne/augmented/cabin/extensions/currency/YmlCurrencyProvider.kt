package dev.slne.augmented.cabin.extensions.currency

import dev.slne.augmented.cabin.api.currency.Currency
import dev.slne.augmented.cabin.api.currency.CurrencyProvider
import dev.slne.augmented.cabin.extensions.cabinYmlExtension
import dev.slne.augmented.common.base.core.config.ConfigHolder
import dev.slne.augmented.common.base.core.extensions.mutableObjectSetOf
import it.unimi.dsi.fastutil.objects.ObjectSet

object YmlCurrencyProvider : ConfigHolder<YmlCurrencyFile>(
    clazz = YmlCurrencyFile::class,
    fileName = "currencies.yml",
    configFolder = cabinYmlExtension.getDataPath()
), CurrencyProvider {

    private val currencies = mutableObjectSetOf<Currency>()

    override fun getDefaultCurrency(): Currency? = currencies.firstOrNull { it.defaultCurrency }

    override fun getCurrency(currencyName: String): Currency? =
        currencies.firstOrNull { it.name == currencyName }

    override suspend fun fetchCurrencies(): ObjectSet<Currency> {
        currencies.clear()

        loadConfig()
        currencies.addAll(config.currencies)

        return currencies
    }

    override val defaultConfig = YmlCurrencyFile(
        currencies = listOf()
    )
}
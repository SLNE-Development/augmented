package dev.slne.augmented.cabin.extensions.balance

import dev.slne.augmented.cabin.api.balance.Balance
import dev.slne.augmented.cabin.api.balance.BalanceProvider
import dev.slne.augmented.cabin.api.currency.Currency
import dev.slne.augmented.cabin.extensions.cabinYmlExtension
import dev.slne.augmented.common.base.core.config.ConfigHolder
import dev.slne.augmented.common.base.core.user.User
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object YmlBalanceProvider : ConfigHolder<YmlBalances>(
    clazz = YmlBalances::class,
    fileName = "balances.yml",
    configFolder = cabinYmlExtension.getDataPath()
), BalanceProvider {

    override val defaultConfig = YmlBalances(emptyMap())
    private val updateMutex = Mutex()

    private suspend fun updateBalances(block: (YmlBalances) -> Balance): Balance =
        updateMutex.withLock {
            loadConfig()
            val result = block(config)
            saveConfig()

            return result
        }

    override suspend fun getBalance(user: User, currency: Currency) =
        config.getBalanceForUser(user.uuid, currency)

    override suspend fun setBalance(user: User, balance: Balance) = updateBalances {
        it.setBalanceForUser(user.uuid, balance)
    }

    override suspend fun addBalance(user: User, balance: Balance) = updateBalances {
        it.addBalanceForUser(user.uuid, balance)
    }

    override suspend fun subtractBalance(user: User, balance: Balance) = updateBalances {
        it.subtractBalanceForUser(user.uuid, balance)
    }
}
package dev.slne.augmented.cabin.extensions.balance

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import com.charleskorn.kaml.encodeToStream
import dev.slne.augmented.cabin.api.balance.Balance
import dev.slne.augmented.cabin.api.balance.BalanceProvider
import dev.slne.augmented.cabin.api.currency.Currency
import dev.slne.augmented.cabin.extensions.cabinYmlExtension
import dev.slne.augmented.common.base.core.user.User
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.FileInputStream
import java.io.FileOutputStream

class YmlBalanceProvider : BalanceProvider {

    private val fileMutex = Mutex()
    private val updateMutex = Mutex()

    private suspend fun getYmlFile(): YmlBalances = fileMutex.withLock {
        val balancesFile = cabinYmlExtension.getDataPath().resolve("balances.yml").toFile()

        if (!balancesFile.exists()) {
            balancesFile.createNewFile()
            return YmlBalances(emptyMap())
        }

        FileInputStream(balancesFile).use { inputStream ->
            return Yaml.default.decodeFromStream<YmlBalances>(inputStream)
        }
    }

    private suspend fun writeYmlFile(ymlBalances: YmlBalances) = fileMutex.withLock {
        val balancesFile = cabinYmlExtension.getDataPath().resolve("balances.yml").toFile()

        if (!balancesFile.exists()) {
            balancesFile.createNewFile()
        }

        FileOutputStream(balancesFile).use { outputStream ->
            Yaml.default.encodeToStream(ymlBalances, outputStream)
        }
    }

    private suspend fun updateBalances(block: (YmlBalances) -> Balance): Balance =
        updateMutex.withLock {
            val balances = getYmlFile()
            val result = block(balances)
            writeYmlFile(balances)

            return result
        }

    override suspend fun getBalance(user: User, currency: Currency) =
        getYmlFile().getBalanceForUser(user.uuid, currency)

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
package dev.slne.augmented.cabin.api.balance

import dev.slne.augmented.cabin.api.currency.Currency
import dev.slne.augmented.cabin.api.internalCabinInstance
import dev.slne.augmented.common.base.core.user.User

interface BalanceProvider {

    suspend fun getBalance(user: User, currency: Currency): Balance
    suspend fun setBalance(user: User, balance: Balance): Balance
    suspend fun addBalance(user: User, balance: Balance): Balance
    suspend fun subtractBalance(user: User, balance: Balance): Balance

}

suspend fun User.getBalance(currency: Currency) =
    balanceProvider.getBalance(this, currency)

suspend fun User.setBalance(balance: Balance) =
    balanceProvider.setBalance(this, balance)

suspend fun User.addBalance(balance: Balance) =
    balanceProvider.addBalance(this, balance)

suspend fun User.subtractBalance(balance: Balance) =
    balanceProvider.subtractBalance(this, balance)

val balanceProvider: BalanceProvider
    get() = internalCabinInstance.activeCabin.balanceProvider
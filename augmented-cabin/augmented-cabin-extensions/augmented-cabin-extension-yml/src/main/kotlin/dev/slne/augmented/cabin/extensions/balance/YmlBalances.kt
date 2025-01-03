package dev.slne.augmented.cabin.extensions.balance

import dev.slne.augmented.cabin.api.balance.Balance
import dev.slne.augmented.cabin.api.currency.Currency
import dev.slne.augmented.common.base.core.serializers.UuidKSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class YmlBalances(
    val balances: Map<@Serializable(with = UuidKSerializer::class) UUID, List<YmlBalance>>
) {
    fun getBalanceForUser(user: UUID, currency: Currency): Balance =
        balances[user]?.firstOrNull { it.currency == currency.name }
            ?.let { Balance(it.balance, currency) }
            ?: Balance(0.0, currency)

    fun setBalanceForUser(user: UUID, balance: Balance): Balance {
        val userBalances = balances[user]?.toMutableList() ?: mutableListOf()
        val existingYmlBalance = userBalances.firstOrNull { it.currency == balance.currency.name }

        if (existingYmlBalance != null) {
            existingYmlBalance.balance = balance.amount
        } else {
            userBalances.add(balance.toYmlBalance())
        }

        return balance
    }

    fun addBalanceForUser(user: UUID, balance: Balance): Balance {
        val userBalances = balances[user]?.toMutableList() ?: mutableListOf()
        val existingYmlBalance = userBalances.firstOrNull { it.currency == balance.currency.name }

        if (existingYmlBalance != null) {
            existingYmlBalance.balance += balance.amount
        } else {
            userBalances.add(balance.toYmlBalance())
        }

        return balance
    }

    fun subtractBalanceForUser(user: UUID, balance: Balance): Balance {
        val userBalances = balances[user]?.toMutableList() ?: mutableListOf()
        val existingYmlBalance = userBalances.firstOrNull { it.currency == balance.currency.name }

        if (existingYmlBalance != null) {
            existingYmlBalance.balance -= balance.amount
        } else {
            userBalances.add(balance.toYmlBalance())
        }

        return balance
    }

}
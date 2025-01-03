package dev.slne.augmented.cabin.api.balance

import dev.slne.augmented.cabin.api.currency.Currency
import net.kyori.adventure.text.Component

data class Balance(val amount: Double, val currency: Currency) {

    fun format() = String.format("%.${currency.decimalPlaces}f", amount)
    fun formatComponent() = Component.text(format())

    fun formatWithSymbol(): String {
        val symbol = currency.symbol

        return buildString {
            if (currency.symbolPlacement == Currency.SymbolPlacement.BEFORE) {
                append(symbol)
            }

            append(format())

            if (currency.symbolPlacement == Currency.SymbolPlacement.AFTER) {
                append(symbol)
            }
        }
    }

    fun formatComponentWithSymbol(): Component {
        val symbol = currency.symbolDisplay
        val builder = Component.text()

        if (currency.symbolPlacement == Currency.SymbolPlacement.BEFORE) {
            builder.append(symbol)
        }

        builder.append(formatComponent())

        if (currency.symbolPlacement == Currency.SymbolPlacement.AFTER) {
            builder.append(symbol)
        }

        return builder.build()
    }

    operator fun plus(amount: Double): Balance {
        return Balance(this.amount + amount, currency)
    }

    operator fun minus(amount: Double): Balance {
        return Balance(this.amount - amount, currency)
    }

    operator fun times(amount: Double): Balance {
        return Balance(this.amount * amount, currency)
    }

    operator fun div(amount: Double): Balance {
        return Balance(this.amount / amount, currency)
    }

    operator fun plus(balance: Balance): Balance {
        if (balance.currency != currency) {
            throw IllegalArgumentException("Cannot add balances with different currencies")
        }

        return Balance(this.amount + balance.amount, currency)
    }

    operator fun minus(balance: Balance): Balance {
        if (balance.currency != currency) {
            throw IllegalArgumentException("Cannot subtract balances with different currencies")
        }

        return Balance(this.amount - balance.amount, currency)
    }

    operator fun times(balance: Balance): Balance {
        if (balance.currency != currency) {
            throw IllegalArgumentException("Cannot multiply balances with different currencies")
        }

        return Balance(this.amount * balance.amount, currency)
    }

    operator fun div(balance: Balance): Balance {
        if (balance.currency != currency) {
            throw IllegalArgumentException("Cannot divide balances with different currencies")
        }

        return Balance(this.amount / balance.amount, currency)
    }


    operator fun compareTo(balance: Balance): Int {
        if (balance.currency != currency) {
            throw IllegalArgumentException("Cannot compare balances with different currencies")
        }

        return this.amount.compareTo(balance.amount)
    }

    operator fun compareTo(amount: Double): Int {
        return this.amount.compareTo(amount)
    }

}
package dev.slne.augmented.common.gui.inventory

import dev.slne.augmented.common.gui.inventory.exception.ShadowInventoryTransactionException

interface ShadowInventory : CustomInventory {

    @Throws(ShadowInventoryTransactionException::class)
    fun startTransaction()

    @Throws(ShadowInventoryTransactionException::class)
    fun performTransaction()

    @Throws(ShadowInventoryTransactionException::class)
    fun rollbackTransaction()
}
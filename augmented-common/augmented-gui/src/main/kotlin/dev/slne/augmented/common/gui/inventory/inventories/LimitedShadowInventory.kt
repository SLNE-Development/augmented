package dev.slne.augmented.common.gui.inventory.inventories

import dev.slne.augmented.common.gui.inventory.ShadowInventory
import dev.slne.augmented.common.gui.inventory.exception.ShadowInventoryTransactionException
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class LimitedShadowInventory(private val storage: Array<ItemStack?>) : ShadowInventory {

    private val changes: Array<ItemStack?> = arrayOfNulls(storage.size)
    private var inventory: Inventory? = null
    private var transaction = false

    constructor(inventory: Inventory) : this(inventory.storageContents) {
        this.inventory = inventory
    }

    override fun getItem(position: Int): ItemStack? {
        val change = changes[position]

        if (change != null) {
            if (!transaction) {
                throw ShadowInventoryTransactionException("Did not expect to have changes when transaction did not start")
            }

            return change
        }

        return storage[position]?.clone()
    }

    override fun setItem(position: Int, itemStack: ItemStack?) {
        if (!transaction) {
            storage[position] = itemStack
            inventory?.setItem(position, itemStack)
            return
        }

        changes[position] = itemStack?.clone()
    }

    override fun getSize() = storage.size

    override fun performTransaction() {
        if (!transaction) {
            throw ShadowInventoryTransactionException("Transaction did not start")
        }

        val inventory = inventory
            ?: throw ShadowInventoryTransactionException("Cannot perform transaction on a limited inventory without an inventory")

        if (inventory.size < storage.size) {
            throw ShadowInventoryTransactionException("Inventory size is smaller than the storage size")
        }

        for (i in 0 until inventory.size) {
            if (i >= storage.size) {
                break
            }

            val inventoryItem = inventory.getItem(i)

            if (inventoryItem?.isSimilar(storage[i]) ?: (inventoryItem == storage[i])) {
                val diff = changes[i]

                if (diff != null) {
                    inventory.setItem(i, diff)

                    storage[i] = diff
                    changes[i] = null
                }
            }
        }

        transaction = false
    }

    override fun startTransaction() {
        if (transaction) {
            throw ShadowInventoryTransactionException("Transaction already started")
        }

        transaction = true
    }

    override fun rollbackTransaction() {
        if (!transaction) {
            throw ShadowInventoryTransactionException("Transaction did not start")
        }

        for (i in changes.indices) {
            changes[i] = null
        }

        transaction = false
    }

    override fun addItem(itemStack: ItemStack) {
        throw UnsupportedOperationException("Cannot add items to a limited inventory")
    }

    override fun indexOf(currentItem: ItemStack): Int {
        return storage.indexOf(currentItem)
    }
}
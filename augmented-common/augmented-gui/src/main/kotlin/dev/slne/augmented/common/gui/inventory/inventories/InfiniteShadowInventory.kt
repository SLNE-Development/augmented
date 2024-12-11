package dev.slne.augmented.common.gui.inventory.inventories

import dev.slne.augmented.common.gui.inventory.ShadowInventory
import org.bukkit.inventory.ItemStack

class InfiniteShadowInventory(private var itemStack: ItemStack) : ShadowInventory {

    private var transaction: Boolean = false

    init {
        itemStack = itemStack.clone()
        itemStack.amount = itemStack.maxStackSize
    }

    override fun getItem(position: Int): ItemStack? {
        return itemStack.clone()
    }

    override fun setItem(position: Int, itemStack: ItemStack?) {

    }

    override fun getSize() = 1

    override fun performTransaction() {
        if (!transaction) {
            throw IllegalStateException("Transaction did not start")
        }

        transaction = false
    }

    override fun startTransaction() {
        if (transaction) {
            throw IllegalStateException("Transaction already started")
        }

        transaction = true
    }

    override fun rollbackTransaction() {
        if (!transaction) {
            throw IllegalStateException("Transaction did not start")
        }

        transaction = false
    }

    override fun addItem(itemStack: ItemStack) {

    }

    override fun indexOf(currentItem: ItemStack) = -1
}
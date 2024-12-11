package dev.slne.augmented.common.gui.inventory.transfer

import dev.slne.augmented.common.gui.inventory.ShadowInventory
import dev.slne.augmented.common.gui.inventory.exception.ShadowInventoryTransactionException
import org.bukkit.inventory.ItemStack


class InventoryTransfer(val from: ShadowInventory, val to: ShadowInventory) {

    private fun findSimilarSlot(data: ItemStack): Int {
        for (i in 0 until to.getSize()) {
            val item = to.getItem(i)

            if (item?.isSimilar(data) == true && item.amount < item.maxStackSize) {
                return i
            }
        }

        for (i in 0 until to.getSize()) {
            val item = to.getItem(i)

            if (item?.type?.isAir == true) {
                return i
            }
        }

        return -1
    }

    private fun getResult(leftOver: Int, amount: Int, cancelReason: InventoryTransferCancelReason) =
        InventoryTransferResult(leftOver, amount - leftOver, cancelReason)

    @Throws(ShadowInventoryTransactionException::class)
    fun transfer(itemStack: ItemStack, amount: Int): InventoryTransferResult {
        startTransaction()

        return transferUnattended(itemStack, amount)
    }

    @Throws(ShadowInventoryTransactionException::class)
    fun startTransaction() {
        from.startTransaction()
        to.startTransaction()
    }

    @Throws(ShadowInventoryTransactionException::class)
    fun performTransaction() {
        from.performTransaction()
        to.performTransaction()
    }

    @Throws(ShadowInventoryTransactionException::class)
    fun rollbackTransaction() {
        from.rollbackTransaction()
        to.rollbackTransaction()
    }

    fun transferUnattended(data: ItemStack, n: Int): InventoryTransferResult {
        var leftOver = n
        var i = 0

        while (i < from.getSize()) {
            val currentItem = from.getItem(i)

            if (currentItem == null || currentItem.type.isAir || !currentItem.isSimilar(data)) {
                i++
                continue
            }

            val transferResult = transferItem(currentItem, data, leftOver, i)
            leftOver = transferResult.first

            if (!transferResult.second) {
                i++
            }

            if (leftOver <= 0) break
        }

        return getResult(
            leftOver,
            n,
            if (leftOver > 0) InventoryTransferCancelReason.FROM_IS_EMPTY else InventoryTransferCancelReason.N_REACHED
        )
    }

    private fun transferItem(
        currentItem: ItemStack,
        data: ItemStack,
        leftOver: Int,
        slotIndex: Int
    ): Pair<Int, Boolean> {
        val slotFound = findSimilarSlot(data)
        if (slotFound < 0) {
            return leftOver to false
        }

        var targetItem = to.getItem(slotFound)
        if (targetItem == null || targetItem.type.isAir) {
            targetItem = null
        }

        val transferableAmount =
            calculateTransferableAmount(currentItem, targetItem, data, leftOver)
        if (transferableAmount == 0) return leftOver to false

        updateItems(currentItem, targetItem, transferableAmount, slotIndex, slotFound)

        return leftOver - transferableAmount to (currentItem.amount > 0)
    }

    private fun calculateTransferableAmount(
        currentItem: ItemStack,
        targetItem: ItemStack?,
        data: ItemStack,
        leftOver: Int
    ): Int {
        val targetAmount = targetItem?.amount ?: 0
        val maxStackSize = data.maxStackSize
        val remainingSpace = maxStackSize - targetAmount
        val transferableBySpace = minOf(remainingSpace, currentItem.amount)

        return if (leftOver > 0) {
            minOf(transferableBySpace, leftOver)
        } else {
            transferableBySpace
        }
    }

    private fun updateItems(
        currentItem: ItemStack,
        targetItem: ItemStack?,
        transferableAmount: Int,
        slotIndex: Int,
        slotFound: Int
    ) {
        currentItem.amount -= transferableAmount

        val updatedTarget = targetItem?.apply {
            amount += transferableAmount
        } ?: currentItem.clone().apply {
            amount = transferableAmount
        }

        from.setItem(slotIndex, currentItem)
        to.setItem(slotFound, updatedTarget)
    }


}
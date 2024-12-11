package dev.slne.augmented.common.gui.inventory.transfer

data class InventoryTransferResult(
    val leftOver: Int,
    val itemsMoved: Int,
    val cancelReason: InventoryTransferCancelReason
)
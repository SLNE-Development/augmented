package dev.slne.augmented.common.gui.inventory.exception

class ShadowInventoryTransactionException(override val message: String) :
    RuntimeException(message) {
    constructor() : this("Shadow inventory transaction failed")
}
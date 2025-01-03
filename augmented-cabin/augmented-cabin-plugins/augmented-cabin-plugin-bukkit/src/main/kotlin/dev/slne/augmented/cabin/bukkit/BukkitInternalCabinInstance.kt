package dev.slne.augmented.cabin.bukkit

import dev.slne.augmented.cabin.api.InternalCabinInstance

class BukkitInternalCabinInstance : InternalCabinInstance() {

    override val dataPath = cabinPlugin.dataPath

    override fun logInfo(message: String) {
        cabinPlugin.logger.info(message)
    }

    override fun logWarn(message: String) {
        cabinPlugin.logger.warning(message)
    }

    override fun logError(message: String) {
        cabinPlugin.logger.severe(message)
    }

    override fun shutdownCabinPlugin() {
        cabinPlugin.server.pluginManager.disablePlugin(cabinPlugin)
    }

}
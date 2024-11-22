package dev.slne.augmented.shop.bukkit

import dev.slne.augmented.common.database.bukkit.plugin.AugmentedDatabasePlugin
import dev.slne.augmented.common.database.core.persistence.AugmentedPersistence
import dev.slne.augmented.common.database.core.persistence.findAll
import dev.slne.augmented.common.database.core.persistence.sessionFactory
import dev.slne.augmented.common.database.core.persistence.withSession
import dev.slne.augmented.shop.core.CoreShop

class AugmentedShopPlugin : AugmentedDatabasePlugin() {

    override suspend fun onLoadAsync() {
        super.onLoadAsync()

        AugmentedPersistence.addAnnotatedClass(CoreShop::class)

        val shops = fetchShops()
        println(shops)
    }

    override suspend fun onEnableAsync() {
        super.onEnableAsync()
    }

    override suspend fun onDisableAsync() {
        super.onDisableAsync()
    }

    private suspend fun fetchShops(): List<CoreShop> = sessionFactory.withSession {
        it.findAll<CoreShop>()
    }
}
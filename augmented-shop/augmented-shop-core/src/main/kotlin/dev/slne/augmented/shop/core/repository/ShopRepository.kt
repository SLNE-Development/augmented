package dev.slne.augmented.shop.core.repository

import dev.slne.augmented.common.base.core.extensions.toObjectList
import dev.slne.augmented.common.database.core.persistence.sessionFactory
import dev.slne.augmented.common.database.core.persistence.upsert
import dev.slne.augmented.common.database.core.persistence.withSession
import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.core.CoreShop
import it.unimi.dsi.fastutil.objects.ObjectList

object ShopRepository {

    suspend fun saveShop(shop: Shop) = sessionFactory.withSession { session ->
        session.upsert(shop) { id !== null }
    }

    suspend fun deleteShop(shop: Shop) = sessionFactory.withSession { session ->
        session.remove(shop)
    }

    suspend fun findAllShops(): ObjectList<Shop> = sessionFactory.withSession { session ->
        val criteriaBuilder = session.criteriaBuilder

        val query = criteriaBuilder.createQuery(CoreShop::class.java)
        val root = query.from(CoreShop::class.java)

        session.createQuery(query.select(root)).resultList.toObjectList()
    }

}
package dev.slne.augmented.shop.core

import dev.slne.augmented.common.database.core.models.converter.UuidConverter
import dev.slne.augmented.shop.api.Shop
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import java.sql.Types
import java.util.*

@Entity
@Table(name = "augmented_shops")
open class CoreShop : Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    override var id: Long? = null

    @Column(name = "material")
    override var material: String? = null

    @Column(name = "shop_key", length = 36)
    @Convert(converter = UuidConverter::class)
    @JdbcTypeCode(Types.CHAR)
    override var shopKey: UUID? = null

    @Column(name = "shop_owner", length = 36)
    @Convert(converter = UuidConverter::class)
    @JdbcTypeCode(Types.CHAR)
    override var shopOwner: UUID? = null

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "augmented_shop_permitted_users",
        joinColumns = [JoinColumn(name = "shop_id")]
    )
    @Convert(converter = UuidConverter::class)
    @Column(name = "user_id", length = 36)
    @JdbcTypeCode(Types.CHAR)
    override var permittedUsers: Set<UUID>? = setOf()

    @Column(name = "location_server")
    override var server: String? = null

    @Column(name = "location_world")
    override var world: String? = null

    @Column(name = "location_x")
    override var x: Double? = null

    @Column(name = "location_y")
    override var y: Double? = null

    @Column(name = "location_z")
    override var z: Double? = null

    @Column(name = "sell_price")
    override var sellPrice: Double? = null

    @Column(name = "buy_price")
    override var buyPrice: Double? = null

    @Column(name = "buy_limit")
    override var buyLimit: Int? = null

    @Column(name = "sell_limit")
    override var sellLimit: Int? = null

    @Column(name = "sell_paused")
    override var sellPaused: Boolean? = null

    @Column(name = "buy_paused")
    override var buyPaused: Boolean? = null

    @Column(name = "stock_amount")
    override var stockAmount: Int? = null

    override fun toString(): String {
        return "CoreShop(id=$id, material=$material, shopKey=$shopKey, shopOwner=$shopOwner, permittedUsers=$permittedUsers, server=$server, world=$world, x=$x, y=$y, z=$z, sellPrice=$sellPrice, buyPrice=$buyPrice, buyLimit=$buyLimit, sellLimit=$sellLimit, sellPaused=$sellPaused, buyPaused=$buyPaused, stockAmount=$stockAmount)"
    }

}
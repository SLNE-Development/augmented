package dev.slne.augmented.shop.core

import dev.slne.augmented.common.base.core.block.BlockPosition
import dev.slne.augmented.common.database.core.models.converter.BlockPositionConverter
import dev.slne.augmented.common.database.core.models.converter.UuidConverter
import dev.slne.augmented.shop.api.Shop
import dev.slne.augmented.shop.api.shopManager
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import java.sql.Types
import java.util.*

@Entity
@Table(name = "augmented_shops")
class CoreShop() : Shop {

    constructor(
        material: String,
        shopOwner: UUID,
        server: String,
        world: UUID,
        location: BlockPosition
    ) : this() {
        this.shopKey = UUID.randomUUID()

        this.material = material
        this.shopOwner = shopOwner
        this.server = server
        this.world = world
        this.location = location
    }

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

    @Column(name = "location_world", length = 36)
    @Convert(converter = UuidConverter::class)
    @JdbcTypeCode(Types.CHAR)
    override var world: UUID? = null

    @Column(name = "location", length = 12)
    @Convert(converter = BlockPositionConverter::class)
    @JdbcTypeCode(Types.BINARY)
    override var location: BlockPosition? = null

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

    override fun add() = shopManager.addShop(this)
    override fun remove() = shopManager.removeShop(this)

    override suspend fun save() = shopManager.saveShop(this)
    override suspend fun delete() = shopManager.deleteShop(this)

    override fun toString(): String {
        return "CoreShop(id=$id, material=$material, shopKey=$shopKey, shopOwner=$shopOwner, permittedUsers=$permittedUsers, server=$server, world=$world, location=$location, sellPrice=$sellPrice, buyPrice=$buyPrice, buyLimit=$buyLimit, sellLimit=$sellLimit, sellPaused=$sellPaused, buyPaused=$buyPaused, stockAmount=$stockAmount)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CoreShop

        if (id != other.id) return false
        if (shopKey != other.shopKey) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0

        result = 31 * result + (shopKey?.hashCode() ?: 0)

        return result
    }

}
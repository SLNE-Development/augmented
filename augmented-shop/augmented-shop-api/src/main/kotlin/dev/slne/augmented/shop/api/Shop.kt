package dev.slne.augmented.shop.api

import jakarta.persistence.*

@Entity
@Table(name = "shops")
open class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "material")
    open var material: String? = null

    override fun toString(): String {
        return "Shop(id=$id, material=$material)"
    }
    
}
package com.example.paginationfilter.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "locations")
data class Location(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    var locationId: Long? = null,
    @Column(name = "street_address", length = 40)
    var streetAddress: String? = null,
    @Column(name = "postal_code", length = 12)
    var postalCode: String? = null,
    @Column(length = 30, nullable = false)
    var city: String,
    @Column(name = "state_province", length = 25)
    var stateProvince: String? = null,
    @Column(name = "country_id", length = 2)
    var countryId: String? = null
)

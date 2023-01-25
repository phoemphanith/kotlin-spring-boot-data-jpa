package com.example.paginationfilter.model

import jakarta.persistence.*

@Entity
@Table(name = "tutorials")
data class Tutorial(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String,
    var description: String,
    var published: Boolean
)

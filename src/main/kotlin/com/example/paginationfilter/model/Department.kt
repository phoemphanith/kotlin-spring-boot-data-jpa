package com.example.paginationfilter.model

import jakarta.persistence.*

@Entity
@Table(name = "departments")
data class Department(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    var departmentId: Long? = null,
    @Column(name = "department_name", nullable = false, length = 30)
    var departmentName: String,
    @Column(name = "manager_id")
    var managerId: Long? = null,
    @Column(name = "location_id")
    var locationId: Long? = null,
)

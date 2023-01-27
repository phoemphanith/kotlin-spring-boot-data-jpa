package com.example.paginationfilter.model

import jakarta.persistence.*
import org.springframework.context.annotation.Primary

@Entity
@Table(name = "jobs")
//CREATE TABLE IF NOT EXISTS `jobs` (
//`JOB_ID` varchar(10) NOT NULL DEFAULT '',
//`JOB_TITLE` varchar(35) NOT NULL,
//`MIN_SALARY` decimal(6,0) DEFAULT NULL,
//`MAX_SALARY` decimal(6,0) DEFAULT NULL,
//PRIMARY KEY (`JOB_ID`)
//) ENGINE=MyISAM DEFAULT CHARSET=latin1;
data class Job(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "job_id", nullable = false, unique = true)
    var jobId: String,
    @Column(name = "job_title", nullable = false)
    var jobTitle: String,
    @Column(name = "min_salary")
    var minSalary: Double? = null,
    @Column(name = "max_salary")
    var maxSalary: Double? = null
)

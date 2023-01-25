package com.example.paginationfilter.repository

import com.example.paginationfilter.model.Tutorial
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TutorialRepository: JpaRepository<Tutorial, Long> {
    fun findByPublished(published: Boolean, pageable: Pageable): Page<Tutorial>
    fun findByTitleContaining(title: String, pageable: Pageable): Page<Tutorial>
}
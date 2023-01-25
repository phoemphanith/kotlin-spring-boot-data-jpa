package com.example.paginationfilter.controller

import com.example.paginationfilter.model.Tutorial
import com.example.paginationfilter.repository.TutorialRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TutorialController(private val repository: TutorialRepository)
{
    @GetMapping("/tutorials")
    fun getAllTutorials(
        @RequestParam(required = false) title: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "3") size: Int
    ): ResponseEntity<Map<String, Any>>{
        try {
            var tutorials = mutableListOf<Tutorial>()
            val paging: Pageable = PageRequest.of(page, size)
            var pageTutorial: Page<Tutorial>
            if(title != null)
                pageTutorial = repository.findByTitleContaining(title, paging)
            else
                pageTutorial = repository.findAll(paging)

            tutorials = pageTutorial.content

            val response = HashMap<String, Any>()
            response["tutorials"] = tutorials
            response["currentPage"] = pageTutorial.number //Number of current page
            response["totalItems"] = pageTutorial.totalElements //All elements
            response["totalPages"] = pageTutorial.totalPages

            return ResponseEntity(response, HttpStatus.OK)
        } catch (e: Exception){
            return ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/tutorials/published")
    fun getAllPublishedTutorials(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "3") size: Int
    ): ResponseEntity<Map<String, Any>>{
        return try {
            val paging: Pageable = PageRequest.of(page, size)
            val pageTutorial: Page<Tutorial> = repository.findByPublished(true, paging)

            val response = HashMap<String, Any>()
            response["tutorials"] = pageTutorial.content
            response["currentPage"] = pageTutorial.number
            response["totalItems"] = pageTutorial.totalElements
            response["totalPages"] = pageTutorial.totalPages

            ResponseEntity(response, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
package com.example.paginationfilter.controller

import com.example.paginationfilter.repository.EmployeeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("testing")
class MainController (private val repository: EmployeeRepository){
    // Restricting and Sorting data
    @GetMapping("/restricting-sorting")
    fun indexOne(): ResponseEntity<Map<String, Any?>> =
        try {
//            val employee = repository.getEmployeeSalaryNotBetweenAndDepartmentIdNotIn(10000.0,15000.0, listOf(30.0, 100.0), PageRequest.of(0, 5))
//            val employee = repository.findByHireDateBetween(LocalDate.parse("1987-01-01"), LocalDate.parse("1987-12-31"))
//            val employee = repository.findByFirstNameContainingAndFirstNameContaining("b", "c")
//            val employee = repository.getEmployeeIsItPROGOrShClerk(listOf("IT_PROG", "SH_CLERK"), listOf(4500.0, 10000.0, 15000.0), PageRequest.of(0,2))
//            val employee = repository.getEmployeeLastNameHaveSixDigit()
//            val employee = repository.getEmployeeStartWithEAtThird("e")
            val employee = repository.findByLastNameIn(listOf("BLAKE", "SCOTT", "KING", "FORD"))
            ResponseEntity(mapOf("response" to employee), HttpStatus.OK)
        }catch (e: Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    // Aggregate Functions and Group by
    @GetMapping("/aggregate-group-by")
    fun indexTwo(): ResponseEntity<Map<String, Any?>> =
        try {
//            val countAvailableJob = repository.countAvailableJob()
//            val response = repository.sumPayableSalary()
//            val response = repository.findMinSalary()
//            val response = repository.findMaxSalaryByJobId("IT_PROG")
//            val response = repository.findAvgSalaryByDepartmentId(90)
//            val response = repository.findMaxMinSumAvgEmployee()
//            val response = repository.countEmployeeEachJob(12000)
//            val response = repository.findSalaryBetweenLowAndHigh()
//            val response = repository.findMinSalaryOfManager()
            val response = repository.findPayableEachDepartment()
            ResponseEntity(mapOf("response" to response), HttpStatus.OK)
        }catch (e: Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    //MySQL Subquery
    @GetMapping("/msql-subquery")
    fun indexThree(): ResponseEntity<Map<String, Any?>> =
        try {
//            val response = repository.findEmployeeSalaryHigherBull()
//            val response = repository.findEmployeeByDepartmentName("IT")
//            val response = repository.findEmployeeByCountry("US and")
//            val response = repository.findManagerEmployee(PageRequest.of(0, 5))
//            val  response = repository.findEmployeeSalaryGreaterThanAvg()
//            val response = repository.findEmployeeEqualJobMinSalary()
            val response = repository.findEmployeeSalaryGreaterThanAvg("IT")
            ResponseEntity(mapOf("response" to response), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }
}
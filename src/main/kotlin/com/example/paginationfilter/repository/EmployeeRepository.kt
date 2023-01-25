package com.example.paginationfilter.repository

import com.example.paginationfilter.model.Employee
import com.example.paginationfilter.response.EmployeeAvgSalary
import com.example.paginationfilter.response.EmployeeMinMaxSalary
import com.example.paginationfilter.response.EmployeeMonthlySalary
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EmployeeRepository: JpaRepository<Employee, Long> {
    @Query("select distinct e.department_id from employees e", nativeQuery = true)
    fun findAllDistinctDepartmentId(): List<Double>
    @Query("select sum(e.salary) from employees e", nativeQuery = true)
    fun findTotalSalaryPayable(): Double
    @Query(nativeQuery = true)
    fun findMinMaxSalary(): EmployeeMinMaxSalary
    @Query(nativeQuery = true)
    fun getAvgSalary(): EmployeeAvgSalary
    @Query("select count(distinct e.job_id) from employees e", nativeQuery = true)
    fun getCountJobAvailable(): Int
    @Query("select upper(e.first_name) from employees e", nativeQuery = true)
    fun getFirstnameInUpperCase(): List<String>
    @Query("select substring(e.first_name, 1, 3) from employees e", nativeQuery = true)
    fun getThreeDigitFirstName(): List<String>
    @Query("select concat(e.first_name, ' ', e.last_name) from employees e", nativeQuery = true)
    fun getEmployeesName(): List<String>
    @Query("select trim(e.first_name) from employees e", nativeQuery = true)
    fun getTrimFirstname(): List<String>
    @Query(nativeQuery = true)
    fun getEmployeeMonthlySalary(pageable: Pageable): Page<EmployeeMonthlySalary>
}
package com.example.paginationfilter.repository

import com.example.paginationfilter.model.Employee
import com.example.paginationfilter.response.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface EmployeeRepository: JpaRepository<Employee, Long> {
    // Basic Query
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

    // Restricting and Sorting data
    fun findBySalaryBetween(minSalary: Double, maxSalary: Double): List<Employee>
    fun findByDepartmentIdIn(department: Collection<Double>): List<Employee>
    @Query(
        value = "select * from employees e where e.salary not between ?1 and ?2 and e.department_id in ?3",
        countQuery = "select count(*) as count from employees e where e.salary not between ?1 and ?2 and e.department_id in ?3",
        nativeQuery = true
    )
    fun getEmployeeSalaryNotBetweenAndDepartmentIdNotIn(
        minSalary: Double,
        maxSalary: Double,
        department: Collection<Double>,
        pageable: Pageable
    ): Page<Employee>
    fun findByHireDateBetween(fromDate: LocalDate, toDate: LocalDate): List<Employee>
    // Query to display all employees who have both "b" and "c" in their first name
    fun findByFirstNameContainingAndFirstNameContaining(firstParam: String, secondParam: String): List<Employee>
    @Query("select e from Employee e where e.jobId in ?1 and e.salary not in ?2")
    fun getEmployeeIsItPROGOrShClerk(jobIds: Collection<String>, salary: Collection<Double>, pageable: Pageable): Page<Employee>
    @Query("select e.lastName from Employee e where e.lastName like '______'")
    fun getEmployeeLastNameHaveSixDigit(): List<String>
    @Query("select e from Employee e where e.lastName like concat('__',?1,'%')")
    fun getEmployeeStartWithEAtThird(letter: String): List<Employee>
    fun findByLastNameIn(lastNames: Collection<String>): List<Employee>
    // Aggregate Functions and Group by
    @Query("select count(distinct e.jobId) from Employee e")
    fun countAvailableJob(): Int
    @Query("select sum(e.salary) from Employee e")
    fun sumPayableSalary(): Double
    @Query("select min(e.salary) from Employee e")
    fun findMinSalary(): Double
    @Query("select max(e.salary) from Employee e where e.jobId like ?1")
    fun findMaxSalaryByJobId(jobId: String): Double
    @Query("select round(avg(e.salary),2) from Employee e where e.departmentId = ?1")
    fun findAvgSalaryByDepartmentId(departmentId: Int): Double
    @Query(nativeQuery = true)
    fun findMaxMinSumAvgEmployee(): EmployeeSalaryRecord
    @Query(nativeQuery = true)
    fun countEmployeeEachJob(): List<EmployeeCountEachJob>
    @Query("select max(e.salary) - min(e.salary) difference from Employee e")
    fun findSalaryBetweenLowAndHigh(): Double
    @Query(nativeQuery = true)
    fun findMinSalaryOfManager(): List<EmployeeManagerSalary>
    @Query(nativeQuery = true)
    fun findPayableEachDepartment(): List<EmployeeDepartmentSalary>
}
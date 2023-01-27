package com.example.paginationfilter.controller

import com.example.paginationfilter.model.Employee
import com.example.paginationfilter.repository.EmployeeRepository
import com.example.paginationfilter.response.EmployeeResponse
import com.example.paginationfilter.response.EmployeeSalaryPerResponse
import com.example.paginationfilter.response.EmployeeSalarySortResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class EmployeeController(private val repository: EmployeeRepository) {
    // Basic Select Statement
    @GetMapping("/employees")
    fun getFirstNameLastName(
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "0") page: Int
    ): ResponseEntity<Map<String, Any>> =
        try {
            val paging: Pageable = PageRequest.of(page, size)
            val employees: Page<EmployeeResponse> = repository.findAll(paging).map { it: Employee ->
                EmployeeResponse(firstName = it.firstName!!, lastName = it.lastName)
            }

            val response = HashMap<String, Any>()
            response["employees"] = employees.content
            response["currentPage"] = employees.number
            response["totalItems"] = employees.totalElements
            response["totalPages"] = employees.totalPages

            ResponseEntity(response, HttpStatus.OK)
        }catch (e: Exception){
            val response = HashMap<String, Any>()
            response["message"] = e.message!!
            ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/unique-dp-id")
    fun getEmployeesByUniqueDpId(): ResponseEntity<Map<String, Any>> =
        try {
            val departmentId: List<Any> = repository.findAllDistinctDepartmentId()

            val response = HashMap<String, Any>()
            response["departmentId"] = departmentId

            ResponseEntity(response, HttpStatus.OK)
        }catch (e: Exception){
            val response = HashMap<String, Any>()
            response["message"] = e.message!!
            ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/sort-firstname-des")
    fun getEmployeesSortByFirstname(): ResponseEntity<Map<String, Any?>> =
        try {
            val employee: List<Employee> = repository.findAll(Sort.by("firstName").descending())

            val response = HashMap<String, Any>()
            response["employees"] = employee

            ResponseEntity(response, HttpStatus.OK)
        }catch (e: Exception){
            val response = HashMap<String, Any>()
            response["message"] = e.message!!
            ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/employee-salary-in-per")
    fun getEmployeeSalaryPer(): ResponseEntity<Map<String, Any?>> =
        try {
            val employee: List<Any> = repository.findAll().map { employee: Employee ->
                EmployeeSalaryPerResponse(
                    employee.firstName!!,
                    employee.lastName,
                    employee.salary!!,
                    employee.salary!!.times(0.15)
                )
            }

            val response = HashMap<String, Any>()
            response["employees"] = employee

            ResponseEntity(response, HttpStatus.OK)
        }catch (e: Exception){
            val response = HashMap<String, Any>()
            response["message"] = e.message!!
            ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/sort-salary")
    fun getEmployeeSortSalary(): ResponseEntity<Map<String, Any?>> =
        try {
            val employee = repository.findAll(Sort.by("salary").descending())
                .map { employee: Employee ->
                    EmployeeSalarySortResponse(
                        employeeId = employee.employeeId!!,
                        name = "${employee.firstName} ${employee.lastName}",
                        salary = employee.salary
                    )
                }
            val response = HashMap<String, Any>()
            response["employees"] = employee
            ResponseEntity(response, HttpStatus.OK)
        }catch (e: Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/total-salary-payable")
    fun getTotalPayableSalary(): ResponseEntity<Map<String, Any?>> =
        try {
            val totalSalary = repository.findTotalSalaryPayable()
            ResponseEntity(mapOf("total" to totalSalary), HttpStatus.OK)
        }catch (e: Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/min-max-salary")
    fun getMinMaxSalary(): ResponseEntity<Map<String, Any?>> =
        try {
            val minMaxSalary = repository.findMinMaxSalary()
            ResponseEntity(mapOf("response" to minMaxSalary), HttpStatus.OK)
        }catch (e: Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/avg-salary")
    fun getAvgSalary(): ResponseEntity<Map<String, Any?>> =
        try {
            val avgSalary = repository.getAvgSalary()
            ResponseEntity(mapOf("response" to avgSalary), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/count-available-job")
    fun getCountAvailableJob(): ResponseEntity<Map<String, Any?>> =
        try {
            val countAvailableJob = repository.getCountJobAvailable()
            ResponseEntity(mapOf("count_available_job" to countAvailableJob), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/firstname-in-uppercase")
    fun getFirstNameInUpperCase(): ResponseEntity<Map<String, Any?>> =
        try {
            val listFirstnameInUpper = repository.getFirstnameInUpperCase()
            ResponseEntity(mapOf("list_all_firstname" to listFirstnameInUpper), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/3-digit-firstname")
    fun getThreeDigitFirstname(): ResponseEntity<Map<String, Any?>> =
        try {
            val firstNames = repository.getThreeDigitFirstName()
            ResponseEntity(mapOf("first_names" to firstNames), HttpStatus.OK)
        } catch (e :Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/employee-names")
    fun getEmployeeNames(): ResponseEntity<Map<String, Any?>> =
        try {
            val employeeNames = repository.getEmployeesName()
            ResponseEntity(mapOf("employee_names" to employeeNames), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/firstname-trim")
    fun getTrimFirstname(): ResponseEntity<Map<String, Any?>> =
        try {
            val trimFirstname = repository.getTrimFirstname()
            ResponseEntity(mapOf("trim_first_name" to trimFirstname), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/monthly-salary")
    fun getEmployeeMonthlySalary(): ResponseEntity<Map<String, Any?>> =
        try {
            val paging = PageRequest.of(0, 10)
            val employees = repository.getEmployeeMonthlySalary(paging)
            ResponseEntity(mapOf("response" to employees.content), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/salary")
    fun getEmployeeBetweenSalary(
        @RequestParam(defaultValue = "0") minSalary: Double?,
        @RequestParam(defaultValue = "0") maxSalary: Double?
    ): ResponseEntity<Map<String, Any?>> =
        try {
            val employees: List<Employee>  =
                if(minSalary!! > 0 && maxSalary!! > 0)
                    repository.findBySalaryBetween(minSalary, maxSalary)
                else
                    repository.findAll()

            ResponseEntity(mapOf("employees" to employees), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/department")
    fun getEmployeeDepartment(
        @RequestParam(defaultValue = "") departmentIds: String?
    ): ResponseEntity<Map<String, Any?>> =
        try {
            val ids: List<Double>? = departmentIds?.split(",")?.map { item -> item.toDouble() }
            val employee = ids?.let { repository.findByDepartmentIdIn(it) }
            ResponseEntity(mapOf("response" to employee), HttpStatus.OK)
        }catch (e :Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @GetMapping("/employees/salary-department")
    fun getEmployeeSalaryDepartmentId(): ResponseEntity<Map<String, Any?>> =
        try {
            val employee = repository
                .getEmployeeSalaryNotBetweenAndDepartmentIdNotIn(
                    10000.0,
                    15000.0,
                    listOf(30.0, 100.0),
                    PageRequest.of(0, 5)
                )

            ResponseEntity(mapOf("response" to employee), HttpStatus.OK)
        }catch (e: Exception){
            ResponseEntity(mapOf("message" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }

}
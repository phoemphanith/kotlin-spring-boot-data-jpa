package com.example.paginationfilter.response

data class EmployeeResponse(
    var firstName: String,
    var lastName: String)

data class EmployeeMinMaxSalary(
    var minSalary: Double,
    var maxSalary: Double)

data class EmployeeSalaryPerResponse(
    var firstName: String,
    var lastName: String,
    var salary: Double,
    var PF: Double)

data class EmployeeSalarySortResponse(
    var employeeId: Long,
    var name: String,
    var salary: Double?)

data class EmployeeAvgSalary(
    var avgSalary: Double,
    var totalEmployees: Double)

data class EmployeeMonthlySalary(
    var firstName: String,
    var lastName: String,
    var monthlySalary: Double)

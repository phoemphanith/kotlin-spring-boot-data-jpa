package com.example.paginationfilter.model

import com.example.paginationfilter.response.EmployeeAvgSalary
import com.example.paginationfilter.response.EmployeeMinMaxSalary
import com.example.paginationfilter.response.EmployeeMonthlySalary
import jakarta.persistence.*
import java.time.LocalDate

@NamedNativeQueries(value = [
    //get the maximum and minimum salary from employees table
    NamedNativeQuery(
        name = "Employee.findMinMaxSalary",
        query = "select min(e.salary) as min_salary, max(e.salary) as max_salary from employees e",
        resultSetMapping = "Mapping.EmployeeMinMaxSalary"),
    //get the average salary and number of employees in the employees table
    NamedNativeQuery(
        name = "Employee.getAvgSalary",
        query = "select avg(e.salary) as avgSalary, count(*) as totalEmployees from employees e",
        resultSetMapping = "Mapping.EmployeeAvgSalary"),
    //Get employee monthly salary with pagination
    NamedNativeQuery(
        name = "Employee.getEmployeeMonthlySalary",
        query = "select e.first_name, e.last_name, round(e.salary/12,2) as 'monthly_salary' from employees e",
        resultSetMapping = "Mapping.EmployeeMonthlySalary"),
    NamedNativeQuery(
        name = "Employee.getEmployeeMonthlySalary.count",
        query = "select count(*) as count from employees e",
        resultSetMapping = "Mapping.EmployeeMonthlySalary.count")
])

@SqlResultSetMappings(value = [
    //get the maximum and minimum salary from employees table
    SqlResultSetMapping(
        name = "Mapping.EmployeeMinMaxSalary",
        classes = [ConstructorResult(
            targetClass = EmployeeMinMaxSalary::class,
            columns = [ColumnResult(name = "min_salary"), ColumnResult(name = "max_salary")]
        )]),
    //get the average salary and number of employees in the employees table
    SqlResultSetMapping(
        name = "Mapping.EmployeeAvgSalary",
        classes = [ConstructorResult(
            targetClass = EmployeeAvgSalary::class,
            columns = [ColumnResult(name = "avgSalary"), ColumnResult(name = "totalEmployees")]
        )]),
    //Get employee monthly salary with pagination
    SqlResultSetMapping(
        name = "Mapping.EmployeeMonthlySalary",
        classes = [ConstructorResult(
            targetClass = EmployeeMonthlySalary::class,
            columns = [ColumnResult(name = "first_name"), ColumnResult(name = "last_name"), ColumnResult(name = "monthly_salary")]
        )]),
    SqlResultSetMapping(
        name = "Mapping.EmployeeMonthlySalary.count",
        classes = [ConstructorResult(
            targetClass = Long::class,
            columns = [ColumnResult(name = "count", type = Long::class)]
        )]),
])

@Entity
@Table(name = "employees")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    var employeeId: Long? = 0,
    @Column(name = "first_name", length = 20)
    var firstName: String? = null,
    @Column(name = "last_name", length = 20, nullable = false)
    var lastName: String,
    @Column(length = 25, nullable = false, unique = true)
    var email: String,
    @Column(name = "phone_number", length = 20)
    var phoneNumber: String? = null,
    @Column(name = "hire_date")
    var hireDate: LocalDate,
    @Column(name = "job_id", length = 10)
    var jobId: String,
    var salary: Double? = null,
    var commissionPct: Double? = null,
    var managerId: Double? = null,
    var departmentId: Double? = null)
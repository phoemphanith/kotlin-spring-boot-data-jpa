package com.example.paginationfilter.model

import com.example.paginationfilter.response.*
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
        resultSetMapping = "Mapping.EmployeeMonthlySalary.count"),
    //Query to get the highest, lowest, sum, and average salary of all employees
    NamedNativeQuery(
        name = "Employee.findMaxMinSumAvgEmployee",
        query = "select min(e.salary) as lowest_salary, max(e.salary) as highest_salary, sum(e.salary) as total_salary, round(avg(e.salary),2) as avg_salary from employees e",
        resultSetMapping = "Mapping.EmployeeSalaryRecord"
    ),
    //Query to get the number of employees with the same job
    NamedNativeQuery(
        name = "Employee.countEmployeeEachJob",
        query = "select e.job_id as job_id, count(*) as total_employee, max(e.salary) as max_salary from employees e group by e.job_id having max(e.salary) >= 12000",
        resultSetMapping = "Mapping.EmployeeCountEachJob"
    ),
    // Query to find the manager ID and the salary of the lowest-paid employee for that manager
    NamedNativeQuery(
        name = "Employee.findMinSalaryOfManager",
        query = "select e.manager_id, min(e.salary) as min_salary from employees e where e.manager_id is not null group by e.manager_id order by min(e.salary) desc",
        resultSetMapping = "Mapping.EmployeeManagerSalary"
    ),
    // Query to get the department ID and the total salary payable in each department
    NamedNativeQuery(
        name = "Employee.findPayableEachDepartment",
        query = "select e.department_id, sum(e.salary) as payable from employees e group by e.department_id order by sum(e.salary) desc",
        resultSetMapping = "Mapping.EmployeeDepartmentSalary"
    )
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
    //Query to get the highest, lowest, sum, and average salary of all employees
    SqlResultSetMapping(
        name = "Mapping.EmployeeSalaryRecord",
        classes = [
            ConstructorResult(
                targetClass = EmployeeSalaryRecord::class,
                columns = [
                    ColumnResult(name = "lowest_salary"),
                    ColumnResult(name = "highest_salary"),
                    ColumnResult(name = "total_salary"),
                    ColumnResult(name = "avg_salary")
                ]
            )
        ]
    ),
    // Query to get the number of employees with the same job
    SqlResultSetMapping(
        name = "Mapping.EmployeeCountEachJob",
        classes = [
            ConstructorResult(
                targetClass = EmployeeCountEachJob::class,
                columns = [
                    ColumnResult(name = "job_id"),
                    ColumnResult(name = "total_employee", type = Int::class),
                    ColumnResult(name = "max_salary")
                ]
            )
        ]
    ),
    // Query to find the manager ID and the salary of the lowest-paid employee for that manager
    SqlResultSetMapping(
        name = "Mapping.EmployeeManagerSalary",
        classes = [
            ConstructorResult(
                targetClass = EmployeeManagerSalary::class,
                columns = [
                    ColumnResult(name = "manager_id"),
                    ColumnResult(name = "min_salary")
                ]
            )
        ]
    ),
    // Query to get the department ID and the total salary payable in each department
    SqlResultSetMapping(
        name = "Mapping.EmployeeDepartmentSalary",
        classes = [
            ConstructorResult(
                targetClass = EmployeeDepartmentSalary::class,
                columns = [
                    ColumnResult(name = "department_id"),
                    ColumnResult(name = "payable")
                ]
            )
        ]
    )
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
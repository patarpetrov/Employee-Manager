package com.example.employeeManager.repository;


import com.example.employeeManager.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
    public interface EmployeeRepository extends JpaRepository<Employee, Long> {
     Optional<Employee> findEmployeeByEmail(String email);
     //Optional<Employee> findEmployeeByID(Long id);

    @Query(value = "SELECT employee.full_name, employee.id, employee.email, employee.phone_num, employee.monthly_salary, employee.date_of_birth " +
            "FROM employee " +
            "EXCEPT " +
            "(SELECT employee.full_name, employee.id, employee.email, employee.phone_num, employee.monthly_salary, employee.date_of_birth " +
            "FROM employee INNER JOIN task_employee_table ON task_employee_table.employee_id = employee.id " +
            "WHERE task_employee_table.task_id = ?1)",
            nativeQuery = true)
    Collection<Employee> findEmployeeWITHOUTTaskID(Long id);

    @Query(value = "SELECT employee.full_name, employee.id, employee.email, employee.phone_num, employee.monthly_salary, employee.date_of_birth" +
            " FROM employee INNER JOIN task_employee_table ON task_employee_table.employee_id = employee.id " +
            "WHERE task_employee_table.task_id = ?1", nativeQuery = true)
    Collection<Employee> findEmployeeWITHTaskID(Long id);

    @Query(value = "SELECT employee.full_name, employee.id, employee.email, employee.phone_num, employee.monthly_salary, employee.date_of_birth " +
            "FROM employee " +
            "INNER JOIN task_employee_table ON task_employee_table.employee_id = employee.id " +
            "inner join task ON task_employee_table.task_id = task.id " +
            "WHERE task.due_date BETWEEN TO_DATE(?1,'YYYYMMDD') and TO_DATE(?2,'YYYYMMDD') " +
            "GROUP BY employee.id  " +
            "ORDER BY COUNT(employee.id) " +
            "DESC LIMIT (5)" , nativeQuery = true)
    Collection<Employee> findBestEmployees(String dateold, String datenew);
    @Query(value = "SELECT COUNT(employee.id) FROM employee", nativeQuery = true)
    int findNumberOfEmployees();

    @Query(value= "SELECT * FROM Employee INNER JOIN users ON users.employee_id = employee.id WHERE users.id = ?1", nativeQuery = true)
    Employee findLoggedEmployee(Long id);




}

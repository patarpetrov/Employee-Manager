package com.example.assignmentinternshipPetarPetrov.repository;


import com.example.assignmentinternshipPetarPetrov.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "SELECT COUNT(task.id) FROM task", nativeQuery = true)
    int findNumberOfTasks();

    @Query(value = "SELECT COUNT(task.id) FROM task WHERE task.due_date BETWEEN TO_DATE(?1,'YYYYMMDD') and TO_DATE(?2,'YYYYMMDD')", nativeQuery = true)
    int findNumberOfTasksLastMonth(String dateold, String datenew);

    @Query(value = "SELECT COUNT(task.id) FROM task WHERE task.due_date < TO_DATE(?1,'YYYYMMDD')", nativeQuery = true)
    int findNumberOfTasksExpired(String datenew);

    @Query(value = "SELECT task.id, task.title, task.description, task.due_date FROM task INNER JOIN task_employee_table ON task_employee_table.task_id = task.id INNER JOIN employee ON task_employee_table.employee_id = employee.id WHERE employee.email = ?1", nativeQuery = true)
    Collection<Task> findTasksByEmployeeEmail(String email);

    @Query(value = "SELECT COUNT(task.id) FROM task INNER JOIN task_employee_table ON task_employee_table.task_id = task.id INNER JOIN employee ON task_employee_table.employee_id = employee.id WHERE employee.email = ?1 AND task.due_date BETWEEN TO_DATE(?2,'YYYYMMDD') and TO_DATE(?3,'YYYYMMDD')", nativeQuery = true)
    Collection<Task> findTasksLastMonthByEmployeeEmail(String email, String dateold, String datenew);
}

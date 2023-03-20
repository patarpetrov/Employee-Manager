package com.example.assignmentinternshipPetarPetrov.service;


import com.example.assignmentinternshipPetarPetrov.entity.Employee;
import com.example.assignmentinternshipPetarPetrov.repository.EmployeeRepository;
import com.example.assignmentinternshipPetarPetrov.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class StatisticService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    @Autowired
    public StatisticService(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }
    public Collection<Employee> findBestEmployees() {
        LocalDate datenow = LocalDate.now();
        LocalDate dateold = datenow.minusMonths(1);
        System.out.println(datenow.toString().replace("-", ""));
        System.out.println(dateold.toString().replace("-", ""));
        return employeeRepository.findBestEmployees(dateold.toString().replace("-", ""),
                datenow.toString().replace("-", ""));
    }

    public int numberOfTasks() {
        return taskRepository.findNumberOfTasks();
    }
    public int findNumberOfTasksLastMonth(){
        LocalDate datenow = LocalDate.now();
        LocalDate dateold = datenow.minusMonths(1);
        return taskRepository.findNumberOfTasksLastMonth(dateold.toString().replace("-", ""), datenow.toString().replace("-", ""));
    }

    public int numberOfEmoloyee() {
        return employeeRepository.findNumberOfEmployees();
    }
    public int numberofTaskExpired(){
        LocalDate datenow = LocalDate.now();
        return taskRepository.findNumberOfTasksExpired(datenow.toString().replace("-", ""));
    }
}


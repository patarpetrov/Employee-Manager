package com.example.assignmentinternshipPetarPetrov.service;


import com.example.assignmentinternshipPetarPetrov.entity.Department;
import com.example.assignmentinternshipPetarPetrov.entity.Employee;
import com.example.assignmentinternshipPetarPetrov.repository.EmployeeRepository;
import com.example.assignmentinternshipPetarPetrov.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    public List<Employee> getEmployees(){
        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
    public void addNewEmployee(Employee employee) {
        Optional<Employee> employeeByEmail = employeeRepository.findEmployeeByEmail(employee.getEmail());
        if (employeeByEmail.isPresent()){
            throw new IllegalStateException("email taken");
        }
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        boolean exists = employeeRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("student with id" + id + " does not exist");
        }
        employeeRepository.deleteById(id);
    }

    public Employee getEmployee(Long id) {
        Optional<Employee> employeeById = employeeRepository.findById(id);
        if (!employeeById.isPresent()){
            throw new IllegalStateException("Can`t find employee with this id");
        }
        return employeeById.get();
    }
    public void updateEmployee(Employee employee, Long id) {
        Employee old = getEmployee(id);
        //TODO remove the if statements
        if (!employee.getEmail().equals(old.getEmail())){
            old.setEmail(employee.getEmail());
        }
        if (!employee.getFullName().equals(old.getFullName())){
            old.setFullName(employee.getFullName());
        }
        if (!employee.getMonthlySalary().equals(old.getMonthlySalary())){
            old.setMonthlySalary(employee.getMonthlySalary());
        }
        if (!employee.getPhoneNum().equals(old.getMonthlySalary())){
            old.setPhoneNum(employee.getPhoneNum());
        }
        if (employee.getDateOfBirth() != null){
            old.setDateOfBirth(employee.getDateOfBirth());
        }
        employeeRepository.save(old);
    }

    public Collection<Employee> getEmployeesWithoutTask(Long id) {
       return employeeRepository.findEmployeeWITHOUTTaskID(id);
    }

    public Collection<Employee> getEmployeesWithTask(Long id) {
        return employeeRepository.findEmployeeWITHTaskID(id);
    }

    //TODO
//    public void addDepartment() {
//        List<Employee> employeeList = new ArrayList<>();
//        Employee employee1 = new Employee("Petar Petrov",
//                "petar.petrov@gmail.com",
//                "123", "1600",
//                LocalDate.now());
//        employeeRepository.save(employee1);
//
//        employeeList.add(employee1);
//        Department department = new Department("Admin", employeeList);
//
//        employee1.setDepartment(department);
//        employeeRepository.save(employee1);
//
//
//        LocalDate datenow = LocalDate.now();
//        LocalDate dateold = datenow.minusMonths(1);
//        taskRepository.findTasksLastMonthByEmployeeEmail(employee1.getEmail(), dateold.toString().replace("-", ""), datenow.toString().replace("-", ""));
//    }
}

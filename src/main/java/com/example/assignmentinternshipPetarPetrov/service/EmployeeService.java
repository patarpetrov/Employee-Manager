package com.example.assignmentinternshipPetarPetrov.service;


import com.example.assignmentinternshipPetarPetrov.entity.Employee;
import com.example.assignmentinternshipPetarPetrov.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
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
    @Transactional
    public void updateEmployee(Employee employee, Long id) {
        Employee old = getEmployee(id);
        if (employee.getEmail() != old.getEmail()){
            old.setEmail(employee.getEmail());
        }
        if (employee.getFullName() != old.getFullName()){
            old.setFullName(employee.getFullName());
        }
        if (employee.getMonthlySalary() != old.getMonthlySalary()){
            old.setMonthlySalary(employee.getMonthlySalary());
        }
        if (employee.getPhoneNum() != old.getMonthlySalary()){
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
}

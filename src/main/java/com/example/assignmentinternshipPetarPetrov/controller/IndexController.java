package com.example.assignmentinternshipPetarPetrov.controller;

import com.example.assignmentinternshipPetarPetrov.entity.Employee;

import com.example.assignmentinternshipPetarPetrov.entity.Task;
import com.example.assignmentinternshipPetarPetrov.repository.EmployeeRepository;
import com.example.assignmentinternshipPetarPetrov.repository.TaskRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Optional;

@Controller
public class IndexController {
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    public IndexController(EmployeeRepository employeeRepository, TaskRepository taskRepository){
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }
    @GetMapping("/index")
    public String getEmployee(Model model, @AuthenticationPrincipal User user) {
        if (user == null){
            return "redirect:/index";
        }
        if (user.getAuthorities().toString().equals("[ROLE_USER]")){
            Optional<Employee> employee = employeeRepository.findEmployeeByEmail(user.getUsername());
            Collection<Task> tasks = taskRepository.findTasksByEmployeeEmail(user.getUsername().toString());
            if(employee.isPresent()){
                model.addAttribute("tasks", tasks);
                model.addAttribute("employee", employee.get());
            }
            else{
                //user has been deleted
                return "error";
            }
            return "userindex";
        }
        return "index";
    }
}

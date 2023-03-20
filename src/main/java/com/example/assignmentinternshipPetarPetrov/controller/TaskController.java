package com.example.assignmentinternshipPetarPetrov.controller;


import com.example.assignmentinternshipPetarPetrov.entity.Employee;
import com.example.assignmentinternshipPetarPetrov.entity.Task;
import com.example.assignmentinternshipPetarPetrov.service.EmployeeService;
import com.example.assignmentinternshipPetarPetrov.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

@Controller
public class TaskController {
    private final TaskService taskService;
    private final EmployeeService employeeService;
    @Autowired
    public TaskController(TaskService taskService, EmployeeService employeeService){
        this.taskService = taskService;
        this.employeeService = employeeService;
    }
    @GetMapping("/task")
    public String getTasks(Model model){
        //System.out.println(employeeService.getEmployees().get(1).toString());
        model.addAttribute("tasks", taskService.getTasks());
        return "tasks";
    }
    @GetMapping("/task/add")
    public String getAddTask(Model model){
        //taskService.addNewTask();
        List employees = employeeService.getEmployees();
        //System.out.println(employees.get(0).toString());
        model.addAttribute("employee", employees);
        model.addAttribute("task", new Task());
        return "AddTask";
    }
    @PostMapping("/task/add")
    public ModelAndView addTask(@ModelAttribute Task task){
        System.out.println(task.toString());
        taskService.addNewTask(task);
        return new ModelAndView("redirect:/task");
    }
    @GetMapping("/task/{id}")
    public String getTast(@PathVariable("id") Long id, Model model){
        Task searchedTask = taskService.getTaskID(id);
        //System.out.println(searchedTask.toString());
        Collection<Employee> employeesNotUsed = employeeService.getEmployeesWithoutTask(id);
        Collection<Employee> employeesUsed = employeeService.getEmployeesWithTask(id);
        model.addAttribute("task", searchedTask);
        model.addAttribute("searchedTask", new Task());
        model.addAttribute("employeesnotused", employeesNotUsed);
        model.addAttribute("employeesused", employeesUsed);
        return "task";
    }
    @PostMapping("/task/{id}")
    public ModelAndView updateTask(@ModelAttribute Task task, @PathVariable("id") Long id){
        System.out.println(task.toString());
        taskService.updateTask(task, id);
        return new ModelAndView("redirect:/task");
    }

    @GetMapping("/task/delete/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        //System.out.println(task.toString());
        taskService.removeTask(id);
        return new ModelAndView("redirect:/task");
    }
}

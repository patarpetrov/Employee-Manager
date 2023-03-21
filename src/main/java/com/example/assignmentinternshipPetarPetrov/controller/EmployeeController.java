package com.example.assignmentinternshipPetarPetrov.controller;


import com.example.assignmentinternshipPetarPetrov.entity.Employee;
import com.example.assignmentinternshipPetarPetrov.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService studentService) {
        this.employeeService = studentService;
    }

    @GetMapping("/employee")
    public String getEmployee(Model model){
        //System.out.println(employeeService.getEmployees().get(1).toString());
        model.addAttribute("employees", employeeService.getEmployees());
        return "employees";
    }
    @GetMapping("/employee/add")
    public String addEmployee(){
        return "AddEmployee";
    }
    @PostMapping("/employee/add")
    public ModelAndView addEmployee(@ModelAttribute Employee employee){
        employeeService.addNewEmployee(employee);
        return new ModelAndView("redirect:/employee");
    }
    @GetMapping("/employee/{id}")
    public String getEmployee(@PathVariable("id") Long id, Model model){
        //System.out.printf(id.toString());
        Employee serchedEmployee = employeeService.getEmployee(id);
        model.addAttribute(serchedEmployee);
        //System.out.println(serchedEmployee.toString());
        return "employee";
    }
    @PostMapping("/employee/{id}")
    public ModelAndView updateEmployee(@ModelAttribute Employee employee, @PathVariable("id") Long id, Model model){
        System.out.println(employee.toString());
        System.out.printf(id.toString());
        employeeService.updateEmployee(employee, id);
        Employee serchedEmployee = employeeService.getEmployee(id);
        model.addAttribute(serchedEmployee);
        return new ModelAndView("redirect:/employee");
    }
    @GetMapping("/employee/delete/{id}")
    public ModelAndView deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
        return new ModelAndView("redirect:/employee");
    }
    //TODO
//    @GetMapping("/employee/department")
//    public void department(){
//        employeeService.addDepartment();
//    }
}

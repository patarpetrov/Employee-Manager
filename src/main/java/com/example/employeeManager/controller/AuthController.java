package com.example.employeeManager.controller;

import com.example.employeeManager.dto.UserDto;
import com.example.employeeManager.entity.Employee;
import com.example.employeeManager.entity.User;
import com.example.employeeManager.repository.EmployeeRepository;
import com.example.employeeManager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

    private UserService userService;
    private EmployeeRepository employeeRepository;

    public AuthController(UserService userService, EmployeeRepository employeeRepository) {
        this.userService = userService;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        Optional<Employee> employeeByEmail = employeeRepository.findEmployeeByEmail(user.getEmail());
        List<Employee> allemployees = employeeRepository.findAll();
        if (!employeeByEmail.isPresent() && allemployees.size() != 0) {
            result.rejectValue("email", null, "There is no account registered with that email");
        }
        else{
            if (!employeeByEmail.isPresent()) {
                Employee employeenew = new Employee("Admin", "admin@admin.bg", "0887089628", "1600", LocalDate.now());
                employeeRepository.save(employeenew);
                user.setEmployee(employeenew);
            }
        }
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/error?continue&continue")
    public String errorFixer(){
        return "redirect:/register?success";
    }

}

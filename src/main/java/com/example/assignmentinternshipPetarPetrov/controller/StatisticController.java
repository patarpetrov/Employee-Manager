package com.example.assignmentinternshipPetarPetrov.controller;


import com.example.assignmentinternshipPetarPetrov.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticController {
    private final StatisticService statisticService;
    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }
    @GetMapping("/statistic/best")
    public String index(Model model){
        //statisticService.findBestEmployees();
        System.out.println(statisticService.findBestEmployees());
        model.addAttribute("employees", statisticService.findBestEmployees());
        return "statisticTOP";
    }
    @GetMapping("/statistic")
    public String getEmployee(Model model){
        int NumEmoloyees = statisticService.numberOfEmoloyee();
        int NumTasks = statisticService.numberOfTasks();
        int NumTasksLastMonth = statisticService.findNumberOfTasksLastMonth();
        int NumTaskExpired = statisticService.numberofTaskExpired();
        int NumTaskActive = NumTasks - NumTaskExpired;
        model.addAttribute("numberoftasksexpired", NumTaskExpired);
        model.addAttribute("numberofemployees", NumEmoloyees);
        model.addAttribute("numberoftasks", NumTasks);
        model.addAttribute("numberoftasksactive", NumTaskActive);
        model.addAttribute("numberoftaskslastmonth", NumTasksLastMonth);
        return "statistic";
    }
}

package com.example.CovidStats.Controllers;


import com.example.CovidStats.Models.LocationStats;
import com.example.CovidStats.Services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService service;


    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> allStats = service.getAllStats();
        int totalCases = allStats.stream().mapToInt(stat -> stat.getLatestTotal()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getIncrease()).sum();
        int largestTodayInt = allStats.stream().mapToInt(stat -> stat.getIncrease()).max().getAsInt();
        Optional<String> largestToday = allStats.stream().filter(stat -> stat.getIncrease() == largestTodayInt).map(stat -> stat.getCountry()).findFirst();
        String largestTodayString = "";
        if(largestToday.isPresent())
        {  largestTodayString = largestToday.get() + ":";}
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("largestTodayInt", largestTodayInt);
        model.addAttribute("largestTodayString", largestTodayString);
        return "home";
    }


}

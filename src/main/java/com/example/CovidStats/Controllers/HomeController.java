package com.example.CovidStats.Controllers;


import com.example.CovidStats.Models.LocationStats;
import com.example.CovidStats.Services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService service;


    @GetMapping("/")
    public String home(Model model){
        DecimalFormat formatter = new DecimalFormat("#,###");
        List<LocationStats> allStats = service.getAllStats();
        int totalCases = allStats.stream().mapToInt(stat -> stat.getLatestTotal()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getIncrease()).sum();
        int largestTodayInt = allStats.stream().mapToInt(stat -> stat.getIncrease()).max().getAsInt();
        String totalCasesString = formatter.format(totalCases);
        String totalNewCasesString = formatter.format(totalNewCases);
        String largestTodayIntString = formatter.format(largestTodayInt);
        Optional<String> largestToday = allStats.stream().filter(stat -> stat.getIncrease() == largestTodayInt).map(stat -> stat.getCountry()).findFirst();
        Duration timeSinceRefresh = Duration.between(Instant.now(), service.getRefreshTime());
        String largestTodayString = "";
        timeSinceRefresh = timeSinceRefresh.abs();
        Long minsSinceRefresh = timeSinceRefresh.toMinutes();
        Long hoursSinceRefresh =
                timeSinceRefresh.toHours() + (timeSinceRefresh.toMinutes()/60-timeSinceRefresh.toHours());


        if(largestToday.isPresent())
        {  largestTodayString = largestToday.get() + ":";}
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("largestTodayInt", largestTodayInt);
        model.addAttribute("largestTodayString", largestTodayString);
        model.addAttribute("totalCasesString",totalCasesString);
        model.addAttribute("totalNewCasesString", totalNewCasesString);
        model.addAttribute("largestTodayIntString", largestTodayIntString);
        model.addAttribute("lastRefreshedTimeMins", minsSinceRefresh);
        model.addAttribute("lastRefreshedTimeHours", hoursSinceRefresh);
        return "home";
    }


}

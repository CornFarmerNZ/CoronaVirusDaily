/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.CovidStats.Services;

import com.example.CovidStats.Models.LocationStats;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author tim
 */

@Service
public class CoronaVirusDataService {

    private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    public List<LocationStats> getAllStats() {
        return allStats;
    }
    private List<LocationStats> allStats = new ArrayList<>();

    public Instant getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Instant refreshTime) {
        this.refreshTime = refreshTime;
    }

    private Instant refreshTime;


    @PostConstruct
    @Scheduled(cron = "* * * 1 * *")
    public void fetchVirusData() throws InterruptedException, IOException {
        List<LocationStats> newStats = new ArrayList<>();
        System.out.println("Fetching virus data...");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        refreshTime = Instant.now();

        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int increase = Integer.parseInt(record.get(record.size() - 1)) - Integer.parseInt(record.get(record.size() - 2));
            locationStat.setIncrease(increase);
            locationStat.setLatestTotal(Integer.parseInt(record.get(record.size() - 1)));
            System.out.println(locationStat);
            newStats.add(locationStat);
        }
        this.allStats = newStats;


    }

}

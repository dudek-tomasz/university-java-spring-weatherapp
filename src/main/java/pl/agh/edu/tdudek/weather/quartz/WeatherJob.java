package pl.agh.edu.tdudek.weather.quartz;


import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.agh.edu.tdudek.weather.entities.Measure;
import pl.agh.edu.tdudek.weather.entities.ObservableCity;
import pl.agh.edu.tdudek.weather.repositories.MeasureRepository;
import pl.agh.edu.tdudek.weather.repositories.ObservableCityRepository;

import java.util.Date;
import java.util.Optional;


@Component
public class WeatherJob implements Job {

    @Autowired
    private ObservableCityRepository observableCityRepository;

    @Autowired
    private MeasureRepository measureRepository;

    @Value("${weather.url}")
    private String url = "http://api.openweathermap.org/data/2.5/weather";

    @Value("${weather.api.key}")
    private String apiKey = "2798448cf6ac4f8129c6610df1bb7ec4";

    public WeatherJob() {
        this.observableCityRepository = ReferencesContainer.observableCityRepository;
        this.measureRepository = ReferencesContainer.measureRepository;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getJobDetail().getJobDataMap();
        Integer cityId = map.getInt("cityId");

        Optional<ObservableCity> byCityName = observableCityRepository.findById(cityId);
        ObservableCity oc = byCityName.get();

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + oc.getCityName() + "&appid=" + apiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);

        JSONObject obj = new JSONObject(response.getBody());
        Double temp = obj.getJSONObject("main").getDouble("temp");

        Measure measure = new Measure();
        measure.setTemperature(temp);
        measure.setTimestamp(new Date());
        measure.setCity(oc);
        measureRepository.save(measure);
        System.out.println("Eloszka z execute " + oc.getCityName());
    }
}

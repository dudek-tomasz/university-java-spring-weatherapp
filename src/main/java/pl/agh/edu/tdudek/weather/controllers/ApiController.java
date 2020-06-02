package pl.agh.edu.tdudek.weather.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.tdudek.weather.entities.ObservableCity;
import pl.agh.edu.tdudek.weather.services.ObservableCityService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ObservableCityService observableCityService;

    @PostMapping("/register")
    public ObservableCity register(@RequestBody ObservableCity observableCity){
        return observableCityService.register(observableCity);
    }

    @GetMapping("/stat/{id}/{n}")
    public Double stat(@PathVariable Integer id, @PathVariable Integer n){
        return observableCityService.averageTemperature(id, n);
    }

    @GetMapping
    public String get(){
        return "elo";
    }
}

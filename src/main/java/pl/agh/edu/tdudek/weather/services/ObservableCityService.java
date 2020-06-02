package pl.agh.edu.tdudek.weather.services;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.edu.tdudek.weather.entities.ObservableCity;
import pl.agh.edu.tdudek.weather.quartz.ReferencesContainer;
import pl.agh.edu.tdudek.weather.quartz.WeatherJob;
import pl.agh.edu.tdudek.weather.repositories.MeasureRepository;
import pl.agh.edu.tdudek.weather.repositories.ObservableCityRepository;

@Component
public class ObservableCityService {

    @Autowired
    private ObservableCityRepository observableCityRepository;

    @Autowired
    private MeasureRepository measureRepository;

    @Autowired
    private WeatherJob weatherJob;

    private Integer quartzJobNumber = 1;

    public ObservableCity register(ObservableCity observableCity) {
        ObservableCity result = observableCityRepository.save(observableCity);


        try {
            startJob(result);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Double averageTemperature(Integer id, Integer n) {
        return measureRepository.get(id, n);
    }


    private void startJob(ObservableCity observableCity) throws SchedulerException {
        Integer currentJobId = quartzJobNumber;
        quartzJobNumber++;

        //quartz sam tworzy obiekt WeatherJob przez co spring nie wstrzykuje zależności
        ReferencesContainer.measureRepository = this.measureRepository;
        ReferencesContainer.observableCityRepository = this.observableCityRepository;

        JobDetail job = JobBuilder.newJob(WeatherJob.class).withIdentity("WeatherJob" + currentJobId)
                .usingJobData("cityId", observableCity.getId()).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger" + currentJobId)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(observableCity.getFrequencies()).repeatForever()).build();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }

}

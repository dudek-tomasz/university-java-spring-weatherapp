package pl.agh.edu.tdudek.weather.quartz;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResponseObject implements Serializable {
    private Double temp;
}

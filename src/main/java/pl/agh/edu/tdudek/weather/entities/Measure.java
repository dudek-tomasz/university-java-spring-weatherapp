package pl.agh.edu.tdudek.weather.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "measures")
@Getter
@Setter
public class Measure {
    @Id
    @SequenceGenerator(name = "measures_gen", sequenceName = "measures_seq", allocationSize = 1)
    @GeneratedValue(generator = "measures_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ObservableCity.class)
    private ObservableCity city;

    @Column(name = "temperature", nullable = false)
    private Double temperature;

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;



}

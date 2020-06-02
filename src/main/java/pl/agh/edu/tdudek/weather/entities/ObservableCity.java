package pl.agh.edu.tdudek.weather.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "observable_cities")
@Getter
@Setter
public class ObservableCity implements Serializable {

    @Id
    @SequenceGenerator(name = "observable_cities_gen", sequenceName = "observable_cities_seq", allocationSize = 1)
    @GeneratedValue(generator = "observable_cities_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "city_name", length = 100, nullable = false)
    private String cityName;

    @Column(name = "frequencies", nullable = false)
    private Integer frequencies;
}

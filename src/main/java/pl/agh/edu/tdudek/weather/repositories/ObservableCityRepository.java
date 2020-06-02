package pl.agh.edu.tdudek.weather.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.agh.edu.tdudek.weather.entities.ObservableCity;

@Repository
public interface ObservableCityRepository extends JpaRepository<ObservableCity, Integer>, CrudRepository<ObservableCity, Integer> {
    ObservableCity findByCityName(String cityName);
//    ObservableCity findById(Integer id);


}

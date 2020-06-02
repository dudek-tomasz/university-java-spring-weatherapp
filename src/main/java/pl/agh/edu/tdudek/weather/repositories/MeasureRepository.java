package pl.agh.edu.tdudek.weather.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.agh.edu.tdudek.weather.entities.Measure;

import java.util.Set;

@Repository
public interface MeasureRepository extends JpaRepository<Measure, Integer>, CrudRepository<Measure, Integer> {

    @Query(value = "select avg(a.temperature) from (select temperature from measures where city_id = ?1 order by timestamp DESC limit ?2) as a", nativeQuery = true)
    Double get(Integer cityId, Integer n);
}

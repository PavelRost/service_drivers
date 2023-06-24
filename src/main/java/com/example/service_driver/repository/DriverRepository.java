package com.example.service_driver.repository;

import com.example.service_driver.entities.Driver;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends CrudRepository<Driver, Integer> {

    List<Driver> findByLastName(String lastName);

    Optional<Driver> findByPassport(String passport);

    @Query(value = "SELECT * FROM drivers dr WHERE EXTRACT(day FROM dr.birthdate) = EXTRACT(day FROM now())", nativeQuery = true)
    List<Driver> findDriversByBirthdateToday();

    Slice<Driver> findAll(Pageable page);

}

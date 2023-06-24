package com.example.service_driver.service;

import com.example.service_driver.entities.Car;
import com.example.service_driver.entities.Driver;
import com.example.service_driver.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class DriverService {

    @Value("${source-api-url}")
    private String url;

    static final Logger logger = Logger.getLogger(DriverService.class.getName());

    private final DriverRepository driverRepository;
    private final RestTemplate carService;

    public DriverService(DriverRepository driverRepository, RestTemplate carService) {
        this.driverRepository = driverRepository;
        this.carService = carService;
    }

    public boolean create(Driver newDriver) {
        boolean rsl = false;
        Driver driver = driverRepository.findByPassport(newDriver.getPassport()).orElse(null);
        if (driver == null) {
            newDriver.getVinCars().forEach(this::isCorrectVinCar);
            driverRepository.save(newDriver);
            rsl = true;
        }
        return rsl;
    }

    public boolean update(int id, Driver driver) {
        boolean rsl = driverRepository.existsById(id);
        if (rsl) {
            driver.getVinCars().forEach(this::isCorrectVinCar);
            driver.setId(id);
            driverRepository.save(driver);
        }
        return rsl;
    }

    public boolean delete(int id) {
        boolean rsl = driverRepository.existsById(id);
        if (rsl) driverRepository.deleteById(id);
        return rsl;
    }

    public List<Driver> findAll(int offset, int limit, String direction, String field) {
        switch (direction) {
            case "asc" -> {
                List<Driver> drivers = driverRepository.findAll(PageRequest.of(offset, limit, Sort.by(field))).getContent();
                if (!drivers.isEmpty()) drivers.forEach(this::addCarsDriver);
                return drivers;
            }
            case "desc" -> {
                List<Driver> drivers = driverRepository.findAll(PageRequest.of(offset, limit, Sort.by(field).descending())).getContent();
                if (!drivers.isEmpty()) drivers.forEach(this::addCarsDriver);
                return drivers;
            }
            default -> {
                return List.of(); }
        }
    }

    public Driver findById(int id) {
        Driver driver = driverRepository.findById(id).orElse(null);
        if (driver != null) addCarsDriver(driver);
        return driver;
    }

    public List<Driver> findByLastName(String lastName) {
        List<Driver> drivers = driverRepository.findByLastName(lastName);
        if (!drivers.isEmpty()) drivers.forEach(this::addCarsDriver);
        return drivers;
    }

    public Driver findByPassport(String passport) {
        Driver driver = driverRepository.findByPassport(passport).orElse(null);
        if (driver != null) addCarsDriver(driver);
        return driver;
    }

    public boolean addVinToDriver(int idDriver, String vinCar) {
        Optional<Driver> driver = driverRepository.findById(idDriver);
        if (driver.isPresent()) {
            if (driver.get().getVinCars().contains(vinCar)) return false;
            isCorrectVinCar(vinCar);
            driver.get().addVinCar(vinCar);
            driverRepository.save(driver.get());
            return true;
        }
        return false;
    }

    public boolean deleteVinOnDriver(int idDriver, String vinCar) {
        Optional<Driver> driver = driverRepository.findById(idDriver);
        if (driver.isPresent()) {
            if (driver.get().getVinCars().contains(vinCar)) {
                driver.get().deleteVinVar(vinCar);
                driverRepository.save(driver.get());
                return true;
            }
        }
        return false;
    }

    @Scheduled(fixedRateString = "PT3H")
    private void showDriversBirthdateToday() {
        List<Driver> drivers = driverRepository.findDriversByBirthdateToday();
        if (!drivers.isEmpty()) {
            drivers.forEach(driver ->
                    logger.info("Поздравляем с днем рождения: " + driver.getLastName() + " " + driver.getFirstName() + "!"));
        } else {
            logger.info("Сегодня никто не празднует день рождение");
        }
    }

    private void addCarsDriver(Driver driver) {
        Set<String> vinCar = driver.getVinCars();
        if (vinCar.isEmpty()) return;
        vinCar.forEach(vin -> {
            Car car = carService.getForEntity("%s/findByVin?vin=%s".formatted(url, vin), Car.class).getBody();
            driver.addCar(car);
        });
    }

    private void isCorrectVinCar(String vinCar) {
        carService.getForEntity("%s/findByVin?vin=%s".formatted(url, vinCar), Car.class);
    }
}

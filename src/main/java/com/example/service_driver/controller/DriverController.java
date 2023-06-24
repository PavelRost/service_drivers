package com.example.service_driver.controller;


import com.example.service_driver.entities.Driver;
import com.example.service_driver.service.DriverService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.service_driver.controller.ApplicationMessage.response;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Driver driver) {
        boolean rsl = driverService.create(driver);
        if (rsl) return response("Водитель успешно добавлен", HttpStatus.OK);
        return response("Водитель с таким паспортом уже добавлен", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam int idDriver, @RequestBody Driver driver) {
        boolean rsl = driverService.update(idDriver, driver);
        if (rsl) return response("Водитель успешно обновлен", HttpStatus.OK);
        return response("Водитель с id: " + idDriver + " не найден", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam int idDriver) {
        boolean rsl = driverService.delete(idDriver);
        if (rsl) return response("Водитель успешно удален", HttpStatus.OK);
        return response("Водитель с id: " + idDriver + " не найден", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestParam(value = "offset", defaultValue = "0") @Min(0) int offset,
                                     @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) int limit,
                                     @RequestParam String direction,
                                     @RequestParam String sortByField) {
        List<Driver> drivers = driverService.findAll(offset, limit, direction, sortByField);
        if (drivers.isEmpty()) return response("Отсутствуют записи о водителях в БД / некорректные параметры запроса", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findById(@RequestParam int id) {
        Driver driver = driverService.findById(id);
        if (driver == null) return response("Водитель с id: " + id + " не найден", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @GetMapping("/findByLastName")
    public ResponseEntity<?> findByLastName(@RequestParam String lastName) {
        List<Driver> driver = driverService.findByLastName(lastName);
        if (driver.isEmpty()) return response("Водитель с фамилией: " + lastName + " не найден", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @GetMapping("/findByPassport")
    public ResponseEntity<?> findByPassport(@RequestParam String passport) {
        Driver driver = driverService.findByPassport(passport);
        if (driver == null) return response("Водитель с паспортом " + passport.substring(0, 4) + " " + passport.substring(4) + " не найден", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @PostMapping("/addCarOnDriver")
    public ResponseEntity<?> addCarOnDriver(@RequestParam int idDriver, @RequestParam String vinCar) {
        boolean rsl = driverService.addVinToDriver(idDriver, vinCar);
        if (rsl) return response("Водителю добавлена новая машина", HttpStatus.OK);
        return response("Проверьте правильность id водителя или машина уже добавлена водителю", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteCarOnDriver")
    public ResponseEntity<?> deleteCarOnDriver(@RequestParam int idDriver, @RequestParam String vinCar) {
        boolean rsl = driverService.deleteVinOnDriver(idDriver, vinCar);
        if (rsl) return response("Машина у водителя удалена", HttpStatus.OK);
        return response("Проверьте vin машины / id водителя, возможно, машина не принадлежит водителю", HttpStatus.NOT_FOUND);
    }
}

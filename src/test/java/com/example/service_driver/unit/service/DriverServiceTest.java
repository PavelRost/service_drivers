package com.example.service_driver.unit.service;

import com.example.service_driver.entities.Car;
import com.example.service_driver.entities.Driver;
import com.example.service_driver.repository.DriverRepository;
import com.example.service_driver.service.DriverService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    @Value("${source-api-url}")
    private String url;

    @InjectMocks
    private DriverService driverService;

    @Mock
    private RestTemplate carService;

    @Mock
    private DriverRepository driverRepository;

    private final ResponseEntity responseEntity = mock(ResponseEntity.class);
    private final Driver driverWithoutVin = new Driver(1, "Федоров", "1234567890", new HashSet<>());
    private final Driver driverWithVin = new Driver(2, "Иванов","7777999988", new HashSet<>(List.of("333-555-777")));
    private final String vinCar = "333-555-777";
    private final int idDriverNotFound = 3;

    @Test
    void whenCreateDriverWithoutVinThenSuccess() {
        Mockito.when(driverRepository.findByPassport(Mockito.anyString())).thenReturn(Optional.empty());

        boolean actual = driverService.create(driverWithoutVin);

        Mockito.verify(driverRepository).findByPassport(driverWithoutVin.getPassport());
        Mockito.verify(driverRepository).save(driverWithoutVin);
        assertTrue(actual);
    }

    @Test
    void whenCreateDriverWithVinThenSuccess() {
        Mockito.when(driverRepository.findByPassport(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(carService.getForEntity(Mockito.anyString(), ArgumentMatchers.any(Class.class))).thenReturn(responseEntity);

        boolean actual = driverService.create(driverWithVin);

        Mockito.verify(driverRepository).findByPassport(driverWithVin.getPassport());
        Mockito.verify(driverRepository).save(driverWithVin);
        Mockito.verify(carService).getForEntity("%s/findByVin?vin=%s".formatted(url, vinCar), Car.class);
        assertTrue(actual);
    }

    @Test
    void whenCreateDriverThenFailedDriverAddEarlier() {
        Mockito.when(driverRepository.findByPassport(Mockito.anyString())).thenReturn(Optional.of(driverWithoutVin));

        boolean actual = driverService.create(driverWithoutVin);

        Mockito.verify(driverRepository).findByPassport(driverWithoutVin.getPassport());
        assertFalse(actual);
    }

    @Test
    void whenUpdateDriverWithoutVinThenSuccess() {
        Mockito.when(driverRepository.existsById(Mockito.anyInt())).thenReturn(true);

        boolean actual = driverService.update(driverWithVin.getId(), driverWithoutVin);

        Mockito.verify(driverRepository).existsById(driverWithVin.getId());
        Mockito.verify(driverRepository).save(driverWithoutVin);
        assertTrue(actual);
    }

    @Test
    void whenUpdateDriverWithVinThenSuccess() {
        Mockito.when(driverRepository.existsById(Mockito.anyInt())).thenReturn(true);
        Mockito.when(carService.getForEntity(Mockito.anyString(), ArgumentMatchers.any(Class.class))).thenReturn(responseEntity);

        boolean actual = driverService.update(driverWithoutVin.getId(), driverWithVin);

        Mockito.verify(driverRepository).existsById(driverWithoutVin.getId());
        Mockito.verify(driverRepository).save(driverWithVin);
        Mockito.verify(carService).getForEntity("%s/findByVin?vin=%s".formatted(url, vinCar), Car.class);
        assertTrue(actual);
    }

    @Test
    void whenUpdateDriverWithoutVinThenFailedDriverNotFound() {
        Mockito.when(driverRepository.existsById(Mockito.anyInt())).thenReturn(false);

        boolean actual = driverService.update(idDriverNotFound, driverWithoutVin);

        Mockito.verify(driverRepository).existsById(idDriverNotFound);
        assertFalse(actual);
    }

    @Test
    void whenDeleteDriverThenSuccess() {
        Mockito.when(driverRepository.existsById(Mockito.anyInt())).thenReturn(true);

        boolean actual = driverService.delete(driverWithVin.getId());

        Mockito.verify(driverRepository).existsById(driverWithVin.getId());
        assertTrue(actual);
    }

    @Test
    void whenDeleteDriverThenFailedDriverNotFound() {
        Mockito.when(driverRepository.existsById(Mockito.anyInt())).thenReturn(false);

        boolean actual = driverService.delete(idDriverNotFound);

        Mockito.verify(driverRepository).existsById(idDriverNotFound);
        assertFalse(actual);
    }

    @Test
    void whenFindAllSortAscThenSuccess() {
        int offset = 0;
        int limit = 3;
        String direction = "asc";
        String field = "id";
        List<Driver> drivers = List.of(new Driver(1), new Driver(2));
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(field));
        Mockito.when(driverRepository.findAll(ArgumentMatchers.any())).thenReturn(
                new SliceImpl<>(drivers, pageRequest,false)
        );

        List<Driver> actual = driverService.findAll(offset, limit, direction, field);

        Mockito.verify(driverRepository).findAll(pageRequest);
        assertEquals(drivers, actual);
    }

    @Test
    void whenFindAllSortDescThenSuccess() {
        int offset = 0;
        int limit = 3;
        String direction = "desc";
        String field = "id";
        List<Driver> drivers = List.of(new Driver(2), new Driver(1));
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(field).descending());
        Mockito.when(driverRepository.findAll(ArgumentMatchers.any())).thenReturn(
                new SliceImpl<>(drivers, pageRequest,false)
        );

        List<Driver> actual = driverService.findAll(offset, limit, direction, field);

        Mockito.verify(driverRepository).findAll(pageRequest);
        assertEquals(drivers, actual);
    }

    @Test
    void whenFindAllThenFailedDriverNotFoundIncorrectParam() {
        int offset = 0;
        int limit = 3;
        String direction = "";
        String field = "id";
        List<Driver> drivers = List.of();

        List<Driver> actual = driverService.findAll(offset, limit, direction, field);

        assertEquals(drivers, actual);
    }

    @Test
    void whenFindDriverWithoutVinByIdThenSuccess() {
        Mockito.when(driverRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(driverWithoutVin));

        Driver actual = driverService.findById(driverWithoutVin.getId());

        Mockito.verify(driverRepository).findById(driverWithoutVin.getId());
        assertEquals(driverWithoutVin, actual);
    }

    @Test
    void whenFindDriverWithVinByIdThenSuccess() {
        Mockito.when(driverRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(driverWithVin));
        Mockito.when(carService.getForEntity(Mockito.anyString(), ArgumentMatchers.any(Class.class))).thenReturn(responseEntity);

        Driver actual = driverService.findById(driverWithVin.getId());

        Mockito.verify(driverRepository).findById(driverWithVin.getId());
        Mockito.verify(carService).getForEntity("%s/findByVin?vin=%s".formatted(url, vinCar), Car.class);
        assertEquals(driverWithVin, actual);
    }

    @Test
    void whenFindDriverByIdThenFailedDriverNotFound() {
        Mockito.when(driverRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Driver actual = driverService.findById(idDriverNotFound);

        Mockito.verify(driverRepository).findById(idDriverNotFound);
        assertNull(actual);
    }

    @Test
    void whenFindDriversWithoutVinByLastNameThenSuccess() {
        List<Driver> drivers = List.of(driverWithoutVin);
        Mockito.when(driverRepository.findByLastName(Mockito.anyString())).thenReturn(drivers);

        List<Driver> actual = driverService.findByLastName(driverWithoutVin.getLastName());

        Mockito.verify(driverRepository).findByLastName(driverWithoutVin.getLastName());
        assertEquals(drivers, actual);
    }

    @Test
    void whenFindDriversWithVinByLastNameThenSuccess() {
        List<Driver> drivers = List.of(driverWithVin);
        Mockito.when(driverRepository.findByLastName(Mockito.anyString())).thenReturn(drivers);
        Mockito.when(carService.getForEntity(Mockito.anyString(), ArgumentMatchers.any(Class.class))).thenReturn(responseEntity);

        List<Driver> actual = driverService.findByLastName(driverWithVin.getLastName());

        Mockito.verify(driverRepository).findByLastName(driverWithVin.getLastName());
        Mockito.verify(carService).getForEntity("%s/findByVin?vin=%s".formatted(url, vinCar), Car.class);
        assertEquals(drivers, actual);
    }

    @Test
    void whenFindDriversByLastNameThenFailedDriverNotFound() {
        Mockito.when(driverRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Driver actual = driverService.findById(idDriverNotFound);

        Mockito.verify(driverRepository).findById(idDriverNotFound);
        assertNull(actual);
    }

    @Test
    void whenFindDriversWithoutVinByPassportThenSuccess() {
        Mockito.when(driverRepository.findByPassport(Mockito.anyString())).thenReturn(Optional.of(driverWithoutVin));

        Driver actual = driverService.findByPassport(driverWithoutVin.getPassport());

        Mockito.verify(driverRepository).findByPassport(driverWithoutVin.getPassport());
        assertEquals(driverWithoutVin, actual);
    }

    @Test
    void whenFindDriversWithVinByPassportThenSuccess() {
        Mockito.when(driverRepository.findByPassport(Mockito.anyString())).thenReturn(Optional.of(driverWithVin));
        Mockito.when(carService.getForEntity(Mockito.anyString(), ArgumentMatchers.any(Class.class))).thenReturn(responseEntity);

        Driver actual = driverService.findByPassport(driverWithVin.getPassport());

        Mockito.verify(driverRepository).findByPassport(driverWithVin.getPassport());
        Mockito.verify(carService).getForEntity("%s/findByVin?vin=%s".formatted(url, vinCar), Car.class);
        assertEquals(driverWithVin, actual);
    }

    @Test
    void whenFindDriversByPassportThenFailedDriverNotFound() {
        Mockito.when(driverRepository.findByPassport(Mockito.anyString())).thenReturn(Optional.empty());

        Driver actual = driverService.findByPassport(driverWithVin.getPassport());

        Mockito.verify(driverRepository).findByPassport(driverWithVin.getPassport());
        assertNull(actual);

    }

    @Test
    void whenAddVinToDriverThenSuccess() {
        Mockito.when(driverRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(driverWithoutVin));
        Mockito.when(carService.getForEntity(Mockito.anyString(), ArgumentMatchers.any(Class.class))).thenReturn(responseEntity);

        boolean actual = driverService.addVinToDriver(driverWithoutVin.getId(), vinCar);

        Mockito.verify(driverRepository).findById(driverWithoutVin.getId());
        Mockito.verify(carService).getForEntity("%s/findByVin?vin=%s".formatted(url, vinCar), Car.class);
        Mockito.verify(driverRepository).save(driverWithoutVin);
        assertTrue(actual);

    }

    @Test
    void whenAddVinToDriverThenDriverNotFound() {
        Mockito.when(driverRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        boolean actual = driverService.addVinToDriver(idDriverNotFound, vinCar);

        Mockito.verify(driverRepository).findById(idDriverNotFound);
        assertFalse(actual);
    }

    @Test
    void whenDeleteVinToDriverThenSuccess() {
        Mockito.when(driverRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(driverWithVin));

        boolean actual = driverService.deleteVinOnDriver(driverWithVin.getId(), vinCar);

        Mockito.verify(driverRepository).findById(driverWithVin.getId());
        Mockito.verify(driverRepository).save(driverWithVin);
        assertTrue(actual);
    }

    @Test
    void whenDeleteVinToDriverThenDriverNotFound() {
        Mockito.when(driverRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        boolean actual = driverService.deleteVinOnDriver(idDriverNotFound, vinCar);

        Mockito.verify(driverRepository).findById(idDriverNotFound);
        assertFalse(actual);
    }
}
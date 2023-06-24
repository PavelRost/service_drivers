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
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class DriverServiceExceptionTest {

    @Value("${source-api-url}")
    private String url;

    @InjectMocks
    private DriverService driverService;

    @Mock
    private RestTemplate carService;

    @Mock
    private DriverRepository driverRepository;

    private final ResponseEntity responseEntity = mock(ResponseEntity.class);
    private final Driver driverWithVin = new Driver(2, "Иванов","7777999988", new HashSet<>(List.of("333-555-777")));
    private final String vinCar = "333-555-777";


    @Test()
    void whenCheckCorrectVinCarThenExceptionVinNotFound() {
        Mockito.when(driverRepository.findByPassport(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(carService.getForEntity(Mockito.anyString(), ArgumentMatchers.any(Class.class)))
                .thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            driverService.create(driverWithVin);
        });
        Mockito.verify(driverRepository).findByPassport(driverWithVin.getPassport());
        Mockito.verify(carService).getForEntity("%s/findByVin?vin=%s".formatted(url, vinCar), Car.class);
    }

    @Test()
    void whenCheckCorrectVinCarThenExceptionServerNotResponse() {
        Mockito.when(driverRepository.findByPassport(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(carService.getForEntity(Mockito.anyString(), ArgumentMatchers.any(Class.class)))
                .thenThrow(RestClientException.class);

        assertThrows(RestClientException.class, () -> {
            driverService.create(driverWithVin);
        });
        Mockito.verify(driverRepository).findByPassport(driverWithVin.getPassport());
        Mockito.verify(carService).getForEntity("%s/findByVin?vin=%s".formatted(url, vinCar), Car.class);
    }


    @Test
    void whenAddCarDriverThenExceptionVinNotFound() {
        Mockito.when(driverRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(driverWithVin));
        Mockito.when(carService.getForEntity(Mockito.anyString(), ArgumentMatchers.any(Class.class))).thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            driverService.findById(driverWithVin.getId());
        });

        Mockito.verify(driverRepository).findById(driverWithVin.getId());
        Mockito.verify(carService).getForEntity("%s/findByVin?vin=%s".formatted(url, vinCar), Car.class);
    }

    @Test
    void whenAddCarDriverThenExceptionServerNotResponse() {
        Mockito.when(driverRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(driverWithVin));
        Mockito.when(carService.getForEntity(Mockito.anyString(), ArgumentMatchers.any(Class.class))).thenThrow(RestClientException.class);

        assertThrows(RestClientException.class, () -> {
            driverService.findById(driverWithVin.getId());
        });

        Mockito.verify(driverRepository).findById(driverWithVin.getId());
        Mockito.verify(carService).getForEntity("%s/findByVin?vin=%s".formatted(url, vinCar), Car.class);
    }
}

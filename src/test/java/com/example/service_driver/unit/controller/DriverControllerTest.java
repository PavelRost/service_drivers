package com.example.service_driver.unit.controller;

import com.example.service_driver.controller.ApplicationMessage;
import com.example.service_driver.controller.DriverController;
import com.example.service_driver.entities.Driver;
import com.example.service_driver.service.DriverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DriverController.class)
class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DriverService driverService;

    private final int idDriverNotFound = 3;
    private final Driver driverId1 = new Driver(1);
    private final Driver driverId2 = new Driver(2);
    private final Driver driver = new Driver(1, "Федоров", "1234567890", Set.of());

    @Test
    void whenCreateDriverThenSuccess() throws Exception {
        Mockito.when(driverService.create(Mockito.any())).thenReturn(true);

        mockMvc.perform(
                        post("/driver/create")
                                .content(objectMapper.writeValueAsString(driverId1))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.OK.value(),
                        "Водитель успешно добавлен"))));
    }

    @Test
    void whenCreateDriverThenFailedDriverAddEarlier() throws Exception {
        Mockito.when(driverService.create(Mockito.any())).thenReturn(false);

        mockMvc.perform(
                        post("/driver/create")
                                .content(objectMapper.writeValueAsString(driverId1))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.BAD_REQUEST.value(),
                        "Водитель с таким паспортом уже добавлен"))));
    }

    @Test
    void whenUpdateDriverThenSuccess() throws Exception {
        Mockito.when(driverService.update(anyInt(), Mockito.any())).thenReturn(true);

        mockMvc.perform(
                        put("/driver/update")
                                .param("idDriver", "1")
                                .content(objectMapper.writeValueAsString(driverId2))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.OK.value(),
                        "Водитель успешно обновлен"))));
    }

    @Test
    void whenUpdateDriverThenFailedIdNotFound() throws Exception {
        int idDriver = 0;
        Mockito.when(driverService.update(anyInt(), Mockito.any())).thenReturn(false);

        mockMvc.perform(
                        put("/driver/update")
                                .param("idDriver", String.valueOf(idDriver))
                                .content(objectMapper.writeValueAsString(driverId2))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.NOT_FOUND.value(),
                        "Водитель с id: " + idDriver + " не найден"))));
    }

    @Test
    void whenDeleteDriverThenSuccess() throws Exception {
        Mockito.when(driverService.delete(anyInt())).thenReturn(true);

        mockMvc.perform(
                        delete("/driver/delete")
                                .param("idDriver", String.valueOf(driverId1.getId()))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.OK.value(),
                        "Водитель успешно удален"))));
    }

    @Test
    void whenDeleteDriverThenFailedIdNotFound() throws Exception {
        Mockito.when(driverService.delete(anyInt())).thenReturn(false);

        mockMvc.perform(
                        delete("/driver/delete")
                                .param("idDriver", String.valueOf(idDriverNotFound))
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.NOT_FOUND.value(),
                        "Водитель с id: " + idDriverNotFound + " не найден"))));
    }

    @Test
    void whenFindAllDriverThenSuccess() throws Exception {
        List<Driver> drivers = List.of(driverId1, driverId2);
        Mockito.when(driverService.findAll(anyInt(), anyInt(), anyString(), anyString())).thenReturn(drivers);

        mockMvc.perform(
                        get("/driver/findAll")
                                .param("offset", "0")
                                .param("limit", "2")
                                .param("direction", "asc")
                                .param("sortByField", "id")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(drivers)));
    }

    @Test
    void whenFindAllDriverThenFailedDbEmpty() throws Exception {
        List<Driver> drivers = List.of();
        Mockito.when(driverService.findAll(anyInt(), anyInt(), anyString(), anyString())).thenReturn(drivers);

        mockMvc.perform(
                        get("/driver/findAll")
                                .param("offset", "0")
                                .param("limit", "2")
                                .param("direction", "asc")
                                .param("sortByField", "id")
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.NOT_FOUND.value(),
                        "Отсутствуют записи о водителях в БД / некорректные параметры запроса"))));
    }

    @Test
    void whenFindByIdThenSuccess() throws Exception {
        Mockito.when(driverService.findById(anyInt())).thenReturn(driverId1);

        mockMvc.perform(
                        get("/driver/findById")
                                .param("id", String.valueOf(driverId1.getId()))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(driverId1)));
    }

    @Test
    void whenFindByIdThenFailedIdNotFound() throws Exception {
        Mockito.when(driverService.findById(anyInt())).thenReturn(null);

        mockMvc.perform(
                        get("/driver/findById")
                                .param("id", String.valueOf(idDriverNotFound))
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.NOT_FOUND.value(),
                        "Водитель с id: " + idDriverNotFound + " не найден"))));
    }

    @Test
    void whenFindByLastNameThenSuccess() throws Exception {
        List<Driver> drivers = List.of(driver);
        Mockito.when(driverService.findByLastName(anyString())).thenReturn(drivers);

        mockMvc.perform(
                        get("/driver/findByLastName")
                                .param("lastName", "Федоров")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(drivers)));
    }

    @Test
    void whenFindByLastNameThenFailedLastNameNotFound() throws Exception {
        String lastName = "Федоров";
        Mockito.when(driverService.findByLastName(anyString())).thenReturn(List.of());

        mockMvc.perform(
                        get("/driver/findByLastName")
                                .param("lastName", lastName)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.NOT_FOUND.value(),
                        "Водитель с фамилией: " + lastName + " не найден"))));
    }

    @Test
    void whenFindByPassportThenSuccess() throws Exception {
        Mockito.when(driverService.findByPassport(anyString())).thenReturn(driver);

        mockMvc.perform(
                        get("/driver/findByPassport")
                                .param("passport", driver.getPassport())
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(driver)));
    }

    @Test
    void whenFindByPassportThenFailedPassportNotFound() throws Exception {
        Mockito.when(driverService.findByPassport(anyString())).thenReturn(null);

        mockMvc.perform(
                        get("/driver/findByPassport")
                                .param("passport", driver.getPassport())
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.NOT_FOUND.value(),
                        "Водитель с паспортом " + driver.getPassport().substring(0, 4)
                                + " " + driver.getPassport().substring(4) + " не найден"))));
    }

    @Test
    void whenAddCarOnDriverThenSuccess() throws Exception {
        String vinCar = "123456789";
        Mockito.when(driverService.addVinToDriver(anyInt(), anyString())).thenReturn(true);

        mockMvc.perform(
                        post("/driver/addCarOnDriver")
                                .param("idDriver", String.valueOf(driverId1.getId()))
                                .param("vinCar", vinCar)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.OK.value(),
                        "Водителю добавлена новая машина"))));
    }

    @Test
    void whenAddCarOnDriverThenFailedIdNotFound() throws Exception {
        String vinCar = "123456789";
        Mockito.when(driverService.addVinToDriver(anyInt(), anyString())).thenReturn(false);

        mockMvc.perform(
                        post("/driver/addCarOnDriver")
                                .param("idDriver", String.valueOf(idDriverNotFound))
                                .param("vinCar", vinCar)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.NOT_FOUND.value(),
                        "Проверьте правильность id водителя или машина уже добавлена водителю"))));
    }

    @Test
    void whenDeleteCarOnDriverThenSuccess() throws Exception {
        String vinCar = "123456789";
        Mockito.when(driverService.deleteVinOnDriver(anyInt(), anyString())).thenReturn(true);

        mockMvc.perform(
                        delete("/driver/deleteCarOnDriver")
                                .param("idDriver", String.valueOf(driverId1.getId()))
                                .param("vinCar", vinCar)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.OK.value(),
                        "Машина у водителя удалена"))));
    }

    @Test
    void whenDeleteCarOnDriverThenFailedIdNotFound() throws Exception {
        String vinCar = "123456789";
        Mockito.when(driverService.deleteVinOnDriver(anyInt(), anyString())).thenReturn(false);

        mockMvc.perform(
                        delete("/driver/deleteCarOnDriver")
                                .param("idDriver", String.valueOf(idDriverNotFound))
                                .param("vinCar", vinCar)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.NOT_FOUND.value(),
                        "Проверьте vin машины / id водителя, возможно, машина не принадлежит водителю"))));
    }
}
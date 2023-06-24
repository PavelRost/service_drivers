package com.example.service_driver.unit.controller;


import com.example.service_driver.controller.ApplicationMessage;
import com.example.service_driver.controller.MoneyController;
import com.example.service_driver.service.MoneyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MoneyController.class)
class MoneyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MoneyService moneyService;

    int idDriver = 1;

    @Test
    void whenAddRedMoneyThenSuccess() throws Exception {
        int money = 3;
        Mockito.when(moneyService.addRedMoney(anyInt(), anyInt())).thenReturn(true);

        mockMvc.perform(
                        put("/money/addRed")
                                .param("idDriver", String.valueOf(idDriver))
                                .param("money", String.valueOf(money))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.OK.value(),
                        "Счет успешно обновлен"))));
    }

    @Test
    void whenAddRedMoneyThenFailedIdDriverOrDepositAmount() throws Exception {
        int money = -3;
        Mockito.when(moneyService.addRedMoney(anyInt(), anyInt())).thenReturn(false);

        mockMvc.perform(
                        put("/money/addRed")
                                .param("idDriver", String.valueOf(idDriver))
                                .param("money", String.valueOf(money))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.BAD_REQUEST.value(),
                        "Проверьте id водителя / сумму пополнения"))));
    }

    @Test
    void whenAddGreenMoneyThenSuccess() throws Exception {
        int money = 3;
        Mockito.when(moneyService.addGreenMoney(anyInt(), anyInt())).thenReturn(true);

        mockMvc.perform(
                        put("/money/addGreen")
                                .param("idDriver", String.valueOf(idDriver))
                                .param("money", String.valueOf(money))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.OK.value(),
                        "Счет успешно обновлен"))));
    }

    @Test
    void whenAddGreenMoneyThenFailedIdDriverOrDepositAmount() throws Exception {
        int money = 3;
        Mockito.when(moneyService.addGreenMoney(anyInt(), anyInt())).thenReturn(false);

        mockMvc.perform(
                        put("/money/addGreen")
                                .param("idDriver", String.valueOf(idDriver))
                                .param("money", String.valueOf(money))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.BAD_REQUEST.value(),
                        "Проверьте id водителя / сумму пополнения"))));
    }

    @Test
    void whenAddBlueMoneyThenSuccess() throws Exception {
        int money = 3;
        Mockito.when(moneyService.addBlueMoney(anyInt(), anyInt())).thenReturn(true);

        mockMvc.perform(
                        put("/money/addBlue")
                                .param("idDriver", String.valueOf(idDriver))
                                .param("money", String.valueOf(money))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.OK.value(),
                        "Счет успешно обновлен"))));
    }

    @Test
    void whenAddBlueMoneyThenFailedIdDriverOrDepositAmount() throws Exception {
        int money = 3;
        Mockito.when(moneyService.addBlueMoney(anyInt(), anyInt())).thenReturn(false);

        mockMvc.perform(
                        put("/money/addBlue")
                                .param("idDriver", String.valueOf(idDriver))
                                .param("money", String.valueOf(money))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.BAD_REQUEST.value(),
                        "Проверьте id водителя / сумму пополнения"))));
    }

    @Test
    void whenGetRedMoneyThenSuccess() throws Exception {
        int money = 3;
        Mockito.when(moneyService.getRedMoney(anyInt(), anyInt())).thenReturn(true);

        mockMvc.perform(
                        put("/money/getRed")
                                .param("idDriver", String.valueOf(idDriver))
                                .param("money", String.valueOf(money))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.OK.value(),
                        "Операция снятия завершена успешно"))));
    }

    @Test
    void whenGetRedMoneyThenFailedIdDriverOrWithdrawAmount() throws Exception {
        int money = -3;
        Mockito.when(moneyService.getRedMoney(anyInt(), anyInt())).thenReturn(false);

        mockMvc.perform(
                        put("/money/getRed")
                                .param("idDriver", String.valueOf(idDriver))
                                .param("money", String.valueOf(money))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.BAD_REQUEST.value(),
                        "Проверьте id водителя / сумму для снятия"))));
    }

    @Test
    void whenGetGreenMoneyThenSuccess() throws Exception {
        int money = 3;
        Mockito.when(moneyService.getGreenMoney(anyInt(), anyInt())).thenReturn(true);

        mockMvc.perform(
                        put("/money/getGreen")
                                .param("idDriver", String.valueOf(idDriver))
                                .param("money", String.valueOf(money))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.OK.value(),
                        "Операция снятия завершена успешно"))));
    }

    @Test
    void whenGetGreenMoneyThenFailedIdDriverOrWithdrawAmount() throws Exception {
        int money = -3;
        Mockito.when(moneyService.getGreenMoney(anyInt(), anyInt())).thenReturn(false);

        mockMvc.perform(
                        put("/money/getGreen")
                                .param("idDriver", String.valueOf(idDriver))
                                .param("money", String.valueOf(money))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.BAD_REQUEST.value(),
                        "Проверьте id водителя / сумму для снятия"))));
    }

    @Test
    void whenGetBlueMoneyThenSuccess() throws Exception {
        int money = 3;
        Mockito.when(moneyService.getBlueMoney(anyInt(), anyInt())).thenReturn(true);

        mockMvc.perform(
                        put("/money/getBlue")
                                .param("idDriver", String.valueOf(idDriver))
                                .param("money", String.valueOf(money))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.OK.value(),
                        "Операция снятия завершена успешно"))));
    }

    @Test
    void whenGetBlueMoneyThenFailedIdDriverOrWithdrawAmount() throws Exception {
        int money = -3;
        Mockito.when(moneyService.getBlueMoney(anyInt(), anyInt())).thenReturn(false);

        mockMvc.perform(
                        put("/money/getBlue")
                                .param("idDriver", String.valueOf(idDriver))
                                .param("money", String.valueOf(money))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.BAD_REQUEST.value(),
                        "Проверьте id водителя / сумму для снятия"))));
    }

    @Test
    void whenShowBlueMoneyThenSuccess() throws Exception {
        double money = 3.0;
        Mockito.when(moneyService.showBlueMoney(anyInt())).thenReturn(money);

        mockMvc.perform(
                        get("/money/showBlue")
                                .param("idDriver", String.valueOf(idDriver))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString((int)money)));
    }

    @Test
    void whenShowBlueMoneyThenFailedIdDriverNotFound() throws Exception {
        double money = -1;
        Mockito.when(moneyService.showBlueMoney(anyInt())).thenReturn(money);

        mockMvc.perform(
                        get("/money/showBlue")
                                .param("idDriver", String.valueOf(idDriver))
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.NOT_FOUND.value(),
                        "Проверьте id водителя"))));
    }

    @Test
    void whenShowGreenMoneyThenSuccess() throws Exception {
        double money = 3.0;
        Mockito.when(moneyService.showGreenMoney(anyInt())).thenReturn(money);

        mockMvc.perform(
                        get("/money/showGreen")
                                .param("idDriver", String.valueOf(idDriver))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString((int)money)));
    }

    @Test
    void whenShowGreenMoneyThenFailedIdDriverNotFound() throws Exception {
        double money = -1;
        Mockito.when(moneyService.showGreenMoney(anyInt())).thenReturn(money);

        mockMvc.perform(
                        get("/money/showGreen")
                                .param("idDriver", String.valueOf(idDriver))
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.NOT_FOUND.value(),
                        "Проверьте id водителя"))));
    }

    @Test
    void whenShowRedMoneyThenSuccess() throws Exception {
        double money = 3.0;
        Mockito.when(moneyService.showRedMoney(anyInt())).thenReturn(money);

        mockMvc.perform(
                        get("/money/showRed")
                                .param("idDriver", String.valueOf(idDriver))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString((int)money)));
    }

    @Test
    void whenShowRedMoneyThenFailedIdDriverNotFound() throws Exception {
        double money = -1;
        Mockito.when(moneyService.showRedMoney(anyInt())).thenReturn(money);

        mockMvc.perform(
                        get("/money/showRed")
                                .param("idDriver", String.valueOf(idDriver))
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new ApplicationMessage(HttpStatus.NOT_FOUND.value(),
                        "Проверьте id водителя"))));
    }
}
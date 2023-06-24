package com.example.service_driver.unit.service;

import com.example.service_driver.entities.Driver;
import com.example.service_driver.repository.DriverRepository;
import com.example.service_driver.service.MoneyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MoneyServiceTest {

    private static final double RED_TO_GREEN = 2.5;
    private static final double GREEN_TO_BLUE = 0.6;

    @InjectMocks
    private MoneyService moneyService;

    @Mock
    private DriverRepository driverRepository;

    private final Driver driverIdAndManyMoney = new Driver(1, 50.0);
    private final Driver driverIdAndNotEnoughMoney = new Driver(1, 1.0);

    @Test
    void addRedMoneyThenSuccess() {
        int money = 3;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.of(driverIdAndManyMoney));

        boolean actual = moneyService.addRedMoney(driverIdAndManyMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        verify(driverRepository).save(driverIdAndManyMoney);
        assertTrue(actual);
    }

    @Test
    void addRedMoneyThenFailedIdDriverNotFound() {
        int money = 3;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.empty());

        boolean actual = moneyService.addRedMoney(driverIdAndManyMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        assertFalse(actual);
    }

    @Test
    void addRedMoneyThenFailedAddMoneyZero() {
        int money = 0;

        boolean actual = moneyService.addRedMoney(driverIdAndManyMoney.getId(), money);

        assertFalse(actual);
    }

    @Test
    void addRedMoneyThenFailedAddMoneyNegative() {
        int money = -1;

        boolean actual = moneyService.addRedMoney(driverIdAndManyMoney.getId(), money);

        assertFalse(actual);
    }

    @Test
    void addGreenMoneyThenSuccess() {
        int money = 3;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.of(driverIdAndManyMoney));

        boolean actual = moneyService.addGreenMoney(driverIdAndManyMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        verify(driverRepository).save(driverIdAndManyMoney);
        assertTrue(actual);
    }

    @Test
    void addGreenMoneyThenFailedIdDriverNotFound() {
        int money = 3;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.empty());

        boolean actual = moneyService.addGreenMoney(driverIdAndManyMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        assertFalse(actual);
    }

    @Test
    void addGreenMoneyThenFailedAddMoneyZero() {
        int money = 0;

        boolean actual = moneyService.addGreenMoney(driverIdAndManyMoney.getId(), money);

        assertFalse(actual);
    }

    @Test
    void addGreenMoneyThenFailedAddMoneyNegative() {
        int money = -1;

        boolean actual = moneyService.addGreenMoney(driverIdAndManyMoney.getId(), money);

        assertFalse(actual);
    }

    @Test
    void addBlueMoneyThenSuccess() {
        int money = 3;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.of(driverIdAndManyMoney));

        boolean actual = moneyService.addBlueMoney(driverIdAndManyMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        verify(driverRepository).save(driverIdAndManyMoney);
        assertTrue(actual);
    }

    @Test
    void addBlueMoneyThenFailedIdDriverNotFound() {
        int money = 3;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.empty());

        boolean actual = moneyService.addBlueMoney(driverIdAndManyMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        assertFalse(actual);
    }

    @Test
    void addGBlueMoneyThenFailedAddMoneyZero() {
        int money = 0;

        boolean actual = moneyService.addBlueMoney(driverIdAndManyMoney.getId(), money);

        assertFalse(actual);
    }

    @Test
    void addBlueMoneyThenFailedAddMoneyNegative() {
        int money = -1;

        boolean actual = moneyService.addBlueMoney(driverIdAndManyMoney.getId(), money);

        assertFalse(actual);
    }

    @Test
    void getRedMoneyThenSuccess() {
        int money = 3;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.of(driverIdAndManyMoney));

        boolean actual = moneyService.getRedMoney(driverIdAndManyMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        verify(driverRepository).save(driverIdAndManyMoney);
        assertTrue(actual);
    }

    @Test
    void getRedMoneyThenFailedIdDriverNotFound() {
        int money = 3;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.empty());

        boolean actual = moneyService.getRedMoney(driverIdAndManyMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        assertFalse(actual);
    }

    @Test
    void getRedMoneyThenFailedNotEnoughMoney() {
        int money = 10;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.of(driverIdAndNotEnoughMoney));

        boolean actual = moneyService.getRedMoney(driverIdAndNotEnoughMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndNotEnoughMoney.getId());
        assertFalse(actual);
    }

    @Test
    void getGreenMoneyThenSuccess() {
        int money = 3;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.of(driverIdAndManyMoney));

        boolean actual = moneyService.getGreenMoney(driverIdAndManyMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        verify(driverRepository).save(driverIdAndManyMoney);
        assertTrue(actual);
    }

    @Test
    void getGreenMoneyThenFailedIdDriverNotFound() {
        int money = 3;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.empty());

        boolean actual = moneyService.getGreenMoney(driverIdAndManyMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        assertFalse(actual);
    }

    @Test
    void getGreenMoneyThenFailedNotEnoughMoney() {
        int money = 10;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.of(driverIdAndNotEnoughMoney));

        boolean actual = moneyService.getGreenMoney(driverIdAndNotEnoughMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndNotEnoughMoney.getId());
        assertFalse(actual);
    }

    @Test
    void getBlueMoneyThenSuccess() {
        int money = 3;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.of(driverIdAndManyMoney));

        boolean actual = moneyService.getBlueMoney(driverIdAndManyMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        verify(driverRepository).save(driverIdAndManyMoney);
        assertTrue(actual);
    }

    @Test
    void getBlueMoneyThenFailedIdDriverNotFound() {
        int money = 3;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.empty());

        boolean actual = moneyService.getBlueMoney(driverIdAndManyMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        assertFalse(actual);
    }

    @Test
    void getBlueMoneyThenFailedNotEnoughMoney() {
        int money = 10;
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.of(driverIdAndNotEnoughMoney));

        boolean actual = moneyService.getBlueMoney(driverIdAndNotEnoughMoney.getId(), money);

        verify(driverRepository).findById(driverIdAndNotEnoughMoney.getId());
        assertFalse(actual);
    }

    @Test
    void whenShowBlueMoneyThenSuccess() {
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.of(driverIdAndManyMoney));

        double actual = moneyService.showBlueMoney(driverIdAndManyMoney.getId());

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        assertEquals(driverIdAndManyMoney.getBlueMoney(), actual);
    }

    @Test
    void whenShowBlueMoneyThenFailedIdDriverNotFound() {
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.empty());

        double actual = moneyService.showBlueMoney(driverIdAndManyMoney.getId());

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        assertEquals(-1.0, actual);
    }

    @Test
    void whenShowGreenMoneyThenSuccess() {
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.of(driverIdAndManyMoney));

        double actual = moneyService.showGreenMoney(driverIdAndManyMoney.getId());
        double expected = driverIdAndManyMoney.getBlueMoney() * GREEN_TO_BLUE;

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        assertEquals(expected, actual);
    }

    @Test
    void whenShowGreenMoneyThenFailedIdDriverNotFound() {
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.empty());

        double actual = moneyService.showGreenMoney(driverIdAndManyMoney.getId());

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        assertEquals(-1.0, actual);
    }

    @Test
    void whenShowRedMoneyThenSuccess() {
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.of(driverIdAndManyMoney));

        double actual = moneyService.showRedMoney(driverIdAndManyMoney.getId());
        double expected = driverIdAndManyMoney.getBlueMoney() * GREEN_TO_BLUE / RED_TO_GREEN;

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        assertEquals(expected, actual);
    }

    @Test
    void whenShowRedMoneyThenFailedIdDriverNotFound() {
        Mockito.when(driverRepository.findById(anyInt())).thenReturn(Optional.empty());

        double actual = moneyService.showRedMoney(driverIdAndManyMoney.getId());

        verify(driverRepository).findById(driverIdAndManyMoney.getId());
        assertEquals(-1.0, actual);
    }
}
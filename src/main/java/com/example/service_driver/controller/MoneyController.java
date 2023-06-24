package com.example.service_driver.controller;

import com.example.service_driver.service.MoneyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.service_driver.controller.ApplicationMessage.response;

@RestController
@RequestMapping("/money")
public class MoneyController {

    private final MoneyService moneyService;

    public MoneyController(MoneyService moneyService) {
        this.moneyService = moneyService;
    }

    @PutMapping("/addRed")
    public ResponseEntity<?> addRedMoney(@RequestParam int idDriver, @RequestParam int money) {
        boolean rsl = moneyService.addRedMoney(idDriver, money);
        if (rsl) return response("Счет успешно обновлен", HttpStatus.OK);
        return response("Проверьте id водителя / сумму пополнения", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/addGreen")
    public ResponseEntity<?> addGreenMoney(@RequestParam int idDriver, @RequestParam int money) {
        boolean rsl = moneyService.addGreenMoney(idDriver, money);
        if (rsl) return response("Счет успешно обновлен", HttpStatus.OK);
        return response("Проверьте id водителя / сумму пополнения", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/addBlue")
    public ResponseEntity<?> addBlueMoney(@RequestParam int idDriver, @RequestParam int money) {
        boolean rsl = moneyService.addBlueMoney(idDriver, money);
        if (rsl) return response("Счет успешно обновлен", HttpStatus.OK);
        return response("Проверьте id водителя / сумму пополнения", HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/getRed")
    public ResponseEntity<?> getRedMoney(@RequestParam int idDriver, @RequestParam int money) {
        boolean rsl = moneyService.getRedMoney(idDriver, money);
        if (rsl) return response("Операция снятия завершена успешно", HttpStatus.OK);
        return response("Проверьте id водителя / сумму для снятия", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/getGreen")
    public ResponseEntity<?> getGreenMoney(@RequestParam int idDriver, @RequestParam int money) {
        boolean rsl = moneyService.getGreenMoney(idDriver, money);
        if (rsl) return response("Операция снятия завершена успешно", HttpStatus.OK);
        return response("Проверьте id водителя / сумму для снятия", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/getBlue")
    public ResponseEntity<?> getBlueMoney(@RequestParam int idDriver, @RequestParam int money) {
        boolean rsl = moneyService.getBlueMoney(idDriver, money);
        if (rsl) return response("Операция снятия завершена успешно", HttpStatus.OK);
        return response("Проверьте id водителя / сумму для снятия", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/showBlue")
    public ResponseEntity<?> showBlueMoney(@RequestParam int idDriver) {
        int rsl = (int) moneyService.showBlueMoney(idDriver);
        if (rsl != -1) return new ResponseEntity<>(rsl, HttpStatus.OK);
        return response("Проверьте id водителя", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/showGreen")
    public ResponseEntity<?> showGreenMoney(@RequestParam int idDriver) {
        int rsl = (int) moneyService.showGreenMoney(idDriver);
        if (rsl != -1) return new ResponseEntity<>(rsl, HttpStatus.OK);
        return response("Проверьте id водителя", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/showRed")
    public ResponseEntity<?> showRedMoney(@RequestParam int idDriver) {
        int rsl = (int) moneyService.showRedMoney(idDriver);
        if (rsl != -1) return new ResponseEntity<>(rsl, HttpStatus.OK);
        return response("Проверьте id водителя", HttpStatus.NOT_FOUND);
    }

}

package com.example.service_driver;

import com.example.service_driver.controller.ApplicationMessage;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.net.ConnectException;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ApplicationMessage> handleException(PSQLException e) {
        return new ResponseEntity<>(new ApplicationMessage(HttpStatus.BAD_REQUEST.value(),
                "Проверьте правильность заполнения JSON"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApplicationMessage> handleException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ApplicationMessage(HttpStatus.BAD_REQUEST.value(),
                "Указаны недопустимые параметры запроса"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ApplicationMessage> handleException(ConnectException e) {
        return new ResponseEntity<>(new ApplicationMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Сервис учета автомобилей и деталей не отвечает"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ApplicationMessage> handleException(HttpClientErrorException.NotFound e) {
        return new ResponseEntity<>(new ApplicationMessage(HttpStatus.NOT_FOUND.value(),
                "VIN автомобиля отсутствует в базе данных"),
                HttpStatus.NOT_FOUND);
    }

}

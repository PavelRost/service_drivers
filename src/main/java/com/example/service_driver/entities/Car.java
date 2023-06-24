package com.example.service_driver.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class Car {

    @JsonProperty("id")
    private int id;
    @JsonProperty("vin")
    private String vin;
    @JsonProperty("regNumber")
    private String regNumber;
    @JsonProperty("manufacturer")
    private String manufacturer;
    @JsonProperty("brand")
    private String brand;
    @JsonProperty("yearManufacture")
    private String yearManufacture;
    @JsonProperty("details")
    List<Detail> details;

    public Car() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id && Objects.equals(vin, car.vin) && Objects.equals(regNumber, car.regNumber) && Objects.equals(manufacturer, car.manufacturer) && Objects.equals(brand, car.brand) && Objects.equals(yearManufacture, car.yearManufacture) && Objects.equals(details, car.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vin, regNumber, manufacturer, brand, yearManufacture, details);
    }
}

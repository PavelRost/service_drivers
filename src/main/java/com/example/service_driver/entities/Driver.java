package com.example.service_driver.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String lastName;
    private String firstName;
    private String patronymic;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthdate;

    private String passport;
    private String license;
    private int experience;
    private double blueMoney;

    @ElementCollection
    @CollectionTable(name = "driver_car", joinColumns = @JoinColumn(name = "driver_id"))
    @Column(name = "vin_car")
    private Set<String> vinCars = new HashSet<>();

    @Transient
    @JsonProperty("car")
    private Set<Car> cars = new HashSet<>();

    public Driver() {
    }

    public Driver(int id) {
        this.id = id;
    }

    public Driver(int id, double blueMoney) {
        this.id = id;
        this.blueMoney = blueMoney;
    }

    public Driver(int id, String lastName, String passport, Set<String> vinCars) {
        this.id = id;
        this.lastName = lastName;
        this.passport = passport;
        this.vinCars = vinCars;
    }

    public double getBlueMoney() {
        return blueMoney;
    }

    public void setBlueMoney(double blueMoney) {
        this.blueMoney = blueMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void addVinCar(String vinCar) {
        vinCars.add(vinCar);
    }

    public void deleteVinVar(String vinCar) {
        vinCars.remove(vinCar);
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getPassport() {
        return passport;
    }

    public String getLicense() {
        return license;
    }

    public int getExperience() {
        return experience;
    }

    public Set<String> getVinCars() {
        return vinCars;
    }

    public void addCar(Car car) {
        cars.add(car);
    }
}

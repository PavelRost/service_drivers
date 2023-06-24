package com.example.service_driver.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


public class Detail {

    @JsonProperty("id")
    private int id;
    @JsonProperty("serialNumber")
    private String serialNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Detail detail = (Detail) o;
        return id == detail.id && Objects.equals(serialNumber, detail.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber);
    }
}

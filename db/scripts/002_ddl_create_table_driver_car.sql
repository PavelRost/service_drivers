CREATE TABLE if NOT EXISTS driver_car(
    id SERIAL PRIMARY KEY,
    driver_id INT NOT NULL REFERENCES drivers(id),
    vin_car VARCHAR(20) NOT NULL
);
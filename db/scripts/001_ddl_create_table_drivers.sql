CREATE TABLE if NOT EXISTS drivers(
    id SERIAL PRIMARY KEY,
    last_name VARCHAR(20) NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    patronymic VARCHAR(20) NULL,
    birthdate TIMESTAMP NOT NULL,
    passport CHAR(10) NOT NULL,
    license VARCHAR(10) NOT NULL,
    experience INT NOT NULL,
    blue_money NUMERIC NOT NULL
);
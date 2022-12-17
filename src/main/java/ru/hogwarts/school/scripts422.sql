CREATE TABLE cars
(
    id    INTEGER PRIMARY KEY,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    price NUMERIC(12, 2),
    UNIQUE (brand, model)
);

CREATE TABLE people
(
    name           VARCHAR(150) PRIMARY KEY,
    age            INTEGER CHECK ( age > 14 ) NOT NULL,
    driver_license BOOLEAN                    NOT NULL,
    car_id         INTEGER REFERENCES cars (id)
);
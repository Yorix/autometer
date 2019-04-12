USE carcalculator;

CREATE TABLE IF NOT EXISTS img
(
  filename VARCHAR(255) NOT NULL PRIMARY KEY,
  car_id   INT          NOT NULL,
  FOREIGN KEY (car_id) REFERENCES car (id)
);


CREATE SCHEMA IF NOT EXISTS autometer;
RENAME TABLE
  carcalculator.car TO autometer.car,
  carcalculator.img TO autometer.img,
  carcalculator.note TO autometer.note,
  carcalculator.visit TO autometer.visit;

DROP SCHEMA IF EXISTS carcalculator;
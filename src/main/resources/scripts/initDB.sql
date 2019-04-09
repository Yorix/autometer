DROP SCHEMA IF EXISTS carcalculator;
CREATE SCHEMA carcalculator;
USE carcalculator;

CREATE TABLE visit
(
  id          INT PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(100)
);
CREATE TABLE car
(
  id    INT PRIMARY KEY AUTO_INCREMENT,
  make  VARCHAR(100) NOT NULL,
  model VARCHAR(100) NOT NULL
);
CREATE TABLE note
(
  id          INT PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(255),
  value       DECIMAL(11, 2),
  date        VARCHAR(10) NOT NULL,
  car_id      INT         NOT NULL,
  FOREIGN KEY (car_id) REFERENCES car (id)
);
CREATE TABLE img
(
  filename VARCHAR(255) NOT NULL PRIMARY KEY,
  car_id   INT          NOT NULL,
  FOREIGN KEY (car_id) REFERENCES car (id)
)
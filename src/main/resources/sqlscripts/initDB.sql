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
  id           INT PRIMARY KEY AUTO_INCREMENT,
  make         VARCHAR(100) NOT NULL,
  model        VARCHAR(100) NOT NULL,
  img_filename VARCHAR(255)
);
CREATE TABLE note
(
  id          INT PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(255),
  value       DECIMAL(11, 2),
  car_id      INT      NOT NULL,
  date        DATETIME NOT NULL,
  FOREIGN KEY (car_id) REFERENCES car (id)
);
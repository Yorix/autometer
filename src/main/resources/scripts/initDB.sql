DROP SCHEMA IF EXISTS autometer;
CREATE SCHEMA autometer;
USE autometer;
SET NAMES 'utf8';

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
  img_filename VARCHAR(255) NOT NULL
);
CREATE TABLE note
(
  id          INT PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(255),
  value       DECIMAL(11, 2),
  date        VARCHAR(20) NOT NULL,
  car_id      INT         NOT NULL,
  FOREIGN KEY (car_id) REFERENCES car (id)
);
CREATE TABLE img
(
  filename VARCHAR(255) NOT NULL PRIMARY KEY,
  car_id   INT          NOT NULL,
  FOREIGN KEY (car_id) REFERENCES car (id)
);
CREATE TABLE param
(
  name  VARCHAR(50) NOT NULL PRIMARY KEY,
  value DECIMAL(11, 2) DEFAULT 0
);
INSERT INTO param VALUE ('budget', 0);
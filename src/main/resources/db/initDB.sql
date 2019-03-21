USE carcalculator;
DROP TABLE IF EXISTS note, car, visit;
CREATE TABLE visit
(
  id          INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  description VARCHAR(255)
);
CREATE TABLE car
(
  id    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  make  VARCHAR(255)    NOT NULL,
  model VARCHAR(255)
);
CREATE TABLE note
(
  id     INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  value  VARCHAR(255),
  car_id INT             NOT NULL,
  FOREIGN KEY (car_id) REFERENCES car (id)
);
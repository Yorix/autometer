USE carcalculator;
DROP TABLE IF EXISTS note, car, visit;
CREATE TABLE visit
(
  id          INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  description VARCHAR(100)
);
CREATE TABLE car
(
  id           INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  make         VARCHAR(100)    NOT NULL,
  model        VARCHAR(100),
  img_filename VARCHAR(255)
);
CREATE TABLE note
(
  date   DATE PRIMARY KEY NOT NULL,
  value  VARCHAR(255),
  car_id INT              NOT NULL,
  FOREIGN KEY (car_id) REFERENCES car (id)
);
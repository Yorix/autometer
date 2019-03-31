USE carcalculator;
DROP TABLE IF EXISTS note, car, visit;
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
  id     INT PRIMARY KEY AUTO_INCREMENT,
  value  VARCHAR(255),
  car_id INT  NOT NULL,
  date   DATE NOT NULL,
  FOREIGN KEY (car_id) REFERENCES car (id)
);
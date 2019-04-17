USE autometer;

CREATE TABLE IF NOT EXISTS param
(
    name  VARCHAR(50) NOT NULL PRIMARY KEY,
    value INT DEFAULT 0
);
INSERT INTO param VALUE ('budget', '0');
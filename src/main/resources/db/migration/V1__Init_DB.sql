CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
);

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

CREATE TABLE `car` (
  `id` int(11) NOT NULL,
  `make` varchar(255) NOT NULL,
  `model` varchar(255) NOT NULL,
  `img_filename` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `img` (
  `filename` varchar(191) NOT NULL,
  `car_id` int(11) NOT NULL,
  PRIMARY KEY (`filename`),
  KEY `img_car_fk` (`car_id`)
);

CREATE TABLE `note` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `value` double NOT NULL,
  `date` date,
  `car_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `note_car_fk` (`car_id`)
);

CREATE TABLE `param` (
  `name` varchar(50) NOT NULL,
  `value` double DEFAULT NULL,
  PRIMARY KEY (`name`)
);

CREATE TABLE `user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
);

CREATE TABLE `user_role` (
  `user_name` varchar(50) NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `user_role_user_fk` (`user_name`)
);

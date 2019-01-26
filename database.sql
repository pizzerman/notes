CREATE DATABASE IF NOT EXISTS `notes`;

USE `notes`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  
  PRIMARY KEY (`username`)
);


DROP TABLE IF EXISTS `authorities`;

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  
  UNIQUE KEY `authorities_idx_1` (`username`, `authority`),
  
  CONSTRAINT `authorities_ibfk_1`
  FOREIGN KEY (`username`)
  REFERENCES `users` (`username`)
);


DROP TABLE IF EXISTS `notes`;

CREATE TABLE `notes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `text` varchar(255) NOT NULL,
  `username` varchar(50) NOT NULL,
  
  PRIMARY KEY (`id`),
  
  CONSTRAINT `notes_ibfk_1`
  FOREIGN KEY (`username`) 
  REFERENCES `users` (`username`)
  ON DELETE NO ACTION ON UPDATE NO ACTION
);


DROP TABLE IF EXISTS `censorship`;

CREATE TABLE `censorship` (
  `censored_phrase` varchar(45) NOT NULL,
  
  PRIMARY KEY (`censored_phrase`)
);


DROP TABLE IF EXISTS `users_censorship`;

CREATE TABLE `users_censorship` (
  `username` varchar(50) NOT NULL,
  `censored_phrase` varchar(45) NOT NULL,
  
  PRIMARY KEY (`username`, `censored_phrase`),
  
  CONSTRAINT `users_censorship_ibfk_1`
  FOREIGN KEY (`username`) 
  REFERENCES `users` (`username`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `users_censorship_ibfk_2`
  FOREIGN KEY (`censored_phrase`) 
  REFERENCES `censorship` (`censored_phrase`)
  ON DELETE NO ACTION ON UPDATE NO ACTION
);

SET FOREIGN_KEY_CHECKS = 1;
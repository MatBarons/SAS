-- Adminer 4.8.1 MySQL 8.1.0 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP TABLE IF EXISTS `Events`;
CREATE TABLE `Events` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `date_start` date DEFAULT NULL,
  `date_end` date DEFAULT NULL,
  `expected_participants` int DEFAULT NULL,
  `num_services` int DEFAULT NULL,
  `organizer_id` int NOT NULL,
  `chef_id` int NOT NULL,
  `client` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `notes` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `docs` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `Events` (`id`, `name`, `date_start`, `date_end`, `expected_participants`, `num_services`, `organizer_id`, `chef_id`, `client`, `notes`, `docs`) VALUES
(1,	'Convegno Agile Community',	'2020-09-25',	'2020-09-25',	100,	NULL,	2,	2,	'',	NULL,	NULL),
(2,	'Compleanno di Manuela',	'2020-08-13',	'2020-08-13',	25,	NULL,	2,	2,	'',	NULL,	NULL),
(3,	'Fiera del Sedano Rapa',	'2020-10-02',	'2020-10-04',	400,	NULL,	1,	2,	'',	NULL,	NULL);

DROP TABLE IF EXISTS `MenuFeatures`;
CREATE TABLE `MenuFeatures` (
  `menu_id` int NOT NULL,
  `name` varchar(128) NOT NULL DEFAULT '',
  `value` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `MenuFeatures` (`menu_id`, `name`, `value`) VALUES
(80,	'Richiede cuoco',	0),
(80,	'Buffet',	0),
(80,	'Richiede cucina',	0),
(80,	'Finger food',	0),
(80,	'Piatti caldi',	0),
(82,	'Richiede cuoco',	0),
(82,	'Buffet',	0),
(82,	'Richiede cucina',	0),
(82,	'Finger food',	0),
(82,	'Piatti caldi',	0),
(86,	'Richiede cuoco',	0),
(86,	'Buffet',	0),
(86,	'Richiede cucina',	0),
(86,	'Finger food',	0),
(86,	'Piatti caldi',	0);

DROP TABLE IF EXISTS `MenuItems`;
CREATE TABLE `MenuItems` (
  `id` int NOT NULL AUTO_INCREMENT,
  `menu_id` int NOT NULL,
  `section_id` int DEFAULT NULL,
  `description` tinytext,
  `recipe_id` int NOT NULL,
  `position` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `MenuItems` (`id`, `menu_id`, `section_id`, `description`, `recipe_id`, `position`) VALUES
(96,	80,	0,	'Croissant vuoti',	9,	0),
(97,	80,	0,	'Croissant alla marmellata',	9,	1),
(98,	80,	0,	'Pane al cioccolato mignon',	10,	2),
(99,	80,	0,	'Panini al latte con prosciutto crudo',	12,	4),
(100,	80,	0,	'Panini al latte con prosciutto cotto',	12,	5),
(101,	80,	0,	'Panini al latte con formaggio spalmabile alle erbe',	12,	6),
(102,	80,	0,	'Girelle all\'uvetta mignon',	11,	3),
(103,	82,	0,	'Biscotti',	13,	1),
(104,	82,	0,	'Lingue di gatto',	14,	2),
(105,	82,	0,	'Bigné alla crema',	15,	3),
(106,	82,	0,	'Bigné al caffè',	15,	4),
(107,	82,	0,	'Pizzette',	16,	5),
(108,	82,	0,	'Croissant al prosciutto crudo mignon',	9,	6),
(109,	82,	0,	'Tramezzini tonno e carciofini mignon',	17,	7),
(112,	86,	41,	'Vitello tonnato',	1,	0),
(113,	86,	41,	'Carpaccio di spada',	2,	1),
(114,	86,	41,	'Alici marinate',	3,	2),
(115,	86,	42,	'Penne alla messinese',	5,	0),
(116,	86,	42,	'Risotto alla zucca',	20,	1),
(117,	86,	43,	'Salmone al forno',	8,	0),
(118,	86,	44,	'Sorbetto al limone',	18,	0),
(119,	86,	44,	'Torta Saint Honoré',	19,	1);

DROP TABLE IF EXISTS `MenuSections`;
CREATE TABLE `MenuSections` (
  `id` int NOT NULL AUTO_INCREMENT,
  `menu_id` int NOT NULL,
  `name` tinytext,
  `position` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `MenuSections` (`id`, `menu_id`, `name`, `position`) VALUES
(41,	86,	'Antipasti',	0),
(42,	86,	'Primi',	1),
(43,	86,	'Secondi',	2),
(44,	86,	'Dessert',	3),
(45,	87,	'Antipasti',	0);

DROP TABLE IF EXISTS `Menus`;
CREATE TABLE `Menus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` tinytext,
  `owner_id` int DEFAULT NULL,
  `published` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `Menus` (`id`, `title`, `owner_id`, `published`) VALUES
(80,	'Coffee break mattutino',	2,	1),
(82,	'Coffee break pomeridiano',	2,	1),
(86,	'Cena di compleanno pesce',	3,	1);

DROP TABLE IF EXISTS `Procedures`;
CREATE TABLE `Procedures` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` tinytext,
  `type` enum('recipe','preparation') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `Procedures` (`id`, `name`, `type`) VALUES
(1,	'Vitello tonnato',	'recipe'),
(2,	'Carpaccio di spada',	'recipe'),
(3,	'Alici marinate',	'recipe'),
(4,	'Insalata di riso',	'recipe'),
(5,	'Penne al sugo di baccalà',	'recipe'),
(6,	'Pappa al pomodoro',	'recipe'),
(7,	'Hamburger con bacon e cipolla caramellata',	'recipe'),
(8,	'Salmone al forno',	'recipe'),
(9,	'Croissant',	'recipe'),
(10,	'Pane al cioccolato',	'recipe'),
(11,	'Girelle all\'uvetta',	'recipe'),
(12,	'Panini al latte',	'recipe'),
(13,	'Biscotti di pasta frolla',	'recipe'),
(14,	'Lingue di gatto',	'recipe'),
(15,	'Bigné farciti',	'recipe'),
(16,	'Pizzette',	'recipe'),
(17,	'Tramezzini',	'recipe'),
(18,	'Sorbetto al limone',	'recipe'),
(19,	'Torta Saint Honoré',	'recipe'),
(20,	'Risotto alla zucca',	'recipe');

DROP TABLE IF EXISTS `Roles`;
CREATE TABLE `Roles` (
  `id` char(1) NOT NULL,
  `role` varchar(128) NOT NULL DEFAULT 'servizio',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `Roles` (`id`, `role`) VALUES
('c',	'cuoco'),
('h',	'chef'),
('o',	'organizzatore'),
('s',	'servizio');

DROP TABLE IF EXISTS `Services`;
CREATE TABLE `Services` (
  `id` int NOT NULL AUTO_INCREMENT,
  `event_id` int NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `proposed_menu_id` int NOT NULL DEFAULT '0',
  `approved_menu_id` int DEFAULT '0',
  `service_date` date DEFAULT NULL,
  `time_start` time DEFAULT NULL,
  `time_end` time DEFAULT NULL,
  `expected_participants` int DEFAULT NULL,
  `place` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `Services` (`id`, `event_id`, `name`, `proposed_menu_id`, `approved_menu_id`, `service_date`, `time_start`, `time_end`, `expected_participants`, `place`) VALUES
(1,	2,	'Cena',	86,	0,	'2020-08-13',	'20:00:00',	'23:30:00',	25,	NULL),
(2,	1,	'Coffee break mattino',	0,	80,	'2020-09-25',	'10:30:00',	'11:30:00',	100,	NULL),
(3,	1,	'Colazione di lavoro',	0,	0,	'2020-09-25',	'13:00:00',	'14:00:00',	80,	NULL),
(4,	1,	'Coffee break pomeriggio',	0,	82,	'2020-09-25',	'16:00:00',	'16:30:00',	100,	NULL),
(5,	1,	'Cena sociale',	0,	0,	'2020-09-25',	'20:00:00',	'22:30:00',	40,	NULL),
(6,	3,	'Pranzo giorno 1',	0,	0,	'2020-10-02',	'12:00:00',	'15:00:00',	200,	NULL),
(7,	3,	'Pranzo giorno 2',	0,	0,	'2020-10-03',	'12:00:00',	'15:00:00',	300,	NULL),
(8,	3,	'Pranzo giorno 3',	0,	0,	'2020-10-04',	'12:00:00',	'15:00:00',	400,	NULL);

DROP TABLE IF EXISTS `UserRoles`;
CREATE TABLE `UserRoles` (
  `user_id` int NOT NULL,
  `role_id` char(1) NOT NULL DEFAULT 's'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `UserRoles` (`user_id`, `role_id`) VALUES
(1,	'o'),
(2,	'o'),
(2,	'h'),
(3,	'h'),
(4,	'h'),
(4,	'c'),
(5,	'c'),
(6,	'c'),
(7,	'c'),
(8,	's'),
(9,	's'),
(10,	's'),
(7,	's');

DROP TABLE IF EXISTS `Users`;
CREATE TABLE `Users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO `Users` (`id`, `username`) VALUES
(1,	'Carlin'),
(2,	'Lidia'),
(3,	'Tony'),
(4,	'Marinella'),
(5,	'Guido'),
(6,	'Antonietta'),
(7,	'Paola'),
(8,	'Silvia'),
(9,	'Marco'),
(10,	'Piergiorgio');

-- 2023-09-02 12:36:15

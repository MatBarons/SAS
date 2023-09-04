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

DROP TABLE IF EXISTS `KitchenTasks`;
CREATE TABLE `KitchenTasks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `summarySheet_id` int DEFAULT NULL,
  `cook_id` int DEFAULT NULL,
  `shift_id` int DEFAULT NULL,
  `procedure_id` int DEFAULT NULL,
  `toDo` tinyint(1) NOT NULL DEFAULT '1',
  `completed` tinyint(1) NOT NULL DEFAULT '0',
  `estimatedTime` int DEFAULT NULL,
  `quantity` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  `position` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

INSERT INTO `KitchenTasks` (`id`, `summarySheet_id`, `cook_id`, `shift_id`, `procedure_id`, `toDo`, `completed`, `estimatedTime`, `quantity`, `position`) VALUES
(1,	1,	NULL,	NULL,	5,	1,	0,	70,	'800g',	NULL),
(2,	1,	NULL,	NULL,	8,	1,	0,	20,	'1.5kg',	NULL),
(3,	2,	5,	NULL,	15,	1,	0,	10,	'800g',	NULL),
(4,	2,	5,	NULL,	19,	1,	0,	40,	'200g',	NULL),
(5,	2,	NULL,	NULL,	11,	1,	0,	50,	'600g',	NULL),
(6,	2,	NULL,	NULL,	4,	1,	0,	50,	'150g',	NULL),
(7,	2,	5,	NULL,	17,	1,	0,	35,	'380g',	NULL),
(8,	1,	NULL,	NULL,	5,	1,	0,	70,	'800g',	NULL),
(9,	1,	NULL,	NULL,	8,	1,	0,	20,	'1.5kg',	NULL),
(10,	1,	NULL,	NULL,	15,	1,	0,	10,	'800g',	NULL),
(11,	1,	NULL,	NULL,	19,	1,	0,	40,	'200g',	NULL),
(12,	1,	NULL,	NULL,	11,	1,	0,	50,	'600g',	NULL),
(13,	1,	NULL,	NULL,	4,	1,	0,	50,	'150g',	NULL),
(14,	1,	NULL,	NULL,	17,	1,	0,	35,	'380g',	NULL),
(32,	2,	NULL,	NULL,	1,	1,	0,	10,	'5kg',	NULL),
(33,	2,	NULL,	NULL,	2,	1,	0,	NULL,	NULL,	NULL),
(34,	2,	NULL,	NULL,	1,	1,	0,	10,	'5kg',	NULL),
(35,	2,	NULL,	NULL,	2,	1,	0,	NULL,	NULL,	NULL),
(37,	2,	NULL,	NULL,	4,	1,	0,	NULL,	NULL,	NULL),
(38,	2,	NULL,	NULL,	4,	0,	0,	NULL,	NULL,	NULL),
(39,	10,	NULL,	NULL,	9,	1,	0,	NULL,	NULL,	1),
(40,	10,	NULL,	NULL,	9,	1,	0,	NULL,	NULL,	0),
(41,	10,	NULL,	NULL,	10,	1,	0,	NULL,	NULL,	2),
(42,	10,	NULL,	NULL,	11,	1,	0,	NULL,	NULL,	3),
(43,	10,	NULL,	NULL,	12,	1,	0,	NULL,	NULL,	4),
(44,	10,	NULL,	NULL,	12,	1,	0,	NULL,	NULL,	5),
(45,	10,	NULL,	NULL,	12,	1,	0,	NULL,	NULL,	6),
(46,	10,	NULL,	NULL,	1,	1,	0,	NULL,	NULL,	7);

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
(86,	'Piatti caldi',	0),
(89,	'Richiede cuoco',	0),
(89,	'Buffet',	0),
(89,	'Richiede cucina',	0),
(89,	'Finger food',	0),
(89,	'Piatti caldi',	0),
(90,	'Richiede cuoco',	1),
(90,	'Buffet',	1),
(90,	'Richiede cucina',	1),
(90,	'Finger food',	1),
(90,	'Piatti caldi',	1),
(91,	'Richiede cuoco',	0),
(91,	'Buffet',	0),
(91,	'Richiede cucina',	0),
(91,	'Finger food',	0),
(91,	'Piatti caldi',	0),
(92,	'Richiede cuoco',	0),
(92,	'Buffet',	0),
(92,	'Richiede cucina',	0),
(92,	'Finger food',	0),
(92,	'Piatti caldi',	0),
(93,	'Richiede cuoco',	0),
(93,	'Buffet',	0),
(93,	'Richiede cucina',	0),
(93,	'Finger food',	0),
(93,	'Piatti caldi',	0),
(94,	'Richiede cuoco',	0),
(94,	'Buffet',	0),
(94,	'Richiede cucina',	0),
(94,	'Finger food',	0),
(94,	'Piatti caldi',	0),
(95,	'Richiede cuoco',	0),
(95,	'Buffet',	0),
(95,	'Richiede cucina',	0),
(95,	'Finger food',	0),
(95,	'Piatti caldi',	0),
(96,	'Richiede cuoco',	0),
(96,	'Buffet',	0),
(96,	'Richiede cucina',	0),
(96,	'Finger food',	0),
(96,	'Piatti caldi',	0),
(97,	'Richiede cuoco',	1),
(97,	'Buffet',	1),
(97,	'Richiede cucina',	1),
(97,	'Finger food',	1),
(97,	'Piatti caldi',	1),
(99,	'Richiede cuoco',	0),
(99,	'Buffet',	0),
(99,	'Richiede cucina',	0),
(99,	'Finger food',	0),
(99,	'Piatti caldi',	0),
(100,	'Richiede cuoco',	0),
(100,	'Buffet',	0),
(100,	'Richiede cucina',	0),
(100,	'Finger food',	0),
(100,	'Piatti caldi',	0),
(101,	'Richiede cuoco',	0),
(101,	'Buffet',	0),
(101,	'Richiede cucina',	0),
(101,	'Finger food',	0),
(101,	'Piatti caldi',	0),
(102,	'Richiede cuoco',	0),
(102,	'Buffet',	0),
(102,	'Richiede cucina',	0),
(102,	'Finger food',	0),
(102,	'Piatti caldi',	0),
(103,	'Richiede cuoco',	0),
(103,	'Buffet',	0),
(103,	'Richiede cucina',	0),
(103,	'Finger food',	0),
(103,	'Piatti caldi',	0),
(104,	'Richiede cuoco',	0),
(104,	'Buffet',	0),
(104,	'Richiede cucina',	0),
(104,	'Finger food',	0),
(104,	'Piatti caldi',	0),
(105,	'Richiede cuoco',	0),
(105,	'Buffet',	0),
(105,	'Richiede cucina',	0),
(105,	'Finger food',	0),
(105,	'Piatti caldi',	0),
(106,	'Richiede cuoco',	0),
(106,	'Buffet',	0),
(106,	'Richiede cucina',	0),
(106,	'Finger food',	0),
(106,	'Piatti caldi',	0),
(107,	'Richiede cuoco',	0),
(107,	'Buffet',	0),
(107,	'Richiede cucina',	0),
(107,	'Finger food',	0),
(107,	'Piatti caldi',	0),
(108,	'Richiede cuoco',	0),
(108,	'Buffet',	0),
(108,	'Richiede cucina',	0),
(108,	'Finger food',	0),
(108,	'Piatti caldi',	0),
(109,	'Richiede cuoco',	0),
(109,	'Buffet',	0),
(109,	'Richiede cucina',	0),
(109,	'Finger food',	0),
(109,	'Piatti caldi',	0);

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
(119,	86,	44,	'Torta Saint Honoré',	19,	1),
(121,	90,	50,	'Vitello tonnato',	1,	0),
(122,	90,	50,	'Carpaccio di spada',	2,	1),
(123,	90,	50,	'Alici marinate',	3,	2),
(124,	90,	52,	'Pane al cioccolato',	10,	0),
(125,	90,	52,	'Girelle all\'uvetta',	11,	1),
(126,	90,	0,	'Penne al sugo di baccalà',	5,	0),
(127,	90,	0,	'Salmone al forno',	8,	1),
(128,	97,	71,	'Vitello tonnato',	1,	0),
(129,	97,	71,	'Carpaccio di spada',	2,	1),
(130,	97,	71,	'Alici marinate',	3,	2),
(131,	97,	73,	'Hamburger con bacon e cipolla caramellata',	7,	0),
(132,	97,	73,	'Salmone al forno',	8,	1),
(133,	97,	0,	'Insalata di riso',	4,	0),
(134,	97,	0,	'Penne al sugo di baccalà',	5,	1),
(142,	99,	77,	'Vitello tonnato',	1,	0),
(143,	99,	77,	'Carpaccio di spada',	2,	1),
(144,	99,	77,	'Alici marinate',	3,	2),
(145,	99,	79,	'Hamburger con bacon e cipolla caramellata',	7,	0),
(146,	99,	79,	'Salmone al forno',	8,	1),
(147,	99,	0,	'Insalata di riso',	4,	0),
(148,	99,	0,	'Penne al sugo di baccalà',	5,	1),
(149,	100,	80,	'Vitello tonnato',	1,	0),
(150,	100,	80,	'Carpaccio di spada',	2,	1),
(151,	100,	80,	'Alici marinate',	3,	2),
(152,	100,	82,	'Hamburger con bacon e cipolla caramellata',	7,	0),
(153,	100,	82,	'Salmone al forno',	8,	1),
(154,	100,	0,	'Insalata di riso',	4,	0),
(155,	100,	0,	'Penne al sugo di baccalà',	5,	1),
(159,	101,	85,	'Hamburger con bacon e cipolla caramellata',	7,	0),
(160,	101,	85,	'Salmone al forno',	8,	1),
(161,	101,	0,	'Insalata di riso',	4,	0),
(162,	101,	0,	'Penne al sugo di baccalà',	5,	1),
(166,	102,	88,	'Hamburger con bacon e cipolla caramellata',	7,	0),
(167,	102,	88,	'Salmone al forno',	8,	1),
(168,	102,	0,	'Insalata di riso',	4,	0),
(169,	102,	0,	'Penne al sugo di baccalà',	5,	1),
(170,	102,	0,	'Vitello tonnato',	1,	0),
(171,	102,	0,	'Carpaccio di spada',	2,	1),
(172,	102,	0,	'Alici marinate',	3,	2),
(173,	103,	89,	'Vitello tonnato',	1,	0),
(174,	103,	89,	'Carpaccio di spada',	2,	1),
(175,	103,	89,	'Alici marinate',	3,	2),
(176,	103,	91,	'Hamburger con bacon e cipolla caramellata',	7,	0),
(177,	103,	91,	'Salmone al forno',	8,	1),
(178,	103,	0,	'Insalata di riso',	4,	0),
(179,	103,	0,	'Penne al sugo di baccalà',	5,	1),
(180,	104,	92,	'Vitello tonnato',	1,	0),
(181,	104,	92,	'Carpaccio di spada',	2,	1),
(182,	104,	92,	'Alici marinate',	3,	2),
(183,	104,	94,	'Hamburger con bacon e cipolla caramellata',	7,	0),
(184,	104,	94,	'Salmone al forno',	8,	1),
(185,	104,	0,	'Insalata di riso',	4,	0),
(186,	104,	0,	'Penne al sugo di baccalà',	5,	1),
(187,	105,	95,	'Vitello tonnato',	1,	2),
(188,	105,	95,	'Carpaccio di spada',	2,	0),
(189,	105,	95,	'Alici marinate',	3,	1),
(190,	105,	97,	'Hamburger con bacon e cipolla caramellata',	7,	0),
(191,	105,	97,	'Salmone al forno',	8,	1),
(192,	105,	0,	'Insalata di riso',	4,	1),
(193,	105,	0,	'Penne al sugo di baccalà',	5,	0),
(194,	106,	100,	'Vitello tonnato',	1,	0),
(195,	106,	98,	'Carpaccio di spada',	2,	1),
(196,	106,	98,	'Alici marinate',	3,	2),
(197,	106,	99,	'Hamburger con bacon e cipolla caramellata',	7,	0),
(198,	106,	0,	'Salmone al forno',	8,	1),
(199,	106,	0,	'Insalata di riso',	4,	0),
(200,	106,	98,	'Penne al sugo di baccalà',	5,	1),
(201,	107,	101,	'Vitello tonnato',	1,	0),
(202,	107,	101,	'Carpaccio di spada',	2,	1),
(203,	107,	101,	'Alici marinate',	3,	2),
(204,	107,	103,	'Hamburger con bacon e cipolla caramellata',	7,	0),
(205,	107,	103,	'Salmone al forno',	8,	1),
(206,	107,	0,	'Insalata di riso',	4,	0),
(207,	107,	0,	'Penne al sugo di baccalà',	5,	1),
(209,	108,	104,	'Carpaccio di spada',	2,	0),
(210,	108,	104,	'Alici marinate',	3,	1),
(212,	108,	106,	'Salmone al forno',	8,	0),
(213,	108,	0,	'Insalata di riso',	4,	0),
(216,	109,	107,	'Carpaccio di spada',	2,	0),
(217,	109,	107,	'Alici marinate',	3,	1),
(219,	109,	109,	'Salmone al forno',	8,	0),
(220,	109,	0,	'Insalata di riso',	4,	0);

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
(45,	87,	'Antipasti',	0),
(47,	89,	'Antipasti',	0),
(48,	89,	'Primi',	1),
(49,	89,	'Secondi',	2),
(50,	90,	'Antipasti',	0),
(51,	90,	'Primi',	1),
(52,	90,	'Secondi',	2),
(53,	91,	'Antipasti',	0),
(54,	91,	'Primi',	1),
(55,	91,	'Secondi',	2),
(56,	92,	'Antipasti',	0),
(57,	92,	'Primi',	1),
(58,	92,	'Secondi',	2),
(59,	93,	'Antipasti',	0),
(60,	93,	'Primi',	1),
(61,	93,	'Secondi',	2),
(62,	94,	'Antipasti',	0),
(63,	94,	'Primi',	1),
(64,	94,	'Secondi',	2),
(65,	95,	'Antipasti',	0),
(66,	95,	'Primi',	1),
(67,	95,	'Secondi',	2),
(68,	96,	'Antipasti',	0),
(69,	96,	'Primi',	1),
(70,	96,	'Secondi',	2),
(71,	97,	'Antipasti',	0),
(72,	97,	'Primi',	1),
(73,	97,	'Secondi',	2),
(77,	99,	'Antipasti',	0),
(78,	99,	'Primi',	1),
(79,	99,	'Secondi',	2),
(80,	100,	'Antipasti',	0),
(81,	100,	'Primi',	1),
(82,	100,	'Secondi',	2),
(85,	101,	'Secondi',	2),
(87,	102,	'Primi',	1),
(88,	102,	'Secondi',	2),
(89,	103,	'Hors d\'Oeuvres',	0),
(90,	103,	'Primi',	1),
(91,	103,	'Secondi',	2),
(92,	104,	'Antipasti',	2),
(93,	104,	'Primi',	1),
(94,	104,	'Secondi',	0),
(95,	105,	'Antipasti',	0),
(96,	105,	'Primi',	1),
(97,	105,	'Secondi',	2),
(98,	106,	'Antipasti',	0),
(99,	106,	'Primi',	1),
(100,	106,	'Secondi',	2),
(101,	107,	'Antipasti',	0),
(102,	107,	'Primi',	1),
(103,	107,	'Secondi',	2),
(104,	108,	'Antipasti',	0),
(105,	108,	'Primi',	1),
(106,	108,	'Secondi',	2),
(107,	109,	'Antipasti',	0),
(108,	109,	'Primi',	1),
(109,	109,	'Secondi',	2);

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
(86,	'Cena di compleanno pesce',	3,	1),
(89,	'Menu Pinco Pallino',	2,	0),
(90,	'Titolo Nuovo',	2,	1),
(91,	'Menu da Cancellare',	2,	0),
(92,	'Menu da Cancellare',	2,	0),
(93,	'Menu da Cancellare',	2,	0),
(94,	'Menu da Cancellare',	2,	0),
(95,	'Menu da Cancellare',	2,	0),
(96,	'Menu da Cancellare',	2,	0),
(97,	'Titolo Nuovo',	2,	1),
(99,	'Menu da copiare',	2,	1),
(100,	'Menu da copiare',	2,	0),
(101,	'Menu Pinco Pallino',	2,	0),
(102,	'Menu Pinco Pallino',	2,	0),
(103,	'Menu Pinco Pallino',	2,	0),
(104,	'Menu Pinco Pallino',	2,	0),
(105,	'Menu Pinco Pallino',	2,	0),
(106,	'Menu Pinco Pallino',	2,	0),
(107,	'Menu Pinco Pallino',	2,	0),
(108,	'Menu Pinco Pallino',	2,	0),
(109,	'Menu Pinco Pallino',	2,	0);

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

DROP TABLE IF EXISTS `Shifts`;
CREATE TABLE `Shifts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `service_id` int NOT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `place` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'NULL',
  `date` date DEFAULT NULL,
  `type` enum('kitchen','service') COLLATE utf16_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

INSERT INTO `Shifts` (`id`, `service_id`, `start_time`, `end_time`, `place`, `date`, `type`) VALUES
(1,	2,	'07:00:00',	'08:00:00',	'NULL',	'2023-09-25',	'kitchen'),
(2,	2,	'12:00:00',	'13:00:00',	'NULL',	'2023-09-25',	'kitchen'),
(3,	3,	'07:00:00',	'08:00:00',	'NULL',	'2023-09-26',	'kitchen'),
(4,	3,	'12:00:00',	'13:00:00',	'NULL',	'2023-09-26',	'kitchen'),
(5,	4,	'07:00:00',	'08:00:00',	'NULL',	'2023-09-27',	'kitchen'),
(6,	4,	'12:00:00',	'13:00:00',	'NULL',	'2023-09-27',	'kitchen');

DROP TABLE IF EXISTS `SummarySheets`;
CREATE TABLE `SummarySheets` (
  `id` int NOT NULL AUTO_INCREMENT,
  `service_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

INSERT INTO `SummarySheets` (`id`, `service_id`) VALUES
(1,	4),
(2,	2),
(3,	3),
(4,	1),
(10,	2);

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

-- 2023-09-04 16:43:18

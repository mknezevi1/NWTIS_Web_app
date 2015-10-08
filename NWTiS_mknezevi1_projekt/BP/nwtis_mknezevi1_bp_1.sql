-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 12, 2014 at 01:00 
-- Server version: 5.1.41
-- PHP Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `nwtis_mknezevi1_bp_1`
--

-- --------------------------------------------------------

--
-- Table structure for table `adrese`
--

CREATE TABLE IF NOT EXISTS `adrese` (
  `idAdresa` int(11) NOT NULL AUTO_INCREMENT,
  `adresa` varchar(255) NOT NULL DEFAULT '',
  `latitude` varchar(25) NOT NULL DEFAULT '',
  `longitude` varchar(25) NOT NULL DEFAULT '',
  PRIMARY KEY (`idAdresa`),
  UNIQUE KEY `adresa` (`adresa`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=58 ;

--
-- Dumping data for table `adrese`
--

INSERT INTO `adrese` (`idAdresa`, `adresa`, `latitude`, `longitude`) VALUES
(1, 'Karlovac, Miroslava Krleze 1c', '45.4883394', '15.5431053'),
(2, 'Zagreb, Maksimirska 2', '45.8151734', '15.9977192'),
(4, 'Karlovac, Banija 52', '45.5023054', '15.5499232'),
(47, 'Karlovac, Bascinska 15', '45.4742456', '15.5444651'),
(57, 'Karlovac, Smiciklasova 5c', '45.4876935', '15.5538606');

-- --------------------------------------------------------

--
-- Table structure for table `dnevnik`
--

CREATE TABLE IF NOT EXISTS `dnevnik` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naredba` text COLLATE utf8_unicode_ci NOT NULL,
  `vrijeme` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=16 ;

--
-- Dumping data for table `dnevnik`
--

INSERT INTO `dnevnik` (`id`, `naredba`, `vrijeme`) VALUES
(6, 'USER pero; GET "Zagreb, Maksimirska 2";', 'Sat Jun 07 09:32:56 CEST 2014'),
(5, 'USER pero; GET "Zagreb, Maksimirska 2";', 'Sat Jun 07 09:32:50 CEST 2014'),
(7, 'USER pero; GET "Zagreb, Maksimirska 2"; ADD', 'Sat Jun 07 09:33:06 CEST 2014'),
(8, 'USER admin; PASSWD 123456; TEST "Karlovac, Bascinska 10";', 'Sun Jun 08 10:23:21 CEST 2014'),
(9, 'USER admin; PASSWD 123456; ADD "Karlovac, Bascinska 10";', 'Sun Jun 08 10:23:28 CEST 2014'),
(10, 'USER admin; PASSWD 123456; PAUSE;', 'Wed Jun 11 08:20:43 CEST 2014'),
(11, 'USER admin; PASSWD 123456; TEST "Karlovac, Smiciklasova 5c";', 'Wed Jun 11 08:23:43 CEST 2014'),
(12, 'USER admin; PASSWD 123456; ADD "Karlovac, Smiciklasova 5c";', 'Wed Jun 11 08:23:43 CEST 2014'),
(13, 'USER admin; PASSWD 123456; PAUSE; KRIVA', 'Wed Jun 11 08:24:30 CEST 2014'),
(14, 'USER admin; PASSWD 123456; START;', 'Wed Jun 11 08:24:43 CEST 2014'),
(15, 'USER admin; PASSWD 123456; PAUSE;', 'Wed Jun 11 08:27:02 CEST 2014');

-- --------------------------------------------------------

--
-- Table structure for table `grupe`
--

CREATE TABLE IF NOT EXISTS `grupe` (
  `gr_ime` varchar(10) NOT NULL DEFAULT '',
  `naziv` varchar(25) NOT NULL DEFAULT '',
  PRIMARY KEY (`gr_ime`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `grupe`
--

INSERT INTO `grupe` (`gr_ime`, `naziv`) VALUES
('admin', 'Administratori'),
('manager', 'Manageri'),
('nwtis', 'NWTiS');

-- --------------------------------------------------------

--
-- Table structure for table `mknezevi1_meteo`
--

CREATE TABLE IF NOT EXISTS `mknezevi1_meteo` (
  `idMeteo` int(11) NOT NULL AUTO_INCREMENT,
  `IDADRESA` int(11) NOT NULL,
  `temperatura` float DEFAULT NULL,
  `vlaga` float DEFAULT NULL,
  `vjetar` float DEFAULT NULL,
  `tlak` float DEFAULT NULL,
  PRIMARY KEY (`idMeteo`),
  KEY `IDADRESA` (`IDADRESA`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=38 ;

--
-- Dumping data for table `mknezevi1_meteo`
--

INSERT INTO `mknezevi1_meteo` (`idMeteo`, `IDADRESA`, `temperatura`, `vlaga`, `vjetar`, `tlak`) VALUES
(1, 1, 28.3, 36, 10.8, NULL),
(2, 2, 28, 39.6, 11.1, 1012),
(3, 4, 28.3, 36, 10.8, NULL),
(4, 1, 28.3, 36, 10.8, NULL),
(5, 2, 28, 39.6, 11.1, 1012),
(6, 4, 28.3, 36, 10.8, NULL),
(23, 4, 18.9, 84, 3.6, NULL),
(22, 2, 23, 73.5, 7.4, 1019),
(21, 1, 18.9, 84, 3.6, NULL),
(26, 2, 23, 73.5, 7.4, 1019),
(25, 1, 18.9, 84, 3.6, NULL),
(24, 47, 18.9, 84, 3.6, NULL),
(27, 4, 18.9, 84, 3.6, NULL),
(28, 47, 18.9, 84, 3.6, NULL),
(29, 1, 18.9, 84, 3.6, NULL),
(30, 2, 23, 73.5, 7.4, 1019),
(31, 4, 18.9, 84, 3.6, NULL),
(33, 1, 18.9, 84, 3.6, NULL),
(34, 2, 23, 73.5, 7.4, 1019),
(35, 4, 18.9, 84, 3.6, NULL),
(36, 47, 18.9, 84, 3.6, NULL),
(37, 57, 18.9, 84, 3.6, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `polaznici`
--

CREATE TABLE IF NOT EXISTS `polaznici` (
  `kor_ime` varchar(30) NOT NULL DEFAULT '',
  `ime` varchar(25) DEFAULT '',
  `prezime` varchar(25) DEFAULT '',
  `lozinka` varchar(20) NOT NULL DEFAULT '',
  `email_adresa` varchar(40) DEFAULT '',
  `vrsta` int(11) DEFAULT '1',
  `datum_kreiranja` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `datum_promjene` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`kor_ime`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `polaznici`
--

INSERT INTO `polaznici` (`kor_ime`, `ime`, `prezime`, `lozinka`, `email_adresa`, `vrsta`, `datum_kreiranja`, `datum_promjene`) VALUES
('admin', 'Iva', 'Zec', '123456', 'admin@foi.hr', 0, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('george', 'George', 'Harrison', '123456', 'george@foi.hr', 1, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('ivo', 'Ivo', 'Zec', '123456', 'ivo@foi.hr', 1, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('janica', 'Janica', 'Kostelic', '123456', 'janica@foi.hr', 1, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('janis', 'Janis', 'Joplin', '123456', 'janis@foi.hr', 1, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('jdean', 'James', 'Dean', '123456', 'jdean@foi.hr', 1, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('jlo', 'Jennifer', 'Lopez', '123456', 'jlo@foi.hr', 1, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('jmoore', 'Joe', 'Moore', '123456', 'jmoore@foi.hr', 1, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('john', 'John', 'Lennon', '123456', 'john@foi.hr', 1, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('mato', 'Mato', 'Kos', '123456', 'mato@foi.hr', 1, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('paul', 'Paul', 'McCartney', '123456', 'paul@foi.hr', 1, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('pero', 'Pero', 'Kos', '123456', 'pero@foi.hr', 1, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('pgreen', 'Peter', 'Green', '123456', 'pgreen@foi.hr', 1, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('ringo', 'Ringo', 'Star', '123456', 'ringo@foi.hr', 1, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('tjones', 'Tom', 'Jones', '123456', 'tjones@foi.hr', 1, '2010-03-20 10:54:45', '2012-03-14 00:00:00'),
('perislav', '', '', 'sadadaad', '', 1, '2014-05-24 08:55:06', '0000-00-00 00:00:00'),
('mata', '', '', 'loza2', '', 1, '2014-05-24 08:59:55', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `polaznici_grupe`
--

CREATE TABLE IF NOT EXISTS `polaznici_grupe` (
  `kor_ime` varchar(10) NOT NULL DEFAULT '',
  `gr_ime` varchar(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`kor_ime`,`gr_ime`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `polaznici_grupe`
--

INSERT INTO `polaznici_grupe` (`kor_ime`, `gr_ime`) VALUES
('admin', 'admin'),
('mato', 'manager'),
('pero', 'admin'),
('pero', 'nwtis');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

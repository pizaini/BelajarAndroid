-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 16, 2013 at 12:37 PM
-- Server version: 5.5.16
-- PHP Version: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `latihan_android`
--
CREATE DATABASE `latihan_android` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `latihan_android`;

-- --------------------------------------------------------

--
-- Table structure for table `mahasiswa`
--

CREATE TABLE IF NOT EXISTS `mahasiswa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nim` varchar(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `telp` varchar(50) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=38 ;

--
-- Dumping data for table `mahasiswa`
--

INSERT INTO `mahasiswa` (`id`, `nim`, `nama`, `telp`, `alamat`) VALUES
(29, '2222', 'aaaa', '0801', 'Alamat 1'),
(30, '1111', 'bbbb', '0802', 'Alamat 2'),
(31, '2222', 'cccc', '0803', 'Alamat 3'),
(32, '3333', 'dddd', '0804', 'Alamat 4'),
(33, '1111', 'eeee', '0805', 'Alamat 5'),
(34, '2222', 'ffff', '0806', 'Alamat 6'),
(35, '3333', 'gggg', '0807', 'Alamat 7'),
(36, '4444', 'hhhh', '0808', 'Alamat 8'),
(37, '5555', 'iiii', '0809', 'Alamat 9');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

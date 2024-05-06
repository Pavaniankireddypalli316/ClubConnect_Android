-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 05, 2024 at 06:13 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `clubevents`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `username` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`username`, `Password`) VALUES
('M.Vamsi', 'hello123'),
('admin12', 'hello123');

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `coursename` varchar(50) NOT NULL,
  `courseid` varchar(10) NOT NULL,
  `facultyid` varchar(10) NOT NULL,
  `strength` int(10) NOT NULL,
  `coursestatus` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`coursename`, `courseid`, `facultyid`, `strength`, `coursestatus`) VALUES
('RAP SINGING', 'SIN01', 'SS111', 40, 'Completed'),
('DRUM KIT', 'SIN02', 'SS112', 40, 'Completed'),
('HIP HOP', 'DAN01', 'SS113', 40, 'ACTIVE'),
('RAP SING', 'SIN0101', 'SS111', 40, 'ACTIVE');

-- --------------------------------------------------------

--
-- Table structure for table `enrolatt`
--

CREATE TABLE `enrolatt` (
  `name` varchar(50) NOT NULL,
  `studentid` bigint(10) NOT NULL,
  `courseid` varchar(10) NOT NULL,
  `coursename` varchar(50) NOT NULL,
  `studentstatus` varchar(50) NOT NULL,
  `attendedclasses` bigint(10) NOT NULL,
  `totalclasses` bigint(10) NOT NULL,
  `coursestatus` varchar(50) NOT NULL,
  `grade` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `enrolatt`
--

INSERT INTO `enrolatt` (`name`, `studentid`, `courseid`, `coursename`, `studentstatus`, `attendedclasses`, `totalclasses`, `coursestatus`, `grade`) VALUES
('indra', 192011240, 'SIN01', 'RAP SINGING', 'Approved', 4, 4, 'Completed', 'Excellent'),
('Vamsi M', 192011429, 'DAN01', 'HIP HOP', 'Approved', 0, 0, 'ACTIVE', 'NA'),
('Vamsi M', 192011429, 'SIN01', 'RAP SINGING', 'Approved', 4, 4, 'Completed', 'Excellent'),
('Radha Krishna', 192011439, 'SIN01', 'RAP SINGING', 'Approved', 3, 4, 'Completed', 'Good'),
('Vamsi M', 192011429, 'SIN02', 'DRUM KIT', 'Approved', 1, 1, 'Completed', 'Excellent'),
('varshitha c', 192011465, 'DAN01', 'HIP HOP', 'Approved', 0, 0, 'ACTIVE', 'NA'),
('Vamsi M', 192011429, 'SIN0101', 'RAP SING', 'pending', 0, 0, 'ACTIVE', 'NA');

-- --------------------------------------------------------

--
-- Table structure for table `facultydetails`
--

CREATE TABLE `facultydetails` (
  `name` varchar(50) NOT NULL,
  `facultyid` varchar(10) NOT NULL,
  `password` varchar(50) NOT NULL,
  `contact` bigint(10) NOT NULL,
  `address` varchar(50) NOT NULL,
  `dob` date NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `facultydetails`
--

INSERT INTO `facultydetails` (`name`, `facultyid`, `password`, `contact`, `address`, `dob`, `email`) VALUES
('Leela', 'SS111', '', 7890213423, 'Anna Nagar,Chennai,Tamil Nadu', '1987-09-03', ''),
('chetan', 'SS112', 'welcome', 9890213345, 'Ponammale,Chennai,Tamil Nadu', '1986-07-13', 'chetan222@gmail.com'),
('Tharun', 'SS113', 'welcome', 8912345678, 'Tirupathi,Andhra Pradesh', '1984-06-22', 'sarapanch@gmail.com'),
('ss114', 'Reethu', 'welcome', 8219435674, 'vizag', '2019-09-11', '');

-- --------------------------------------------------------

--
-- Table structure for table `studentdetails`
--

CREATE TABLE `studentdetails` (
  `name` varchar(50) NOT NULL,
  `studentid` bigint(10) NOT NULL,
  `password` varchar(50) NOT NULL,
  `contact` bigint(10) NOT NULL,
  `address` varchar(50) NOT NULL,
  `dob` date NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `studentdetails`
--

INSERT INTO `studentdetails` (`name`, `studentid`, `password`, `contact`, `address`, `dob`, `email`) VALUES
('indra', 192011240, 'welcome', 6304324356, 'ponamalle,chennai', '2002-04-15', 'indra123@gmail.com'),
('Vamsi M', 192011429, 'welcome', 8309654542, 'Kavali,Nellore,Andhra Pradesh', '2002-07-29', 'm1231se21@gmail.com'),
('Mahesh', 192011001, 'welcome', 8989767622, 'Banglore,Karnataka', '2002-07-17', 'mahesh975@gmail.com'),
('Radha Krishna', 192011439, 'welcome', 7890144567, 'Kavali,Nellore,Andhra Pradesh', '2002-12-24', 'krishanradha12@gmail.com'),
('Pavani', 192011483, 'welcome', 9907165432, 'kadapa,Andhra Pradesh', '2002-11-08', 'hello@gmail.com'),
('varshitha c', 192011465, 'welcome', 8452678924, 'chennai', '2001-01-31', '');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

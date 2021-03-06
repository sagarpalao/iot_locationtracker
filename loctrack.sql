-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 11, 2016 at 06:42 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `loctrack`
--

-- --------------------------------------------------------

--
-- Table structure for table `busdetails`
--

CREATE TABLE IF NOT EXISTS `busdetails` (
  `bus_no` varchar(20) NOT NULL,
  `source` varchar(50) NOT NULL,
  `dest` varchar(50) NOT NULL,
  PRIMARY KEY (`bus_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `busdetails`
--

INSERT INTO `busdetails` (`bus_no`, `source`, `dest`) VALUES
('1', 'R.C.CHURCH:COLABA/RC CHURCH', 'BANDRA RECLAMATION BUS STATION:(W)'),
('2 LTD', 'COLABA DEPOT :ELECTRIC HOUSE', 'AGARKAR CHOWK :ANDHERI STATION (E)'),
('3 ROUTE 1', 'NAVY NAGAR COLABA', 'JIJAMATA UDYAN :RANI BAUG:BYCULLA (E)'),
('313 ROUTE 1', 'KURLA STATION (W)', 'SANTACRUZ STATION(E): SANTACRUZ (E)'),
('313 ROUTE 2', 'KURLA STATION (W)', 'AIR INDIA QUARTERS: KALINA'),
('318', 'KURLA STATION (W)', 'SANTACRUZ STATION(E): SANTACRUZ (E)'),
('361', 'MAHUL VILLAGE', 'KURLA BUS STATION (E)'),
('362', 'DR. AMBEDKAR GARDEN CHEMBUR STATION (E)', 'KURLA BUS STATION (E)'),
('363', 'KURLA STATION (E)', 'MAHUL VILLAGE'),
('369', 'KURLA STATION (E)', 'S.R.A.COLONY (WASHI NAKA): CHEMBUR(E)'),
('377', 'KURLA STATION (E)', 'S.R.A.COLONY(MANKHURD) : MANKHURD(S)'),
('4 LTD ROUTE 1', 'HUTATMA CHOWK: FLORA FOUNTAIN: FORT', 'GOREGAON/ OSHIWARA DEPOT:(W)');

-- --------------------------------------------------------

--
-- Table structure for table `busstops`
--

CREATE TABLE IF NOT EXISTS `busstops` (
  `bus_no` varchar(10) NOT NULL DEFAULT '',
  `sr_no` int(11) NOT NULL DEFAULT '0',
  `stop` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`bus_no`,`sr_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `busstops`
--

INSERT INTO `busstops` (`bus_no`, `sr_no`, `stop`) VALUES
('361', 1, 'MAHUL VILLAGE'),
('361', 2, 'MAHUL MARKET'),
('361', 3, 'WADALA ROAD MAHUL'),
('361', 4, 'MAZGAON DOCK (MAHUL)'),
('361', 5, 'ACHARYA VIDYANIKETAN MAHUL'),
('361', 6, 'H.P. NAGAR MAHUL'),
('361', 7, 'BPCL SPORTS CLUB MAHUL'),
('361', 8, 'SHANKAR MANDIR'),
('361', 9, 'VASI NAKA CHEMBUR(E)'),
('361', 10, 'AZIZ BAUG CHEMBUR (E)'),
('361', 11, 'MARAWALI CHURCH CHEMBUR(E)'),
('361', 12, 'CHEMBUR COLONY (E)'),
('361', 13, 'NAVJEEVAN SOCIETY CHEMBUR (E)'),
('361', 14, 'BASANT PARK CHEMBUR (E)'),
('361', 15, 'CHEMBUR NAKA (E)'),
('361', 16, 'KUMBHAR WADA CHEMBUR(E)'),
('361', 17, 'UMARSHI BAPPA CHOWK CHEMBUR (E)'),
('361', 18, 'THAKKAR BAPPA COLONY KURLA (E)'),
('361', 19, 'KAMGAR NAGAR KURLA (E)'),
('361', 20, 'NEHRU NAGAR KURLA (E)'),
('361', 21, 'KURLA BUS STATION (E)'),
('362', 1, 'DR. AMBEDKAR GARDEN CHEMBUR STATION (E)'),
('362', 2, '10TH ROAD CHEMBUR CHURCH(W)'),
('362', 3, 'ACHARYA GARDEN CHEMBUR(E)'),
('362', 4, 'R.B.I. COLONY (CHEMBUR E)'),
('362', 5, 'MAITRI PARK DEONAR'),
('362', 6, 'R.K. STUDIO DEONAR'),
('362', 7, 'PANJARA POL DEONAR'),
('362', 8, 'BASANT TALKIES CHEMBUR (W)'),
('362', 9, 'WADAVALI VILLAGE CHEMBUR (E)'),
('362', 10, 'R.C.F. COLONY CHEMBUR (E)'),
('362', 11, 'GANDHI MARKET CHEMBUR (W)'),
('362', 12, 'CHEMBUR COLONY (E)'),
('362', 13, 'NAVJEEVAN SOCIETY CHEMBUR (E)'),
('362', 14, 'BASANT PARK CHEMBUR (E)'),
('362', 15, 'BHAKTI BAVAN CHEMBUR (E)'),
('362', 16, 'UMARSHI BAPPA CHOWK CHEMBUR(E)'),
('362', 17, 'THAKKAR BAPPA CHOWK KURLA(E)'),
('362', 18, 'KAMGAR NAGAR KURLA (E)'),
('362', 19, 'NEHRU NAGAR KURLA (E)'),
('362', 20, 'KURLA BUS STATION (E)'),
('363', 1, 'KURLA BUS STATION(E)'),
('363', 2, 'NEHRU NAGAR'),
('363', 3, 'KAMGAR NAGAR KURLA(E)'),
('363', 4, 'THAKKAR BAPPA COLONY KURLA(E0'),
('363', 5, 'UMARSHI BAPPA CHOWK CHEMNUR(E)'),
('363', 6, 'BHAKTI BHAVAN CHEMBUR (W)'),
('363', 7, 'BASANT PARK CHEMBUR(E)'),
('363', 8, 'NAVJEEVAN SOCIETY CHEMBUR(E)'),
('363', 9, 'CHEMBUR COLONY (E)'),
('363', 10, 'MARAWALI CHURCH CHEMBUR(E)'),
('363', 11, 'R.C.F HOSTEL (CHEMBUR(E))'),
('363', 12, 'R.C.F.OFFICE (CHEMBUR)'),
('363', 13, 'R.C.F.(CHEMBUR)'),
('363', 14, 'KAMAL TOWING COMPANY VASI NAKA'),
('363', 15, 'JIJAMATA NAGAR MAHUL'),
('363', 16, 'ACHARYA VIDYANIKETAN MAHUL'),
('363', 17, 'MAZGAON DOCK (MAHUL)'),
('363', 18, 'WADALA ROAD MAHUL'),
('363', 19, 'MAHUL MARKET'),
('363', 20, 'MAHUL VILLAGE'),
('369', 1, 'KURLA STATION (E)'),
('369', 2, 'NEHRU NAGAR KURLA (E)'),
('369', 3, 'KAMGAR NAGAR KURLA (E)'),
('369', 4, 'THAKKAR BAPPA COLONY KURLA(E)'),
('369', 5, 'UMARSHI BAPPA CHOWK SWASTIK CHAMBERS CHEMBUR (E)'),
('369', 6, 'BHAKTI BHAVAN CHEMBUR (E)'),
('369', 7, 'BASANT PARK CHEMBUR (E)'),
('369', 8, 'NAVJEEVAN SOCIETY CHEMBUR (E)'),
('369', 9, 'CHEMBUR COLONY CHEMBUR (E)'),
('369', 10, 'MARAWALI CHURCH CHEMBUR (E)'),
('369', 11, 'AZIZ BAUG CHEMBUR (E)'),
('369', 12, 'VASI NAKA CHEMBUR (E)'),
('369', 13, 'VINAY HIGH SCHOOL CHEMBUR (E)'),
('369', 14, 'S.R.A.COLONY (WASHI NAKA) CHEMBUR (E)/SRA COLONY'),
('377', 1, 'KURLA BUS STATION (E)'),
('377', 2, 'NEHRU NAGAR KURLA (E)'),
('377', 3, 'KAMGAR NAGAR KURLA(E)'),
('377', 4, 'THAKKAR BAPPA COLONY KURLA (E)'),
('377', 5, 'S.G. BARVE MARG JUNCTION / KURLA SIGNAL (E)'),
('377', 6, 'POSTAL COLONY CHEMBUR(W)'),
('377', 7, 'AMAR MAHAL CHEMBUR (W)'),
('377', 8, 'SHIVSENA OFFICE CHEMBUR (E)'),
('377', 9, 'NARAYA GURU H''SCHOOL CHEMBUR (E)'),
('377', 10, 'ASHISH NAGAR '),
('377', 11, 'NEW GAUTAM NAGAR DEONAR'),
('377', 12, 'DEONAR POLICE STATION'),
('377', 13, 'DEONAR FIRE BRIGADE COLONY'),
('377', 14, 'DEONAR MUNICIPAL COLONY'),
('377', 15, 'LALLUBHAI COMPOUND POLICE CHOWKEY MANKHURD (S)'),
('377', 16, 'S.R.A. COLONY (MANKHURD)');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `busstops`
--
ALTER TABLE `busstops`
  ADD CONSTRAINT `busstops_ibfk_1` FOREIGN KEY (`bus_no`) REFERENCES `busdetails` (`bus_no`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

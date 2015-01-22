-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: meteocaldb
-- ------------------------------------------------------
-- Server version	5.6.22-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `calendars`
--

DROP TABLE IF EXISTS `calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calendars` (
  `id_calendar` bigint(20) NOT NULL,
  `calendar_privacy` bigint(20) DEFAULT NULL,
  `owner_calendar` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_calendar`),
  KEY `FK_CALENDARS_calendar_privacy` (`calendar_privacy`),
  KEY `FK_CALENDARS_owner_calendar` (`owner_calendar`),
  CONSTRAINT `FK_CALENDARS_calendar_privacy` FOREIGN KEY (`calendar_privacy`) REFERENCES `privacy_types` (`id_privacy_type`),
  CONSTRAINT `FK_CALENDARS_owner_calendar` FOREIGN KEY (`owner_calendar`) REFERENCES `users` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendars`
--

LOCK TABLES `calendars` WRITE;
/*!40000 ALTER TABLE `calendars` DISABLE KEYS */;
INSERT INTO `calendars` VALUES (110000001,190000000,200000001),(110000002,190000000,200000002),(110000003,190000000,200000003),(110000004,190000000,200000004),(110000005,190000000,200000005),(110000006,190000000,200000006),(110000007,190000000,200000007),(110000050,190000000,200000050),(110000100,190000000,200000100),(110000200,190000000,200000200),(110000250,190000000,200000250);
/*!40000 ALTER TABLE `calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_statuses`
--

DROP TABLE IF EXISTS `event_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_statuses` (
  `id_event_status` bigint(20) NOT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_event_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_statuses`
--

LOCK TABLES `event_statuses` WRITE;
/*!40000 ALTER TABLE `event_statuses` DISABLE KEYS */;
INSERT INTO `event_statuses` VALUES (150000000,0),(150000001,1),(150000002,2);
/*!40000 ALTER TABLE `event_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_types`
--

DROP TABLE IF EXISTS `event_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_types` (
  `id_event_type` bigint(20) NOT NULL,
  `type` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id_event_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_types`
--

LOCK TABLES `event_types` WRITE;
/*!40000 ALTER TABLE `event_types` DISABLE KEYS */;
INSERT INTO `event_types` VALUES (160000000,0),(160000001,1);
/*!40000 ALTER TABLE `event_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events` (
  `id_event` bigint(20) NOT NULL,
  `begin_hour` time DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `date_created` date DEFAULT NULL,
  `date_modified` date DEFAULT NULL,
  `dateOfEvent` date DEFAULT NULL,
  `date_rescheduled` date DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `modified` tinyint(1) DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `street_and_number` varchar(255) DEFAULT NULL,
  `event_privacy` bigint(20) DEFAULT NULL,
  `event_type` bigint(20) DEFAULT NULL,
  `event_calendar` bigint(20) DEFAULT NULL,
  `notify_owner` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id_event`),
  KEY `FK_EVENTS_event_calendar` (`event_calendar`),
  KEY `FK_EVENTS_event_privacy` (`event_privacy`),
  KEY `FK_EVENTS_event_type` (`event_type`),
  CONSTRAINT `FK_EVENTS_event_calendar` FOREIGN KEY (`event_calendar`) REFERENCES `calendars` (`id_calendar`),
  CONSTRAINT `FK_EVENTS_event_privacy` FOREIGN KEY (`event_privacy`) REFERENCES `privacy_types` (`id_privacy_type`),
  CONSTRAINT `FK_EVENTS_event_type` FOREIGN KEY (`event_type`) REFERENCES `event_types` (`id_event_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (130000150,'12:00:00','Enter City Name','2015-01-18',NULL,'2020-12-12',NULL,1,NULL,'Enter Event Name','Enter Address',190000000,160000000,110000250,1),(130000200,'22:45:00','Milan','2015-01-18',NULL,'2015-02-02',NULL,45,NULL,'test1','test1',190000000,160000000,110000250,1),(130000201,'12:54:00','Enter City Name','2015-01-18',NULL,'2015-03-11',NULL,120,NULL,'test2','test2',190000000,160000000,110000250,0),(130000250,'01:18:10','Enter City Name','2015-01-19',NULL,'2015-01-19',NULL,1,NULL,'Enter Event Name','Enter Address',190000000,160000000,110000001,1),(130000300,'01:37:25','Enter City Name','2015-01-19',NULL,'2015-01-19',NULL,1,NULL,'Enter Event Name','Enter Address',190000000,160000000,110000002,0),(130000350,'13:14:42','Enter City Name','2015-01-19',NULL,'2015-01-19',NULL,1,NULL,'Enter Event Name','Enter Address',190000000,160000000,110000250,0),(130000400,'13:45:25','Enter City Name','2015-01-19',NULL,'2015-01-19',NULL,1,NULL,'test','Enter Address',190000000,160000000,110000250,0),(130000450,'13:50:26','Enter City Name','2015-01-19',NULL,'2015-01-19',NULL,1,NULL,'test name','Enter Address',190000000,160000000,110000250,0),(130000500,'14:05:13','Test City 2','2015-01-19',NULL,'2015-01-19',NULL,1,NULL,'Test Name 2','Test Address 2',190000000,160000001,110000250,0),(130000550,'06:38:15','kjhdajkh','2015-01-19',NULL,'2015-01-22',NULL,35,NULL,'jhdajkhd','kjdhakjhd',190000000,160000001,110000250,0),(130000600,'16:21:39','Enter City Name','2015-01-19',NULL,'2015-01-19',NULL,1,NULL,'Enter Event Name','Enter Address',190000000,160000000,110000250,0),(130000650,'03:17:37','Enter City Name','2015-01-19',NULL,'2015-01-30',NULL,35,NULL,'test test test','Enter Address',190000001,160000001,110000250,0),(130000700,'18:54:57','Milan','2015-01-19',NULL,'2015-01-19',NULL,46,NULL,'Event One','Via Vallazze 101',190000001,160000001,110000250,0),(130000701,'20:56:35','Milan','2015-01-19',NULL,'2015-01-19',NULL,25,NULL,'Event Two','Via Vallazze 101',190000000,160000000,110000250,0),(130000702,'20:57:43','Milan','2015-01-19',NULL,'2015-01-19',NULL,46,NULL,'Event Three','Via Vallazze 101',190000000,160000000,110000250,0),(130000703,'07:59:32','Milan','2015-01-19',NULL,'2015-01-20',NULL,65,NULL,'Event Four','Via Vallazze 101',190000001,160000001,110000250,0),(130000704,'15:00:43','Milan','2015-01-19',NULL,'2015-01-20',NULL,20,NULL,'Event 5','Via Vallazze 101',190000000,160000000,110000250,0),(130000705,'18:01:12','Milan','2015-01-19',NULL,'2015-01-20',NULL,100,NULL,'Event 6','Via Vallazze 101',190000001,160000000,110000250,0);
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `id_gen`
--

DROP TABLE IF EXISTS `id_gen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `id_gen` (
  `ID_NAME` varchar(50) NOT NULL,
  `ID_VAL` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`ID_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `id_gen`
--

LOCK TABLES `id_gen` WRITE;
/*!40000 ALTER TABLE `id_gen` DISABLE KEYS */;
INSERT INTO `id_gen` VALUES ('CAL_GEN',110000299),('ES_GEN',150000049),('ET_GEN',160000049),('EVT_GEN',130000749),('INV_GEN',170000499),('NOTF_GEN',230000099),('PT_GEN',190000049),('USR_GEN',200000299),('WD_GEN',220000049);
/*!40000 ALTER TABLE `id_gen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invitations`
--

DROP TABLE IF EXISTS `invitations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invitations` (
  `id_invitation` bigint(20) NOT NULL,
  `owner_event` bigint(20) DEFAULT NULL,
  `event_status` bigint(20) DEFAULT NULL,
  `owner_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_invitation`),
  KEY `FK_INVITATIONS_event_status` (`event_status`),
  KEY `FK_INVITATIONS_owner_user` (`owner_user`),
  KEY `FK_INVITATIONS_owner_event` (`owner_event`),
  CONSTRAINT `FK_INVITATIONS_event_status` FOREIGN KEY (`event_status`) REFERENCES `event_statuses` (`id_event_status`),
  CONSTRAINT `FK_INVITATIONS_owner_event` FOREIGN KEY (`owner_event`) REFERENCES `events` (`id_event`),
  CONSTRAINT `FK_INVITATIONS_owner_user` FOREIGN KEY (`owner_user`) REFERENCES `users` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invitations`
--

LOCK TABLES `invitations` WRITE;
/*!40000 ALTER TABLE `invitations` DISABLE KEYS */;
INSERT INTO `invitations` VALUES (170000059,130000150,150000000,200000001),(170000060,130000150,150000000,200000003),(170000061,130000150,150000000,200000200),(170000100,130000201,150000000,200000100),(170000101,130000201,150000000,200000200),(170000102,130000201,150000000,200000005),(170000103,130000201,150000000,200000003),(170000155,130000250,150000000,200000003),(170000156,130000250,150000000,200000100),(170000157,130000250,150000000,200000200),(170000158,130000250,150000000,200000250),(170000200,130000300,150000000,200000004),(170000201,130000300,150000000,200000005),(170000202,130000300,150000000,200000006),(170000250,130000350,150000000,200000001),(170000251,130000350,150000000,200000002),(170000303,130000500,150000000,200000001),(170000304,130000500,150000000,200000003),(170000305,130000500,150000000,200000004),(170000306,130000500,150000000,200000006),(170000350,130000550,150000000,200000005),(170000351,130000550,150000000,200000006),(170000400,130000650,150000000,200000004),(170000401,130000650,150000000,200000005),(170000402,130000650,150000000,200000006),(170000450,130000700,150000000,200000001),(170000451,130000700,150000000,200000002),(170000452,130000700,150000000,200000003),(170000453,130000701,150000000,200000001),(170000454,130000701,150000000,200000002),(170000455,130000701,150000000,200000003),(170000456,130000702,150000000,200000001),(170000457,130000702,150000000,200000002),(170000458,130000702,150000000,200000003),(170000459,130000703,150000000,200000001),(170000460,130000703,150000000,200000002),(170000461,130000703,150000000,200000003),(170000462,130000704,150000000,200000001),(170000463,130000704,150000000,200000002),(170000464,130000704,150000000,200000003),(170000465,130000705,150000000,200000001),(170000466,130000705,150000000,200000002),(170000467,130000705,150000000,200000003);
/*!40000 ALTER TABLE `invitations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `id_notification` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `owner` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_notification`),
  KEY `FK_NOTIFICATION_owner` (`owner`),
  CONSTRAINT `FK_NOTIFICATION_owner` FOREIGN KEY (`owner`) REFERENCES `users` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notifications` (
  `id_notification` bigint(20) NOT NULL,
  `description` varchar(45) NOT NULL,
  `owner` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_notification`),
  KEY `FK_NOTIFICATIONS_owner_user_idx` (`owner`),
  CONSTRAINT `FK_NOTIFICATIONS_owner_user` FOREIGN KEY (`owner`) REFERENCES `users` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (230000000,'Event Rescheduled',200000001),(230000050,'Event Canceled',200000250),(230000100,'Event Canceled',200000250);
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privacy_types`
--

DROP TABLE IF EXISTS `privacy_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `privacy_types` (
  `id_privacy_type` bigint(20) NOT NULL,
  `privacy` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id_privacy_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privacy_types`
--

LOCK TABLES `privacy_types` WRITE;
/*!40000 ALTER TABLE `privacy_types` DISABLE KEYS */;
INSERT INTO `privacy_types` VALUES (190000000,0),(190000001,1);
/*!40000 ALTER TABLE `privacy_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id_user` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (200000001,'u1@mail.com','User1','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Test','username1'),(200000002,'u2@mail.com','User2','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Test','username2'),(200000003,'u3@mail.com','User3','e3c33e889e0e1b62cb7f65c63b60c42bd77275d0e730432fc37b7e624b09ad1f','Test','username3'),(200000004,'u4@mail.com','User4','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Test','username4'),(200000005,'u5@mail.com','User5','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Test','username5'),(200000006,'u6@mail.com','User6','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Test','username6'),(200000007,'u7@mail.com','User7','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Test','username7'),(200000050,'u8@mail.com','User8','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','Test','username8'),(200000100,'u10@mail.com','piter','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','piterson','username10'),(200000200,'mail@email.com','ime','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','prezime','username12'),(200000250,'milos.colic@elfak.rs','Milos','dbc936cafad99793ba46d632be4b0261d7418d12803c6654a360cf60a3ed9b8','Colic','chola1990');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weather_data`
--

DROP TABLE IF EXISTS `weather_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weather_data` (
  `id_weather_data` bigint(20) NOT NULL,
  `cloud_percentage` double DEFAULT NULL,
  `date` date DEFAULT NULL,
  `hour` time DEFAULT NULL,
  `preasure` double DEFAULT NULL,
  `temperature` double DEFAULT NULL,
  `wind_speed` double DEFAULT NULL,
  `city` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `icon` varchar(45) DEFAULT NULL,
  `code` mediumint(10) DEFAULT '800',
  PRIMARY KEY (`id_weather_data`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weather_data`
--

LOCK TABLES `weather_data` WRITE;
/*!40000 ALTER TABLE `weather_data` DISABLE KEYS */;
INSERT INTO `weather_data` VALUES (220000000,12,'2015-01-20','01:00:00',952.1,-4.5,1.32,'Madrid','few clouds','02n',801),(220000001,48,'2015-01-20','04:00:00',951.06,-4.64,1.75,'Madrid','scattered clouds','03n',802),(220000002,88,'2015-01-20','07:00:00',950.2,-3.01,1.33,'Madrid','overcast clouds','04n',804),(220000003,76,'2015-01-20','10:00:00',949.64,-0.31,1.32,'Madrid','light snow','13d',600),(220000004,64,'2015-01-20','13:00:00',949.17,4.26,2.76,'Madrid','broken clouds','04d',803),(220000005,56,'2015-01-20','16:00:00',947.45,6.17,3.43,'Madrid','broken clouds','04d',803),(220000006,92,'2015-01-20','19:00:00',948.02,3.89,1.61,'Madrid','light rain','10n',500),(220000007,20,'2015-01-20','22:00:00',949.15,-0.14,1.77,'Madrid','few clouds','02n',801),(220000008,20,'2015-01-21','01:00:00',949.89,-3.27,1.66,'Madrid','few clouds','02n',801),(220000009,0,'2015-01-21','04:00:00',950.68,-5.2,1.66,'Madrid','sky is clear','01n',800),(220000010,0,'2015-01-21','07:00:00',951.49,-6.27,1.62,'Madrid','sky is clear','01n',800),(220000011,0,'2015-01-21','10:00:00',953,-3.36,1.55,'Madrid','sky is clear','01d',800),(220000012,0,'2015-01-21','13:00:00',953.72,6.88,3.58,'Madrid','sky is clear','01d',800),(220000013,24,'2015-01-21','16:00:00',951.88,8.75,6.11,'Madrid','few clouds','02d',801),(220000014,80,'2015-01-21','19:00:00',952.22,6.73,6.44,'Madrid','broken clouds','04n',803),(220000015,0,'2015-01-21','22:00:00',952.75,4.74,5.56,'Madrid','sky is clear','01n',800),(220000016,0,'2015-01-22','01:00:00',952.62,3.49,5.36,'Madrid','sky is clear','01n',800),(220000017,0,'2015-01-22','04:00:00',952.26,3.43,5.44,'Madrid','sky is clear','01n',800),(220000018,12,'2015-01-22','07:00:00',951.96,4.34,5.96,'Madrid','few clouds','02n',801),(220000019,24,'2015-01-22','10:00:00',952.69,6.49,6.3,'Madrid','few clouds','02d',801),(220000020,88,'2015-01-22','13:00:00',951.88,10.34,8.11,'Madrid','overcast clouds','04d',804),(220000021,88,'2015-01-22','16:00:00',950.69,10.72,8.41,'Madrid','overcast clouds','04d',804),(220000022,88,'2015-01-22','19:00:00',951.43,10.35,8.38,'Madrid','overcast clouds','04n',804),(220000023,68,'2015-01-22','22:00:00',953.17,10.3,8.92,'Madrid','broken clouds','04n',803),(220000024,0,'2015-01-23','01:00:00',955.05,8.56,8.52,'Madrid','sky is clear','01n',800),(220000025,0,'2015-01-23','04:00:00',956.35,6.84,7.26,'Madrid','sky is clear','01n',800),(220000026,0,'2015-01-23','07:00:00',957.14,5.9,6.4,'Madrid','sky is clear','01n',800),(220000027,0,'2015-01-23','10:00:00',959.05,7.07,6.01,'Madrid','sky is clear','01d',800),(220000028,32,'2015-01-23','13:00:00',960.42,10.47,7.21,'Madrid','scattered clouds','03d',802),(220000029,0,'2015-01-23','16:00:00',960.76,10.73,6.66,'Madrid','sky is clear','01d',800),(220000030,0,'2015-01-23','19:00:00',962.18,7.1,4.51,'Madrid','sky is clear','01n',800),(220000031,12,'2015-01-23','22:00:00',961.18,-1.39,1.72,'Madrid','moderate rain','10n',501),(220000032,0,'2015-01-24','01:00:00',964.15,-0.56,1.66,'Madrid','sky is clear','01n',800),(220000033,0,'2015-01-24','04:00:00',964.37,-2.68,0.71,'Madrid','sky is clear','01n',800),(220000034,0,'2015-01-24','07:00:00',964.53,-3.87,1.16,'Madrid','sky is clear','01n',800),(220000035,0,'2015-01-24','10:00:00',965.58,-0.42,1.02,'Madrid','sky is clear','01d',800),(220000036,0,'2015-01-24','13:00:00',965.75,9.2,1.06,'Madrid','sky is clear','01d',800),(220000037,0,'2015-01-24','16:00:00',964.26,11.93,2,'Madrid','sky is clear','01d',800),(220000038,0,'2015-01-24','19:00:00',964.32,6.5,1.26,'Madrid','sky is clear','01n',800),(220000039,0,'2015-01-24','22:00:00',965.21,1.45,1.07,'Madrid','sky is clear','01n',800),(220000040,0,'2015-01-25','01:00:00',964.96,-0.85,1.23,'Madrid','sky is clear','01n',800);
/*!40000 ALTER TABLE `weather_data` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-01-21 22:06:36

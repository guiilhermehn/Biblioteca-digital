-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: biblioteca_db
-- ------------------------------------------------------
-- Server version	5.7.7-rc-log

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
-- Table structure for table `unidade_livro`
--

DROP TABLE IF EXISTS `unidade_livro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidade_livro` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avarias` varchar(255) DEFAULT NULL,
  `emprestado` bit(1) NOT NULL,
  `reservado` bit(1) NOT NULL,
  `livro_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkxktdt5a1vwu9guxdxrt3nlhn` (`livro_id`)
) ENGINE=MyISAM AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidade_livro`
--

LOCK TABLES `unidade_livro` WRITE;
/*!40000 ALTER TABLE `unidade_livro` DISABLE KEYS */;
INSERT INTO `unidade_livro` VALUES (1,NULL,'\0','\0',1),(2,NULL,'\0','\0',2),(3,NULL,'\0','\0',3),(4,NULL,'\0','\0',4),(5,NULL,'\0','\0',5),(6,NULL,'\0','\0',6),(7,NULL,'\0','\0',7),(8,NULL,'\0','\0',8),(9,NULL,'\0','\0',9),(10,NULL,'\0','\0',10),(11,NULL,'\0','\0',11),(12,NULL,'\0','\0',12),(13,NULL,'\0','\0',13),(14,NULL,'\0','\0',14),(15,NULL,'\0','\0',15),(16,NULL,'\0','\0',16),(17,NULL,'\0','\0',17),(18,NULL,'\0','\0',18),(19,NULL,'\0','\0',19),(20,NULL,'\0','\0',20),(21,NULL,'\0','\0',21),(22,NULL,'\0','\0',22),(23,NULL,'\0','\0',23),(24,NULL,'\0','\0',24),(25,NULL,'\0','\0',25),(26,NULL,'\0','\0',26),(27,NULL,'\0','\0',27),(28,NULL,'\0','\0',28),(29,NULL,'\0','\0',29),(30,NULL,'\0','\0',30),(31,NULL,'\0','\0',31),(32,NULL,'\0','\0',32),(33,NULL,'\0','\0',33),(34,NULL,'\0','\0',34),(35,NULL,'\0','\0',35),(36,NULL,'\0','\0',36),(37,NULL,'\0','\0',37),(38,NULL,'\0','\0',38),(39,NULL,'\0','\0',39),(40,NULL,'\0','\0',40),(41,NULL,'\0','\0',41),(42,NULL,'\0','\0',42),(43,NULL,'\0','\0',43),(44,NULL,'\0','\0',44),(45,NULL,'\0','\0',45),(46,NULL,'\0','\0',46),(47,NULL,'\0','\0',47),(48,NULL,'\0','\0',48),(49,NULL,'\0','\0',49),(50,NULL,'\0','\0',50),(51,NULL,'\0','\0',51),(52,NULL,'\0','\0',52),(53,NULL,'\0','\0',53),(54,NULL,'\0','\0',54),(55,NULL,'\0','\0',55),(56,NULL,'\0','\0',56),(57,NULL,'\0','\0',57),(58,NULL,'\0','\0',58),(59,NULL,'\0','\0',59),(60,NULL,'\0','\0',60),(61,NULL,'\0','\0',61),(62,NULL,'\0','\0',62),(63,NULL,'\0','\0',63),(64,NULL,'\0','\0',64),(65,NULL,'\0','\0',65),(66,NULL,'\0','\0',66),(67,NULL,'\0','\0',67),(68,NULL,'\0','\0',68),(69,NULL,'\0','\0',69),(70,NULL,'\0','\0',70),(71,NULL,'\0','\0',71),(72,NULL,'\0','\0',72);
/*!40000 ALTER TABLE `unidade_livro` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-23 11:40:13

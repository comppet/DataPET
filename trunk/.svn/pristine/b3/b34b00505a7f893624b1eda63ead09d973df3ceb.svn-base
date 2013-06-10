-- MySQL dump 10.13  Distrib 5.1.39, for Win64 (unknown)
--
-- Host: localhost    Database: datapet
-- ------------------------------------------------------
-- Server version	5.1.39-community

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
-- Table structure for table `atividade`
--

DROP TABLE IF EXISTS `atividade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atividade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(80) NOT NULL,
  `parceiros` tinytext,
  `descricao` tinytext NOT NULL,
  `justificativa` tinytext,
  `datainicio` date NOT NULL,
  `datafim` date DEFAULT NULL,
  `comentario` tinytext,
  `resultadosesperados` tinytext,
  `resultadosalcancados` tinytext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atividade`
--

LOCK TABLES `atividade` WRITE;
/*!40000 ALTER TABLE `atividade` DISABLE KEYS */;
/*!40000 ALTER TABLE `atividade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atividadepublica`
--

DROP TABLE IF EXISTS `atividadepublica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `atividadepublica` (
  `id` int(11) NOT NULL,
  `publicoalvo` tinytext,
  `id_natureza` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKEY2_ATVPUB` (`id_natureza`),
  CONSTRAINT `FKEY2_ATVPUB` FOREIGN KEY (`id_natureza`) REFERENCES `natureza` (`id`) ON DELETE SET NULL,
  CONSTRAINT `FKEY_ATVPUB` FOREIGN KEY (`id`) REFERENCES `atividade` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atividadepublica`
--

LOCK TABLES `atividadepublica` WRITE;
/*!40000 ALTER TABLE `atividadepublica` DISABLE KEYS */;
/*!40000 ALTER TABLE `atividadepublica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ensino`
--

DROP TABLE IF EXISTS `ensino`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ensino` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `ensino_ibfk_1` FOREIGN KEY (`id`) REFERENCES `atividadepublica` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ensino`
--

LOCK TABLES `ensino` WRITE;
/*!40000 ALTER TABLE `ensino` DISABLE KEYS */;
/*!40000 ALTER TABLE `ensino` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `extensao`
--

DROP TABLE IF EXISTS `extensao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `extensao` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `extensao_ibfk_1` FOREIGN KEY (`id`) REFERENCES `atividadepublica` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `extensao`
--

LOCK TABLES `extensao` WRITE;
/*!40000 ALTER TABLE `extensao` DISABLE KEYS */;
/*!40000 ALTER TABLE `extensao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `natureza`
--

DROP TABLE IF EXISTS `natureza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `natureza` (
  `id` int(11) NOT NULL DEFAULT '0',
  `nome` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `natureza`
--

LOCK TABLES `natureza` WRITE;
/*!40000 ALTER TABLE `natureza` DISABLE KEYS */;
/*!40000 ALTER TABLE `natureza` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pesquisa`
--

DROP TABLE IF EXISTS `pesquisa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pesquisa` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `pesquisa_ibfk_1` FOREIGN KEY (`id`) REFERENCES `atividade` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pesquisa`
--

LOCK TABLES `pesquisa` WRITE;
/*!40000 ALTER TABLE `pesquisa` DISABLE KEYS */;
/*!40000 ALTER TABLE `pesquisa` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-08-10 20:59:50

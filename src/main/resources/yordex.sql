DROP TABLE IF EXISTS `tasks`;

CREATE TABLE `tasks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` datetime NOT NULL,
  `description` varchar(1000) NOT NULL DEFAULT '',
  `due_date` date NOT NULL,
  `modified_on` datetime NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `repeat_count` int(2) NOT NULL DEFAULT '0',
  `repeat_freq` int(2) NOT NULL DEFAULT '0',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6s1ob9k4ihi75xbxe2w0ylsdh` (`user_id`),
  CONSTRAINT `FK6s1ob9k4ihi75xbxe2w0ylsdh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;

INSERT INTO `tasks` (`id`, `created_on`, `description`, `due_date`, `modified_on`, `name`, `repeat_count`, `repeat_freq`, `user_id`)
VALUES
	(1,'2017-05-21 09:41:42','Desc1','2017-05-21','2017-05-21 09:41:42','name1',0,0,1),
	(2,'2017-05-21 09:41:55','Desc2','2017-05-21','2017-05-21 09:41:55','name2',1,1,1),
	(3,'2017-05-21 09:42:04','Desc3','2017-05-21','2017-05-21 09:42:04','name3',0,0,1),
	(4,'2017-05-21 09:42:08','Desc4','2017-05-21','2017-05-21 09:42:08','name4',0,0,1),
	(5,'2017-05-21 09:42:16','Desc5','2017-05-21','2017-05-21 09:42:16','name5',3,3,1),
	(6,'2017-05-21 09:42:24','Desc6','2017-05-21','2017-05-21 09:42:24','name6',1,4,1);

/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` datetime NOT NULL,
  `account_non_expired` char(1) NOT NULL,
  `account_non_locked` char(1) NOT NULL,
  `credentials_non_expired` char(1) NOT NULL,
  `is_enabled` char(1) NOT NULL,
  `password` varchar(100) NOT NULL DEFAULT '',
  `role` varchar(255) NOT NULL,
  `username` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;

INSERT INTO `users` (`id`, `created_on`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `is_enabled`, `password`, `role`, `username`)
VALUES
	(1,'2017-05-06 18:06:06','Y','Y','Y','Y','$2a$10$2yW2FHS5yiqfa0kCok/HY.Y54cXGvH.2yfoM.OHrS2SZDQcpm9Py.','USER','ankush');

/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
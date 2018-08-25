SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

USE `ted` ;
DROP TABLE IF EXISTS `ted`.`user` ;
DROP TABLE IF EXISTS `ted`.`connection` ;
DROP TABLE IF EXISTS `ted`.`post` ;
DROP TABLE IF EXISTS `ted`.`conversation` ;
DROP TABLE IF EXISTS `ted`.`comment` ;
DROP TABLE IF EXISTS `ted`.`message` ;

CREATE SCHEMA IF NOT EXISTS `ted` DEFAULT CHARACTER SET utf8 ;
USE `ted` ;

CREATE TABLE IF NOT EXISTS `ted`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `isAdmin` INT(11) NOT NULL,
  `email` VARCHAR(60) NOT NULL,
  `password` VARCHAR(70) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(50) NOT NULL,
  `tel` VARCHAR(15) NULL DEFAULT NULL,
  `photoURL` VARCHAR(2083) NULL DEFAULT NULL,
  `dateOfBirth` DATE NULL DEFAULT NULL,
  `gender` INT(10) UNSIGNED NULL DEFAULT NULL,
  `city` VARCHAR(100) NULL DEFAULT NULL,
  `country` VARCHAR(100) NULL DEFAULT NULL,
  `hasImage` TINYINT(4) NOT NULL,
  `isConnected` TINYINT(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `iduser_UNIQUE` (`id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `ted`.`connection` (
  `user_id` INT(11) NOT NULL,
  `connectedUser_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `connectedUser_id`),
  INDEX `fk_user_has_user_user2_idx` (`connectedUser_id` ASC),
  INDEX `fk_user_has_user_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ted`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_user_user2`
    FOREIGN KEY (`connectedUser_id`)
    REFERENCES `ted`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `ted`.`post` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(5000) NULL DEFAULT NULL,
  `date_posted` DATETIME NOT NULL,
  `path_files` VARCHAR(1000) NULL DEFAULT NULL,
  `hasAudio` TINYINT(4) NOT NULL,
  `hasImages` TINYINT(4) NOT NULL,
  `hasVideos` TINYINT(4) NOT NULL,
  `likes` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_post_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_post_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ted`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `ted`.`conversation` (
  `user_id1` INT(11) NOT NULL,
  `user_id2` INT(11) NOT NULL,
  PRIMARY KEY (`user_id1`, `user_id2`),
  INDEX `fk_user_has_user_user4_idx` (`user_id2` ASC),
  INDEX `fk_user_has_user_user3_idx` (`user_id1` ASC),
  CONSTRAINT `fk_user_has_user_user3`
    FOREIGN KEY (`user_id1`)
    REFERENCES `ted`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_user_user4`
    FOREIGN KEY (`user_id2`)
    REFERENCES `ted`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `ted`.`comment` (
  `comment_id` INT NOT NULL,
  `date_posted` DATETIME NOT NULL,
  `text` VARCHAR(2000) NOT NULL,
  `post_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`comment_id`),
  INDEX `fk_comment_post1_idx` (`post_id` ASC),
  INDEX `fk_comment_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_comment_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `ted`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ted`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
 
CREATE TABLE IF NOT EXISTS `ted`.`message` (
  `message_id` INT NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(2000) NOT NULL,
  `date` DATETIME NOT NULL,
  `from_user` INT(11) NOT NULL,
  `to_user` INT(11) NOT NULL,
  PRIMARY KEY (`message_id`),
  INDEX `fk_message_conversation_idx` (`from_user` ASC, `to_user` ASC),
  CONSTRAINT `fk_message_conversation`
    FOREIGN KEY (`from_user` , `to_user`)
    REFERENCES `ted`.`conversation` (`user_id1` , `user_id2`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO User (id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage) values (1,1,'admin@hotmail.com','HS4mBcBiOYLNcn2997bKaw==','Admin','Surname','2101234567','/LinkedIn/images/default-user.png','1996-02-21',1,'Piraeus','Greece',0);

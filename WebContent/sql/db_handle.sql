SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

/*schema ted*/

CREATE SCHEMA IF NOT EXISTS `ted` DEFAULT CHARACTER SET utf8 ;
USE `ted` ;

/*table ted.user*/

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
  `prof_exp` VARCHAR(3000) NULL,
  `education` VARCHAR(3000) NULL,
  `skills` VARCHAR(3000) NULL,
  `privateEmail` TINYINT(4) NOT NULL,
  `privateDateOfBirth` TINYINT(4) NOT NULL,
  `privateTelephone` TINYINT(4) NOT NULL,
  `privateGender` TINYINT(4) NOT NULL,
  `privateCountry` TINYINT(4) NOT NULL,
  `privateCity` TINYINT(4) NOT NULL,
  `privateProfExp` TINYINT(4) NOT NULL,
  `privateEducation` TINYINT(4) NOT NULL,
  `privateSkills` TINYINT(4) NOT NULL,
  `workPos` VARCHAR(3000) NULL,
  `institution` VARCHAR(3000) NULL,
  `privateWorkPos` TINYINT(4) NOT NULL,
  `privateInstitution` TINYINT(4) NOT NULL,
  `isPending` TINYINT(4) NOT NULL DEFAULT 0,
  `sentConnectionRequest` TINYINT(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `iduser_UNIQUE` (`id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
DEFAULT CHARACTER SET = utf8;


/*table ted.connection*/

CREATE TABLE IF NOT EXISTS `ted`.`connection` (
  `user_id` INT(11) NOT NULL,
  `connectedUser_id` INT(11) NOT NULL,
  `approved` TINYINT(4) NOT NULL,
  `dateSent` DATETIME NOT NULL,
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


/*table ted.post*/

CREATE TABLE IF NOT EXISTS `ted`.`post` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(5000) NULL DEFAULT NULL,
  `date_posted` DATETIME NOT NULL,
  `path_files` VARCHAR(1000) NULL DEFAULT NULL,
  `hasAudio` TINYINT(4) NOT NULL,
  `hasImages` TINYINT(4) NOT NULL,
  `hasVideos` TINYINT(4) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_post_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_post_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `ted`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8;


/*table ted.comment*/

CREATE TABLE IF NOT EXISTS `ted`.`comment` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
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

/*table ted.like*/

CREATE TABLE IF NOT EXISTS `ted`.`like` (
  `user_id` INT(11) NOT NULL,
  `post_id` INT(11) NOT NULL,
  `date_liked` DATETIME NOT NULL,
  PRIMARY KEY (`user_id`, `post_id`),
  INDEX `fk_user_has_post_post1_idx` (`post_id` ASC),
  INDEX `fk_user_has_post_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_post_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ted`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_post_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `ted`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8;


/*table ted.job*/

CREATE TABLE IF NOT EXISTS `ted`.`job` (
  `job_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(60) NOT NULL,
  `company` VARCHAR(45) NOT NULL,
  `location` VARCHAR(200) NOT NULL,
  `job_function` VARCHAR(200) NOT NULL,
  `job_type` VARCHAR(45) NOT NULL,
  `job_company_type` VARCHAR(200) NOT NULL,
  `experience` VARCHAR(45) NOT NULL,
  `description` VARCHAR(10000) NOT NULL,
  `skills` VARCHAR(1000) NOT NULL,
  `experience_from` INT NOT NULL,
  `experience_to` INT NOT NULL,
  `education_level` VARCHAR(200) NOT NULL,
  `daily_salary` DOUBLE NOT NULL,
  `date_posted` DATETIME NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`job_id`, `user_id`),
  INDEX `fk_job_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_job_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ted`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

/*table ted.jobApplication*/

CREATE TABLE IF NOT EXISTS `ted`.`jobApplication` (
  `user_id` INT(11) NOT NULL,
  `job_id` INT NOT NULL,
  `approved` TINYINT NOT NULL,
  PRIMARY KEY (`user_id`, `job_id`),
  INDEX `fk_user_has_job_job1_idx` (`job_id` ASC),
  INDEX `fk_user_has_job_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_job_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ted`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_job_job1`
    FOREIGN KEY (`job_id`)
    REFERENCES `ted`.`job` (`job_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8;


/*table ted.conversation*/

CREATE TABLE IF NOT EXISTS `ted`.`conversation` (
  `user_id1` INT(11) NOT NULL,
  `user_id2` INT(11) NOT NULL,
  `lastDate` DATETIME NOT NULL,
  PRIMARY KEY (`user_id1`, `user_id2`),
  INDEX `fk_conversation_user2_idx` (`user_id2` ASC),
  CONSTRAINT `fk_conversation_user1`
    FOREIGN KEY (`user_id1`)
    REFERENCES `ted`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_conversation_user2`
    FOREIGN KEY (`user_id2`)
    REFERENCES `ted`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

/*table ted.message*/

CREATE TABLE IF NOT EXISTS `ted`.`message` (
  `message_id` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME NOT NULL,
  `text` VARCHAR(3000) NOT NULL,
  `user_id1` INT(11) NOT NULL,
  `user_id2` INT(11) NOT NULL,
  `sender` TINYINT NOT NULL,
  PRIMARY KEY (`message_id`),
  INDEX `fk_message_conversation1_idx` (`user_id1` ASC, `user_id2` ASC),
  CONSTRAINT `fk_message_conversation1`
    FOREIGN KEY (`user_id1` , `user_id2`)
    REFERENCES `ted`.`conversation` (`user_id1` , `user_id2`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT IGNORE INTO User (id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, prof_exp, education, skills, privateEmail, privateDateOfBirth, privateTelephone, privateGender, privateCountry, privateCity, privateProfExp, privateEducation, privateSkills, workPos, institution, privateWorkPos, privateInstitution) values (1,1,'admin@hotmail.com','HS4mBcBiOYLNcn2997bKaw==','Admin','Surname','2101234567','/LinkedIn/images/default-user.png','1996-02-21',1,'Piraeus','Greece',0, null, null, null, 0,0,0,0,0,0,0,0,0, null, null, 0,0);

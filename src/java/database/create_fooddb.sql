DROP SCHEMA IF EXISTS `fooddb`;
CREATE SCHEMA IF NOT EXISTS `fooddb` DEFAULT CHARACTER SET latin1;
USE `fooddb`;

-- -----------------------------------------------------
-- Table `fooddb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fooddb`.`user` (
  `email` VARCHAR(40) NOT NULL,
  `active` TINYINT(1) NOT NULL DEFAULT '1',
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `reset_password_id` VARCHAR(36),
  PRIMARY KEY (`email`)
);

-- -----------------------------------------------------
-- Table `fooddb`.`food`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fooddb`.`food` (
  `food_id` INT(8) NOT NULL,
  `food_code` INT(8) NOT NULL,
  `food_group_id` BIGINT(15) NOT NULL,
  `food_source_id` BIGINT(15) NOT NULL,
  `food_description` VARCHAR(255) NOT NULL,
  `food_description_f` VARCHAR(255) NOT NULL,
  `country_code` VARCHAR(20) NOT NULL,
  `food_date_of_entry` VARCHAR(10) NOT NULL,
  `food_date_of_publication` VARCHAR(10) NOT NULL,
  `scientific_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`food_id`)
);

-- -----------------------------------------------------
-- Table `fooddb`.`nutrient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fooddb`.`nutrient` (
  `nutrient_id` INT(4) NOT NULL,
  `nutrient_code` INT(15) NOT NULL,
  `nutrient_symbol` VARCHAR(10) NOT NULL,
  `unit` VARCHAR(8) NOT NULL,
  `nutrient_name` VARCHAR(200) NOT NULL,
  `nutrient_name_f` VARCHAR(200) NOT NULL,
  `tagname` VARCHAR(20) NOT NULL,
  `nutrient_decimals` INT(15) NOT NULL,
  PRIMARY KEY (`nutrient_id`)
);

-- -----------------------------------------------------
-- Table `fooddb`.`nutrient_amount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fooddb`.`nutrient_amount` (
  `food_id` INT(8) NOT NULL,
  `nutrient_id` INT(4) NOT NULL,
  `nutrient_value` DOUBLE(12, 5) NOT NULL,
  `standard_error` VARCHAR(8) NOT NULL,
  `number_of_observations` VARCHAR(6) NOT NULL,
  `nutrient_source_id` BIGINT(15) NOT NULL,
  `nutrient_date_of_entry` DATE NOT NULL,
  PRIMARY KEY (`food_id`, `nutrient_id`),
  FOREIGN KEY (`food_id`) REFERENCES `fooddb`.`food`(`food_id`),
  FOREIGN KEY (`nutrient_id`) REFERENCES `fooddb`.`nutrient`(`nutrient_id`)
);

-- -----------------------------------------------------
-- Table `fooddb`.`record`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fooddb`.`record` (
  `record_id` INT(11) NOT NULL AUTO_INCREMENT,
  `owner` VARCHAR(40) NOT NULL,
  `food_id` INT(8) NOT NULL,
  `date` DATE NOT NULL,
  `servings` INT(8) NOT NULL,
  PRIMARY KEY (`record_id`),
  FOREIGN KEY (`owner`) REFERENCES `fooddb`.`user`(`email`),
  FOREIGN KEY (`food_id`) REFERENCES `fooddb`.`food`(`food_id`)
);

INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`)
        VALUES ('deb@gmail.com', true, 'Debora', 'Kwon', 'password');
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`)
        VALUES ('fer@gmail.com', true, 'Fernando', 'Valera', 'password');
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`)
        VALUES ('bon@gmail.com', true, 'Bong', 'Bong', 'password');


INSERT INTO `food` (`food_id`,`food_code`,`food_group_id`,`food_source_id`,
    `food_description`,`food_description_f`,`food_date_of_entry`,
    `food_date_of_publication`,`country_code`,`scientific_name`)
        VALUES (43,43,1,0,'Cheese, ricotta, with whole milk','Fromage ricotta, avec lait entier','1981-01-01','',01036,
                '');
INSERT INTO `food` (`food_id`,`food_code`,`food_group_id`,`food_source_id`,
    `food_description`,`food_description_f`,`food_date_of_entry`,
    `food_date_of_publication`,`country_code`,`scientific_name`)
        VALUES (61,61,1,0,'Milk, fluid, partly skimmed, 2% M.F.','Lait, partiellement écrémé, liquide, 2% M.G.','1981-01-01','',017079,
                '');
INSERT INTO `food` (`food_id`,`food_code`,`food_group_id`,`food_source_id`,
    `food_description`,`food_description_f`,`food_date_of_entry`,
    `food_date_of_publication`,`country_code`,`scientific_name`)
        VALUES (1704,1704,9,3,'Banana, raw','Banana, crue','1984-01-01','',9040,
                '');

INSERT INTO `record` (`owner`,`food_id`,`date`,`servings`)
        VALUES ('deb@gmail.com', 43, '2022-08-17', 1);
INSERT INTO `record` (`owner`,`food_id`,`date`,`servings`)
        VALUES ('deb@gmail.com', 1704, '2022-08-16', 1);
INSERT INTO `record` (`owner`,`food_id`,`date`,`servings`)
        VALUES ('fer@gmail.com', 61, '2022-08-15', 1);
INSERT INTO `record` (`owner`,`food_id`,`date`,`servings`)
        VALUES ('bon@gmail.com', 1704, '2022-08-16', 1);

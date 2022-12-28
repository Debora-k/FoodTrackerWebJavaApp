
USE `fooddb`;

DELETE FROM `record`;
DELETE FROM `nutrient_amount`;
DELETE FROM `nutrient`;
DELETE FROM `food`;
DELETE FROM `user`;

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/FOOD NAME.csv'
    INTO TABLE `food`
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\r\n'
    IGNORE 1 LINES
    (food_id, food_code, food_group_id, food_source_id, food_description,
        food_description_f, food_date_of_entry, food_date_of_publication,
        country_code, scientific_name);

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/NUTRIENT NAME.csv'
    INTO TABLE `nutrient`
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\r\n'
    IGNORE 1 LINES
    (nutrient_id, nutrient_code, nutrient_symbol, unit, nutrient_name,
        nutrient_name_f, tagname, nutrient_decimals);

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/NUTRIENT AMOUNT.csv'
    INTO TABLE `nutrient_amount`
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\r\n'
    IGNORE 1 LINES
    (food_id, nutrient_id, nutrient_value, standard_error,
        number_of_observations, nutrient_source_id, nutrient_date_of_entry);


INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`)
        VALUES ('deb@gmail.com', true, 'Debora', 'Kwon', 'password');
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`)
        VALUES ('fer@gmail.com', true, 'Fernando', 'Valera', 'password');
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`)
        VALUES ('bon@gmail.com', true, 'Bong', 'Bong', 'password');

INSERT INTO `record` (`owner`,`food_id`,`date`, `servings`)
        VALUES ('deb@gmail.com', 43, '2022-08-17', 1);
INSERT INTO `record` (`owner`,`food_id`,`date`, `servings`)
        VALUES ('deb@gmail.com', 1704, '2022-08-16', 1);
INSERT INTO `record` (`owner`,`food_id`,`date`, `servings`)
        VALUES ('fer@gmail.com', 61, '2022-08-15', 1);
INSERT INTO `record` (`owner`,`food_id`,`date`, `servings`)
        VALUES ('bon@gmail.com', 1704, '2022-08-16', 1);
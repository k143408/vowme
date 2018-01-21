CREATE TABLE `user_info` (
  `userid` INT(11) NOT NULL,
  `address` VARCHAR(45) NULL,
  `city` VARCHAR(20) NULL,
  `zipcode` INT(7) NULL,
  `contact_number1` VARCHAR(45) NULL,
  `contact_number2` VARCHAR(45) NULL,
  `is_organizer` VARCHAR(45) NULL,
  `created_at` INT(11) NULL,
  `updated_at` INT(11) NULL,
  PRIMARY KEY (`userid`),
  CONSTRAINT `user_info_fk`
    FOREIGN KEY (`userid`)
    REFERENCES `vowme`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

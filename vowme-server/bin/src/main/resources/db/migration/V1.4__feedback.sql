CREATE TABLE `feedback` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `userid` INT(11) NULL,
  `given_by` INT(11) NULL,
  `causeid` INT(11) NULL,
  `feedback` VARCHAR(2000) NULL,
  `created_at` INT(11) NULL,
  `updated_at` INT(11) NULL,
  PRIMARY KEY (`id`),
  INDEX `feedback_userid_fk_idx` (`userid` ASC),
  INDEX `feedback_userid_fk2_idx` (`given_by` ASC),
  INDEX `feedback_causeid_fk_idx` (`causeid` ASC),
  CONSTRAINT `feedback_userid_fk`
    FOREIGN KEY (`userid`)
    REFERENCES `vowme`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `feedback_userid_fk2`
    FOREIGN KEY (`given_by`)
    REFERENCES `vowme`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `feedback_causeid_fk`
    FOREIGN KEY (`causeid`)
    REFERENCES `vowme`.`cause` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

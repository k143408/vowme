CREATE TABLE `skill` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `created_at` INT(11) NULL,
  `updated_at` INT(11) NULL,
  PRIMARY KEY (`id`));

  CREATE TABLE `user_skill` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `skillid` INT(11) NULL,
  `userid` INT(11) NULL,
  PRIMARY KEY (`id`),
  INDEX `user_skill_fk_idx` (`skillid` ASC),
  INDEX `user_skill_user_fk_idx` (`userid` ASC),
  CONSTRAINT `user_skill_fk`
    FOREIGN KEY (`skillid`)
    REFERENCES `skill` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_skill_user_fk`
    FOREIGN KEY (`userid`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `cause_skill` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `skillid` INT(11) NULL,
  `causeid` INT(11) NULL,
  PRIMARY KEY (`id`),
  INDEX `cause_skill_fk1_idx` (`causeid` ASC),
  INDEX `cause_skill-fk2_idx` (`skillid` ASC),
  CONSTRAINT `cause_skill_fk1`
    FOREIGN KEY (`causeid`)
    REFERENCES `cause` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `cause_skill-fk2`
    FOREIGN KEY (`skillid`)
    REFERENCES `skill` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
	
ALTER TABLE `user` 
ADD COLUMN `rank` FLOAT NULL AFTER `updated_at`;

ALTER TABLE `cause` 
ADD COLUMN `latlong` VARCHAR(45) NULL AFTER `created_at`;

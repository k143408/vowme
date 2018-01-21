CREATE TABLE `team` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `team_member` INT(11) NULL,
  `causeid` INT(11) NULL,
  `is_disabled` BIT(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `team_cause_fk1_idx` (`causeid` ASC),
  INDEX `team_user_fk1_idx` (`team_member` ASC),
  CONSTRAINT `team_user_fk1`
    FOREIGN KEY (`team_member`)
    REFERENCES `vowme`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `team_cause_fk1`
    FOREIGN KEY (`causeid`)
    REFERENCES `vowme`.`cause` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `user_info` 
ADD COLUMN `about_me` VARCHAR(1000) NULL AFTER `contact_number2`;

ALTER TABLE `user_info` 
ADD COLUMN `organization_name` VARCHAR(45) NULL AFTER `is_organizer`;

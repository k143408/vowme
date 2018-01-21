ALTER TABLE `approvals` 
CHANGE COLUMN `is_approved` `is_approved` BIT(2) NULL DEFAULT 2 ,
ADD COLUMN `override` BIT(1) NULL DEFAULT 0 AFTER `is_approved`;


CREATE TABLE `team_notification` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NULL,
  `message` VARCHAR(1000) NULL,
  `notified` INT(1) NULL DEFAULT 0,
  `status` INT(2) NULL DEFAULT -1,
  `question` INT(1) NULL,
  `created_at` INT(11) NULL,
  `updated_at` INT(11) NULL,
  `type` INT(3) NULL,
  PRIMARY KEY (`id`));

  
ALTER TABLE `team_notification` 
CHANGE COLUMN `question` `parameters` VARCHAR(50) NULL DEFAULT NULL;
ALTER TABLE `causetype` 
ADD COLUMN `causeid` INT(11) NULL AFTER `type`,
ADD INDEX `cause_fk_idx` (`causeid` ASC);
ALTER TABLE `causetype` 
ADD CONSTRAINT `cause_fk`
  FOREIGN KEY (`causeid`)
  REFERENCES `cause` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
ALTER TABLE `cause` 
DROP COLUMN `eventType`;

ALTER TABLE `approvals` 
ADD COLUMN `causeid` INT(11) NULL AFTER `id`,
ADD INDEX `cause_fk_idx` (`causeid` ASC);
ALTER TABLE `approvals` 
ADD CONSTRAINT `cause_fk`
  FOREIGN KEY (`causeid`)
  REFERENCES `cause` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `approvals` 
ADD COLUMN `is_approved` BIT(1) NULL AFTER `created_at`;

ALTER TABLE `causetype` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ,
ADD PRIMARY KEY (`id`);


ALTER TABLE `email` 
CHANGE COLUMN `email` `recipient_email` VARCHAR(200) NULL DEFAULT NULL ;

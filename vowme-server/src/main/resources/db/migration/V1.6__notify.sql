ALTER TABLE `vowme`.`approvals` 
CHANGE COLUMN `is_approved` `is_approved` BIT(2) NULL DEFAULT 2 ,
ADD COLUMN `override` BIT(1) NULL DEFAULT 0 AFTER `is_approved` 

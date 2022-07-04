DROP SCHEMA midterm_demo;
CREATE SCHEMA midterm_demo;
USE midterm_demo;

CREATE SCHEMA midterm_demo_test;
USE midterm_demo_test;


DROP TABLE IF EXISTS third_party;
DROP TABLE IF EXISTS student_checking;
DROP TABLE IF EXISTS saving;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS credit_card;
DROP TABLE IF EXISTS checking;
DROP TABLE IF EXISTS `admin`;
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS account_holder;
DROP TABLE IF EXISTS user;


-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Table midterm_demo`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user (
  user_id BIGINT NOT NULL,
  password VARCHAR(255) NULL DEFAULT NULL,
  username VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (user_id))
 ENGINE = InnoDB
 DEFAULT CHARACTER SET = utf8mb4
 COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `midterm_demo`.`account_holder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS account_holder (
  date_of_birth VARCHAR(255) NULL DEFAULT NULL,
  mailing_address VARCHAR(255) NULL DEFAULT NULL,
  name VARCHAR(255) NULL DEFAULT NULL,
  city VARCHAR(255) NULL DEFAULT NULL,
  postal_code INT NULL DEFAULT NULL,
  street VARCHAR(255) NULL DEFAULT NULL,
  account_holder_id BIGINT NOT NULL,
  PRIMARY KEY (account_holder_id),
  CONSTRAINT `FK9mbjvdea4vnrmui59frtt7ybf`
    FOREIGN KEY (account_holder_id)
    REFERENCES user (user_id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `midterm_demo`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `account` (
  account_id BIGINT NOT NULL AUTO_INCREMENT,
  amount_balance DECIMAL(19,2) NULL DEFAULT NULL,
  currency_balance VARCHAR(255) NULL DEFAULT NULL,
  creation_date VARCHAR(255) NULL DEFAULT NULL,
  amount_penalty_fee DECIMAL(19,2) NULL DEFAULT NULL,
  currency_penalty_fee VARCHAR(255) NULL DEFAULT NULL,
  primary_owner BIGINT NULL DEFAULT NULL,
  secondary_owner BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (account_id),
  INDEX `FKgruqtnns6x0s1isfo8gfi2skx` (primary_owner ASC) VISIBLE,
  INDEX `FKlm35kejp8kbhbkmrbqolvuk0b` (secondary_owner ASC) VISIBLE,
  CONSTRAINT `FKgruqtnns6x0s1isfo8gfi2skx`
    FOREIGN KEY (primary_owner)
    REFERENCES account_holder (account_holder_id),
  CONSTRAINT `FKlm35kejp8kbhbkmrbqolvuk0b`
    FOREIGN KEY (secondary_owner)
    REFERENCES account_holder (account_holder_id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `midterm_demo`.`admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `admin` (
  name VARCHAR(255) NULL DEFAULT NULL,
  admin_id BIGINT NOT NULL,
  PRIMARY KEY (admin_id),
  CONSTRAINT `FKj76jc86gjlv7838l361r9tasy`
    FOREIGN KEY (admin_id)
    REFERENCES user (user_id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `midterm_demo`.`checking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS checking (
  minimum_balance DECIMAL(19,2) NULL DEFAULT NULL,
  monthly_maintenance_fee INT NOT NULL,
  secret_key VARCHAR(255) NULL DEFAULT NULL,
  status VARCHAR(255) NULL DEFAULT NULL,
  checking_id BIGINT NOT NULL,
  PRIMARY KEY (checking_id),
  CONSTRAINT `FK208fyi7jrf7te5vsm9vnoamuc`
    FOREIGN KEY (checking_id)
    REFERENCES `midterm_demo`.`account` (`account_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `midterm_demo`.`credit_card`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS credit_card (
  credit_limit DECIMAL(19,2) NULL DEFAULT NULL,
  interest_rate DECIMAL(19,2) NULL DEFAULT NULL,
  credit_card_id BIGINT NOT NULL,
  PRIMARY KEY (credit_card_id),
  CONSTRAINT `FK1nq9v3xjm51dlx1rac46qay51`
    FOREIGN KEY (credit_card_id)
    REFERENCES `account` (account_id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `midterm_demo`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS role (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NULL DEFAULT NULL,
  user_user_id BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX `FKcppkuoqqh417ann4h6gql3luw` (user_user_id ASC) VISIBLE,
  CONSTRAINT `FKcppkuoqqh417ann4h6gql3luw`
    FOREIGN KEY (user_user_id)
    REFERENCES user (user_id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `midterm_demo`.`saving`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS saving (
  interest_rate DECIMAL(19,2) NULL DEFAULT NULL,
  minimum_balance DECIMAL(19,2) NULL DEFAULT NULL,
  secret_key VARCHAR(255) NULL DEFAULT NULL,
  status VARCHAR(255) NULL DEFAULT NULL,
  saving_id BIGINT NOT NULL,
  PRIMARY KEY (saving_id),
  CONSTRAINT `FKahwigia4rrbm4a3hjkjvq661t`
    FOREIGN KEY (saving_id)
    REFERENCES `account` (account_id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `midterm_demo`.`student_checking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS student_checking (
  secret_key VARCHAR(255) NULL DEFAULT NULL,
  status VARCHAR(255) NULL DEFAULT NULL,
  student_checking_id BIGINT NOT NULL,
  PRIMARY KEY (student_checking_id),
  CONSTRAINT `FKg7f7u0x4miiubawv6slu0nxk2`
    FOREIGN KEY (student_checking_id)
    REFERENCES `account` (account_id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `midterm_demo`.`third_party`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS third_party (
  id BIGINT NOT NULL AUTO_INCREMENT,
  hashed_key VARCHAR(255) NULL DEFAULT NULL,
  name VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- SET SQL_MODE=@OLD_SQL_MODE;
-- SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
-- SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

drop schema music_store;

CREATE SCHEMA `music_store` ;

CREATE TABLE `music_store`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `surname` VARCHAR(30) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `type` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);

CREATE TABLE `music_store`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `state` VARCHAR(30) NOT NULL,
  `date` DATE NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `userOrder_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `userOrder`
    FOREIGN KEY (`userId`)
    REFERENCES `music_store`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `music_store`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `disponibility` INT NOT NULL,
  `shippingPrice` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `music_store`.`order_product` (
  `orderId` INT NOT NULL,
  `productId` INT NOT NULL,
  PRIMARY KEY (`orderId`, `productId`),
  INDEX `productListOrder_idx` (`productId` ASC) VISIBLE,
  CONSTRAINT `productListOrder`
    FOREIGN KEY (`productId`)
    REFERENCES `music_store`.`product` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `orderListOrder`
    FOREIGN KEY (`orderId`)
    REFERENCES `music_store`.`order` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `music_store`.`category` (
  `name` VARCHAR(30) NOT NULL, 
  PRIMARY KEY (`name`));

CREATE TABLE `music_store`.`product_category` (
  `productId` INT NOT NULL AUTO_INCREMENT,
  `categoryName` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`productId`, `categoryName`),
  INDEX `categoryProduct_idx` (`categoryName` ASC) VISIBLE,
  CONSTRAINT `categoryProduct`
    FOREIGN KEY (`categoryName`)
    REFERENCES `music_store`.`category` (`name`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `productCategory`
    FOREIGN KEY (`productId`)
    REFERENCES `music_store`.`product` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `music_store`.`address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `surname` VARCHAR(30) NOT NULL,
  `street` VARCHAR(60) NOT NULL,
  `province` VARCHAR(30) NOT NULL,
  `zipCode` VARCHAR(6) NOT NULL,
  `state` VARCHAR(30) NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  `userID` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `userAddress_idx` (`userID` ASC) VISIBLE,
  CONSTRAINT `userAddress`
    FOREIGN KEY (`userID`)
    REFERENCES `music_store`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

ALTER TABLE `music_store`.`order` 
ADD COLUMN `addressID` INT NOT NULL AFTER `userId`;
ALTER TABLE `music_store`.`order` 
ADD CONSTRAINT `addressOrder`
  FOREIGN KEY (`addressID`)
  REFERENCES `music_store`.`address` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `music_store`.`order` 
DROP FOREIGN KEY `addressOrder`;
ALTER TABLE `music_store`.`order` 
CHANGE COLUMN `addressID` `addressId` INT NOT NULL ;
ALTER TABLE `music_store`.`order` 
ADD CONSTRAINT `addressOrder`
  FOREIGN KEY (`addressId`)
  REFERENCES `music_store`.`address` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

CREATE TABLE `music_store`.`cart` (
  `id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `userForcart`
    FOREIGN KEY (`id`)
    REFERENCES `music_store`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `music_store`.`cart_product` (
  `cartId` INT NOT NULL,
  `productId` INT NOT NULL,
  PRIMARY KEY (`cartId`, `productId`),
  INDEX `productId_idx` (`productId` ASC) VISIBLE,
  CONSTRAINT `cartId`
    FOREIGN KEY (`cartId`)
    REFERENCES `music_store`.`cart` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `productId`
    FOREIGN KEY (`productId`)
    REFERENCES `music_store`.`product` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `music_store`.`payment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cardNumber` VARCHAR(16) NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `surname` VARCHAR(30) NOT NULL,
  `expiryDate` DATE NOT NULL,
  `cvv` INT NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`id`, `cardNumber`),
  UNIQUE INDEX `cardNumber_UNIQUE` (`cardNumber` ASC) VISIBLE,
  INDEX `userId_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `userId`
    FOREIGN KEY (`userId`)
    REFERENCES `music_store`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

ALTER TABLE `music_store`.`product` 
CHANGE COLUMN `description` `description` TEXT NOT NULL ;
ALTER TABLE `music_store`.`address` 
ADD COLUMN `city` VARCHAR(45) NOT NULL AFTER `street`;

ALTER TABLE `music_store`.`product` 
ADD COLUMN `imagePath` TEXT NULL AFTER `shippingPrice`;

ALTER TABLE `music_store`.`cart_product` 
ADD COLUMN `quantity` INT NOT NULL DEFAULT 0 AFTER `productId`;

ALTER TABLE `music_store`.`product` 
CHANGE COLUMN `name` `name` VARCHAR(100) NOT NULL ;

ALTER TABLE `music_store`.`product_category` 
RENAME TO  `music_store`.`product_category` ;

ALTER TABLE `music_store`.`category` 
ADD COLUMN `id` INT NULL AUTO_INCREMENT AFTER `name`,
ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE;
;

ALTER TABLE `music_store`.`order_product` 
ADD COLUMN `quantity` INT NULL AFTER `productId`;

ALTER TABLE `music_store`.`order_product` 
CHANGE COLUMN `quantity` `quantity` INT NOT NULL ;

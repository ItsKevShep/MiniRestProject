CREATE TABLE IF NOT EXISTS CUSTOMERS
(
    `id`         	bigint 			AUTO_INCREMENT 	NOT NULL,
    `reference` 	varchar(255)       				NOT NULL,
    `name`  		varchar(255)       				NOT NULL,
    `addressOne`  	varchar(255)       				NULL,
    `addressTwo`  	varchar(255)       				NULL,
    `town`  		varchar(255)       				NULL,
    `county`  		varchar(255)       				NULL,
    `country`  		varchar(255)       				NULL,
    `postcode`  	varchar(255)       				NULL,
    PRIMARY KEY (`id`)
);

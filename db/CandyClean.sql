CREATE DATABASE candyclean DEFAULT CHARSET utf8mb4;

USE candyclean;

CREATE TABLE users
(
	user_id mediumint UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name    varchar(100)       NOT NULL
);

CREATE TABLE games
(
	game_id   mediumint UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	game_seed varchar(20)        NOT NULL,
	timestamp timestamp          NOT NULL DEFAULT CURRENT_TIMESTAMP(),
	movements varchar(255)       NOT NULL,
	score     mediumint          NOT NULL
);

select * from users;


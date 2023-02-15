SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS user_authorities;
DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS leisure_file;
DROP TABLE IF EXISTS review_comment;
DROP TABLE IF EXISTS review_file;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS leisure;
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS qna_comment;
DROP TABLE IF EXISTS qna;
DROP TABLE IF EXISTS user;




/* Create Tables */

CREATE TABLE authority
(
	id int NOT NULL AUTO_INCREMENT,
	name varchar(40) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (name)
);


CREATE TABLE company
(
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	name varchar(100) NOT NULL,
	address varchar(200) NOT NULL,
	companyname varchar(80) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (companyname)
);


CREATE TABLE leisure
(
	id int NOT NULL AUTO_INCREMENT,
	company_id int NOT NULL,
	name varchar(20) NOT NULL,
	price int NOT NULL,
	content text NOT NULL,
	address varchar(200) NOT NULL,
	avgstar float DEFAULT 0,
	PRIMARY KEY (id)
);


CREATE TABLE leisure_file
(
	id int NOT NULL AUTO_INCREMENT,
	leisure_id int NOT NULL,
	source varchar(100) NOT NULL,
	file varchar(100) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE qna
(
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	subject varchar(200) NOT NULL,
	content longtext NOT NULL,
	regdate datetime DEFAULT now(),
	PRIMARY KEY (id)
);


CREATE TABLE qna_comment
(
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	qna_id int NOT NULL,
	content text NOT NULL,
	regdate datetime DEFAULT now(),
	PRIMARY KEY (id)
);


CREATE TABLE reservation
(
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	leisure_id int NOT NULL,
	name varchar(80) NOT NULL,
	regdate datetime DEFAULT now(),
	phone int NOT NULL,
	date int(8) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (phone)
);


CREATE TABLE review
(
	id int NOT NULL AUTO_INCREMENT,
	reservation_id int NOT NULL,
	user_id int NOT NULL,
	leisure_id int NOT NULL,
	subject varchar(200) NOT NULL,
	content longtext NOT NULL,
	regdate datetime DEFAULT now(),
	star int DEFAULT 0,
	PRIMARY KEY (id)
);


CREATE TABLE review_comment
(
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	review_id int NOT NULL,
	content text NOT NULL,
	regdate datetime DEFAULT now(),
	PRIMARY KEY (id)
);


CREATE TABLE review_file
(
	id int NOT NULL AUTO_INCREMENT,
	review_id int NOT NULL,
	source varchar(100) NOT NULL,
	file varchar(100) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE user
(
	id int NOT NULL AUTO_INCREMENT,
	u_id varchar(80) NOT NULL,
	password varchar(100) NOT NULL,
	regdate datetime DEFAULT now(),
	PRIMARY KEY (id),
	UNIQUE (u_id)
);


CREATE TABLE user_authorities
(
	user_id int NOT NULL,
	authority_id int NOT NULL
);



/* Create Foreign Keys */

ALTER TABLE user_authorities
	ADD FOREIGN KEY (authority_id)
	REFERENCES authority (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE leisure
	ADD FOREIGN KEY (company_id)
	REFERENCES company (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE leisure_file
	ADD FOREIGN KEY (leisure_id)
	REFERENCES leisure (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE reservation
	ADD FOREIGN KEY (leisure_id)
	REFERENCES leisure (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE review
	ADD FOREIGN KEY (leisure_id)
	REFERENCES leisure (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE qna_comment
	ADD FOREIGN KEY (qna_id)
	REFERENCES qna (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE review
	ADD FOREIGN KEY (reservation_id)
	REFERENCES reservation (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE review_comment
	ADD FOREIGN KEY (review_id)
	REFERENCES review (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE review_file
	ADD FOREIGN KEY (review_id)
	REFERENCES review (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE company
	ADD FOREIGN KEY (user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE qna
	ADD FOREIGN KEY (user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE qna_comment
	ADD FOREIGN KEY (user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reservation
	ADD FOREIGN KEY (user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE review
	ADD FOREIGN KEY (user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE review_comment
	ADD FOREIGN KEY (user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE user_authorities
	ADD FOREIGN KEY (user_id)
	REFERENCES user (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;




drop database if exists blind_chess;
create database blind_chess;
use blind_chess;

create table user (
	user_id int primary key auto_increment,
	username varchar(250) not null unique,
	email varchar(250) not null unique
);
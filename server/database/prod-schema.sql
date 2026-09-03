drop database if exists blind_chess;
create database blind_chess;
use blind_chess;

create table user (
    user_id int primary key auto_increment,
    username varchar(250) not null unique,
    email varchar(250) not null unique
);

create table game_status (
	status_id int primary key auto_increment,
	status_text varchar(50) not null
);

insert into game_status (status_text) values 
	("IN_PROGRESS"),
	("WHITE_WIN"),
	("BLACK_WIN"),
	("DRAW_INSUFFICIENT_MATERIAL"),
	("DRAW_STALEMATE"),
	("DRAW_50_MOVE_REPETITION");
	

create table player_color (
	player_color_id int primary key auto_increment,
	color_text varchar(10) not null
);

insert into player_color (color_text) values
	("WHITE"),
	("BLACK");

create table game (
	game_id int primary key auto_increment,
	user_id int null,
	engine_level int not null,
	fen varchar(100) not null,
	status_id int not null,
	player_color_id int,
	created_at timestamp default CURRENT_TIMESTAMP,
	board_peaks int not null default 0,
	is_deleted tinyint(1) not null default 0,
	constraint fk_user_id_game
		foreign key (user_id)
		references user(user_id)
		on delete set null,
	constraint fk_status_id_game
		foreign key (status_id)
		references game_status(status_id),
	constraint fk_player_color_id_game
		foreign key (player_color_id)
		references player_color(player_color_id)
);

create table move (
	move_id int primary key auto_increment,
	game_id int,
	move_number int not null,
	move_san varchar(10) not null,
	fen_after varchar(100) not null,
	constraint fk_game_id_move
		foreign key (game_id)
		references game(game_id)
		on delete cascade
);
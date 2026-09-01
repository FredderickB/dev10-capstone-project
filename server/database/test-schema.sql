drop database if exists blind_chess_test;
create database blind_chess_test;
use blind_chess_test;

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

delimiter //
create procedure set_known_good_state()
begin
	delete from move;
	alter table move auto_increment = 1;
	delete from game;
	alter table game auto_increment = 1;
	delete from user;
    alter table user auto_increment = 1;
    insert into user (email, username) values
        ("a@a.com", "a"),
        ("b@b.com", "b");
    insert into game (user_id, engine_level, fen, status_id, created_at, board_peaks, player_color_id) values
    	(1, 1000, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", 1, "2000-01-01 01:01:00", 0, 1);
	insert into move (game_id, move_number, move_san, fen_after) values 
		(1, 1, "E4", "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
end //
delimiter ;

call set_known_good_state();
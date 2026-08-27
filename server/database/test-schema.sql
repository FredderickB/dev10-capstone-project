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
	status_text varchar(20) not null
);

insert into game_status (status_text) values 
	("WHITE_WIN"),
	("BLACK_WIN"),
	("DRAW"),
	("IN_PROGRESS");

create table game (
	game_id int primary key auto_increment,
	user_id int null,
	engine_level int not null,
	fen varchar(100) not null,
	status_id int not null,
	created_at timestamp default CURRENT_TIMESTAMP,
	board_peaks int not null default 0,
	constraint fk_user_id_game
		foreign key (user_id)
		references user(user_id)
		on delete set null,
	constraint fk_status_id_game
		foreign key (status_id)
		references game_status(status_id)
);	

delimiter //
create procedure set_known_good_state()
begin
	delete from user;
    alter table user auto_increment = 1;
	
    insert into user (email, username) values
        ("a@a.com", "a"),
        ("b@b.com", "b");
end //
delimiter ;
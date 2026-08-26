drop database if exists blind_chess_test;
create database blind_chess_test;
use blind_chess_test;

create table user (
    user_id int primary key auto_increment,
    username varchar(250) not null unique,
    email varchar(250) not null unique
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
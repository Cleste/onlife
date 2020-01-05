delete from user_role;
delete from usr;

insert into usr(id, active, password, username) values
(1, true, '$2a$08$7OHzg/AvNqjNI4pnNagGtu3slOCEzCXpPLD3CO4/PUyJE3kocYWqa', 'admin'),
(2, true, '$2a$08$7OHzg/AvNqjNI4pnNagGtu3slOCEzCXpPLD3CO4/PUyJE3kocYWqa', 'user');

insert into user_role(user_id, roles) values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');
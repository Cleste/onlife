delete from message;

insert into message(id, tag, text, user_id) values
(1, 'first', 'first', 1),
(2, 'second', 'second', 2),
(3, 'first', 'third', 1),
(4, 'fourth', 'fourth', 2);

alter sequence hibernate_sequence restart with 10;
insert into apartments (name, address) values
('Crystal Plaza','2111 Jefferson'),
('Buchanan','320 23rd St')
;
commit;

insert into residents (name, room, apartmentId) values
('Dave', '606', 2),
('Run','1008',1)
;
commit;

insert into users (name, password, residentId) values
('Dave', 1234, 1),
('Runuuu', 0000, 2)
;
commit;
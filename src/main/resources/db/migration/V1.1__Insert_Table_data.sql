insert into apartments (name, address) values
('Crystal Plaza','2111 Jefferson'),
('Buchanan','320 23rd St')
;
commit;

insert into users (account, password, name,room,apartmentId) values
('DaveAccount', 1234,'DaveSmith','001', 1),
('RunuuuAccount', 0000,'RunPete','002', 2),
('Admin',123,'','',1)
;
commit;

insert into packages (shipNumber,shipper,deliveredDate,description,status,arrangeDate,notes,userId) values
('20191913','UPS','2019-06-22 19:10:25','this is package 1',1,'2019-06-24 09:10:25','my request__1',1),
('2019324812160','Amazon','2019-06-23 09:33:20','this is package 2',0,'2019-06-24 09:10:25','my request__2',1))
;
commit;
create table if not exists stock
(
 ID int auto_increment primary key,
 NAME varchar(20) null,
 PRICE DECIMAL(10, 2) null,
 UPDATED_ON datetime null
);


insert into stock(NAME,PRICE,UPDATED_ON) values ('DEMOSTOCK1',4.55,now());
insert into stock(NAME,PRICE,UPDATED_ON) values ('DEMOSTOCK2',14.55,now());
insert into stock(NAME,PRICE,UPDATED_ON) values ('DEMOSTOCK3',54.55,now());
insert into stock(NAME,PRICE,UPDATED_ON) values ('DEMOSTOCK4',124.55,now());
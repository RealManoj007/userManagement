create table city_master (city_id integer not null, city_name varchar(255), state_id integer, primary key (city_id)) engine=InnoDB
create table country_master (country_id integer not null, country_name varchar(255), primary key (country_id)) engine=InnoDB
create table state_master (state_id integer not null, state_name varchar(255), country_id integer, primary key (state_id)) engine=InnoDB
create table user_dtls (user_id integer not null auto_increment, created_date date, email varchar(255), name varchar(255), phno bigint, pwd varchar(255), pwd_update varchar(255), updated_date date, city_id integer, country_id integer, state_id integer, primary key (user_id)) engine=InnoDB
alter table city_master add constraint FKfxtjuwt9iqx9n7xl6f8wl6uu4 foreign key (state_id) references state_master (state_id)
alter table state_master add constraint FKbit3kv24ddslslqs9sy3evpjn foreign key (country_id) references country_master (country_id)
alter table user_dtls add constraint FKje2in23ciox5x1y70k2jq4gu foreign key (city_id) references city_master (city_id)
alter table user_dtls add constraint FK3ndya571crf6ifcpx88i2ccvf foreign key (country_id) references country_master (country_id)
alter table user_dtls add constraint FKtpygs6ncmyf980d7usksa6yuk foreign key (state_id) references state_master (state_id)

insert into country_master values (1, 'India');
insert into country_master values (2, 'USA');

insert into state_master values (1, 'AP',1);
insert into state_master values (2, 'TG',1);
insert into state_master values (3, 'RI',2);
insert into state_master values (4, 'NJ',2);

insert into city_master values (1, 'Guntur',1);
insert into city_master values (2, 'Ongole',1);
insert into city_master values (3, 'Hydrabad',2);
insert into city_master values (4, 'Warangal',2);
insert into city_master values (5, 'Providence',3);
insert into city_master values (6, 'New Port',3);
insert into city_master values (7, 'Jersey',4);
insert into city_master values (8, 'Newark',4);
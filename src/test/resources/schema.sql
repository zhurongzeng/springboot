create table contacts (
	id identity,
	firstName varchar(30) not null,
	lastName varchar(50) not null,
	phoneNumber varchar(13),
	emailAddress varchar(30)
);

create table reader (
	username varchar(30) not null,
	password varchar(30) not null,
	fullname varchar(50) not null
);
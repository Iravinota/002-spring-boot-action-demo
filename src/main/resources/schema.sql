create table if not exists reader (
    username varchar(50) not null primary key,
    fullname varchar(100),
    password varchar(50)
);
insert into reader values ('test', 'testtest', '{noop}pppp');

create table if not exists book (
    id int not null primary key,
    reader varchar(50),
    isbn varchar(50),
    title varchar(50),
    author varchar(50),
    description varchar(255)
);
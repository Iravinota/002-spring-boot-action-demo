drop table if exists reader;
create table reader (
    name varchar2(50) not null,
    fullname varchar2(100),
    password varchar2(50)
);
insert into reader(name, fullname, password) values ('test', 'test', 'test');

drop table if exists book;
create table book (
    id int not null,
    isbn varchar2(50),
    name varchar2(50),
    author varchar2(50),
    reader varchar2(50)
);
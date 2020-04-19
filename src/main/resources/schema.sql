drop table if exists reader;
drop table if exists book;

create table reader (
    id int not null,
    name varchar2(50),
    password varchar2(50)
);

create table book (
    id int not null,
    isbn varchar2(50),
    name varchar2(50),
    author varchar2(50),
    reader varchar2(50)
);
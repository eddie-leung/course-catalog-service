create table if not exists COURSE (
    ID serial not null,
    NAME varchar(255),
    CATEGORY varchar(255),
    PRIMARY KEY (ID)
);

insert into COURSE (NAME, CATEGORY) values ('Java in Container', 'Programming');
insert into COURSE (NAME, CATEGORY) values ('Kotlin in Container', 'Programming');
insert into COURSE (NAME, CATEGORY) values ('Spring in Container', 'Framework');
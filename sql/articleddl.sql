create table article
(
    id        bigint generated by default as identity,
    user_name varchar(255),
    content   varchar(255),
    filePath  varchar(255),
    primary key (id)
);
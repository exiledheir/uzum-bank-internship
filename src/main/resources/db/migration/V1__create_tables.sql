create table person
(
    id        bigint primary key not null,
    name      varchar(100)       not null,
    birthdate date               not null
);

create table car
(
    id         bigint primary key not null,
    model      varchar(100)       not null,
    horsepower int                not null,
    ownerid    bigint references person (id)
)


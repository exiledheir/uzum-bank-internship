create table person
(
    id bigint generated always as identity primary key not null,
    name      varchar(100)       not null,
    birthdate date               not null
);

create table car
(
    id       bigint generated always as identity primary key not null,
    model      varchar(100)       not null,
    horsepower int                not null,
    owner_id bigint references person (id) on delete cascade
)


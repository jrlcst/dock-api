create table dock.costumer
(
    id         bigserial primary key,
    uuid       varchar(255) not null unique,
    full_name  varchar(255) not null,
    document   varchar(50)  not null,
    created_at timestamp,
    deleted_at timestamp
);
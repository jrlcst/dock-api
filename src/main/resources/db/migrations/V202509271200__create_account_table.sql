create table dock.account
(
    id                  bigserial primary key,
    uuid                varchar(255)    not null unique,
    costumer_id         varchar(255)    not null,
    agency              varchar(4)     not null,
    number              varchar(12)    not null,
    status              varchar(16)    not null,
    balance             numeric(14, 2) not null default 0,
    created_at          timestamp      not null default now(),
    blocked_at          timestamp,
    closed_at           timestamp,
    last_withdrawal_day date,
    withdrawn_today     numeric(14, 2),
    constraint fk_account_customer foreign key (costumer_id) references dock.costumer(uuid)
);

create index idx_account_costumer_id on dock.account (costumer_id);
create unique index uq_account_uuid on dock.account (uuid);

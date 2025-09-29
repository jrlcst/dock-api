create table dock.transaction
(
    id          bigserial primary key,
    uuid        varchar(36)    not null unique,
    account_id  varchar(36)    not null,
    type        varchar(16)    not null,
    amount      numeric(14, 2) not null,
    occurred_at timestamp      not null,
    description varchar(255),

    constraint fk_tx_account foreign key (account_id) references dock.account(uuid) on delete cascade
);

create index idx_transaction_account_uuid on dock.transaction (account_id);
create unique index uq_transaction_uuid on dock.transaction (uuid);

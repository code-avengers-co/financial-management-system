CREATE TABLE public.transaction
(
    id               bigserial NOT NULL,
    description      text,
    amount           numeric(20, 2),
    transaction_date date,
    type             text,
    id_account       bigint    REFERENCES account (id),
    id_category      bigint    REFERENCES category (id),
    PRIMARY KEY (id)
);
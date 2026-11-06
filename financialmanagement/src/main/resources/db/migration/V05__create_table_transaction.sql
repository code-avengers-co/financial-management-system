CREATE TABLE public.transaction
(
    id               bigserial      NOT NULL,
    description      text,
    amount           numeric(20, 2) NOT NULL,
    transaction_date date           NOT NULL,
    type             text           NOT NULL,
    id_account       bigint         NOT NULL REFERENCES account (id),
    id_category      bigint         NOT NULL REFERENCES category (id),
    PRIMARY KEY (id)
);

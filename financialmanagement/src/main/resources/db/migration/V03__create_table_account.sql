CREATE TABLE public.account
(
    id              bigserial      NOT NULL,
    name            text           NOT NULL,
    initial_balance numeric(20, 2) NOT NULL,
    active          boolean        NOT NULL DEFAULT true,
    id_user         bigint         NOT NULL REFERENCES users (id),
    PRIMARY KEY (id)
);

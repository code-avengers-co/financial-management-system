CREATE TABLE public.users
(
    id            bigserial NOT NULL,
    name          text      NOT NULL,
    email         text      NOT NULL UNIQUE,
    hash_password text      NOT NULL,
    role          text      NOT NULL,
    active        boolean   NOT NULL DEFAULT true,
    PRIMARY KEY (id)
);
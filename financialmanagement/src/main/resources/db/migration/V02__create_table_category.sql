CREATE TABLE public.category
(
    id     bigserial NOT NULL,
    name   text      NOT NULL,
    active boolean   NOT NULL DEFAULT true,
    PRIMARY KEY (id)
);

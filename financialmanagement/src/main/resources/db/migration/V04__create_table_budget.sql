CREATE TABLE public.budget
(
    id           bigserial      NOT NULL,
    limit_amount numeric(20, 2) NOT NULL,
    budget_month integer        NOT NULL,
    budget_year  integer        NOT NULL,
    id_user      bigint         NOT NULL REFERENCES users (id),
    id_category  bigint         NOT NULL REFERENCES category (id),
    PRIMARY KEY (id)
);

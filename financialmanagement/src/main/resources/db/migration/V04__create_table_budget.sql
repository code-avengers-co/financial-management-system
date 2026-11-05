CREATE TABLE public.budget
(
    id           bigserial NOT NULL,
    limit_amount numeric(20, 2),
    budget_month integer   NOT NULL,
    budget_year  integer   NOT NULL,
    id_user      bigint    REFERENCES users (id),
    id_category  bigint    REFERENCES category (id),
    PRIMARY KEY (id)
);
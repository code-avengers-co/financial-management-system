ALTER TABLE public.budget
ADD CONSTRAINT uk_budget_user_category_month_year
UNIQUE (id_user, id_category, budget_month, budget_year);
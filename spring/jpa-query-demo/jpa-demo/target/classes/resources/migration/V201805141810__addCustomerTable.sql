DROP TABLE IF EXISTS public.customer;
CREATE TABLE IF NOT EXISTS public.customer (
-- auto-increment the id
  id           SERIAL PRIMARY KEY,
  age          INT,
  first_name   TEXT NOT NULL,
  last_name    TEXT NOT NULL,
  email        TEXT
);


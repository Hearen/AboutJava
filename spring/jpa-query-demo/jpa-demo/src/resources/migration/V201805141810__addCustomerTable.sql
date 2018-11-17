DROP TABLE IF EXISTS public.customer;
CREATE TABLE IF NOT EXISTS public.customer (
-- auto-increment the id
  id           SERIAL PRIMARY KEY,
  age          INT,
  first_name   TEXT NOT NULL,
  last_name    TEXT NOT NULL,
  email        TEXT
);

INSERT INTO public.customer(id, first_name, last_name, email, age) VALUES (1, 'John', 'Doe', 'johnD@gmail.com', 21);
INSERT INTO public.customer(id, first_name, last_name, email, age) VALUES (2, 'Tom', 'Doe', 'tom@gmail.com', 26);

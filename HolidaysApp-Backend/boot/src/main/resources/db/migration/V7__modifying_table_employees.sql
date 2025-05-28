alter table employees
alter column user_id drop not null;

UPDATE employees
SET user_id = NULL
WHERE user_id IS NOT NULL;

ALTER TABLE employees
DROP CONSTRAINT fk_employees_user;

ALTER TABLE employees
DROP COLUMN user_id;

ALTER TABLE users
ADD COLUMN employee_id INTEGER;

ALTER TABLE users
ADD CONSTRAINT fk_employees_user
    FOREIGN KEY (employee_id)
        REFERENCES employees(person_id)
        ON DELETE RESTRICT;

UPDATE users
SET employee_id = 1
WHERE user_id = 1;

UPDATE users
SET employee_id = 2
WHERE user_id = 2;


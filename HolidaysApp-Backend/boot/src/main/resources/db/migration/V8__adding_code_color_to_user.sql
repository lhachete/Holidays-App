ALTER TABLE users
ADD COLUMN code_color VARCHAR(7);

UPDATE users SET code_color = '#000001' WHERE user_id = 1;
UPDATE users SET code_color = '#000002' WHERE user_id = 2;

ALTER TABLE users
ALTER COLUMN code_color SET DEFAULT '#000000';

ALTER TABLE users
ALTER COLUMN code_color SET NOT NULL;

CREATE UNIQUE INDEX users_code_color_key ON users(code_color);
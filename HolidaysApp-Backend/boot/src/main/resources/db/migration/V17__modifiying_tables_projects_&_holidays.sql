-- Cambiar project_state de ENUM a VARCHAR
ALTER TABLE projects
    ALTER COLUMN project_state TYPE VARCHAR(50) USING project_state::text;

-- Cambiar project_type de ENUM a VARCHAR
ALTER TABLE projects
    ALTER COLUMN project_type TYPE VARCHAR(50) USING project_type::text;

-- Cambiar vacation_type de ENUM a VARCHAR
ALTER TABLE holidays
    ALTER COLUMN vacation_type TYPE VARCHAR(50) USING vacation_type::text;

-- Cambiar vacation_type de ENUM a VARCHAR
ALTER TABLE holidays
    ALTER COLUMN vacation_state TYPE VARCHAR(50) USING vacation_state::text;
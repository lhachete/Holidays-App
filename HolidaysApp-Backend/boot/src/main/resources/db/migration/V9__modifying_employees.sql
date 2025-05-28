ALTER TABLE employees
    ALTER COLUMN dept_id DROP NOT NULL,
    ALTER COLUMN project_id DROP NOT NULL,
    ALTER COLUMN nif DROP NOT NULL,
    ALTER COLUMN birthday DROP NOT NULL,
    ALTER COLUMN country_of_birth DROP NOT NULL,
    ALTER COLUMN birth_city DROP NOT NULL,
    ALTER COLUMN personal_phone DROP NOT NULL,
    -- first_name se mantiene NOT NULL
    -- last_name se mantiene NOT NULL
    ALTER COLUMN title DROP NOT NULL,
    ALTER COLUMN gender DROP NOT NULL,
    ALTER COLUMN marital_status DROP NOT NULL,
    ALTER COLUMN nationality DROP NOT NULL,
    ALTER COLUMN primary_residence DROP NOT NULL,
    ALTER COLUMN personal_email DROP NOT NULL,
    ALTER COLUMN work_email DROP NOT NULL;
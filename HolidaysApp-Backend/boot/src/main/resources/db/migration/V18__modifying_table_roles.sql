ALTER TABLE roles
    ALTER COLUMN rol TYPE VARCHAR(50)
        USING rol::text;

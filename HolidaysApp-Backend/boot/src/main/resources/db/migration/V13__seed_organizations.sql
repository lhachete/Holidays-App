-- V13__seed_organizations.sql
INSERT INTO organizations (org_id, name) VALUES
                                             (1,  'Research & Development'),
                                             (19, 'Human Resources'),
                                             (20, 'Finance'),
                                             (21, 'IT Services'),
                                             (22, 'Marketing'),
                                             (65, 'Logistics')
    ON CONFLICT (org_id) DO NOTHING;  -- evita errores si ya existen

-- Ajusta la secuencia SERIAL para que no choque
SELECT setval(
               pg_get_serial_sequence('organizations','org_id'),
               GREATEST((SELECT MAX(org_id) FROM organizations), nextval(pg_get_serial_sequence('organizations','org_id')))
       );

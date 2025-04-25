INSERT INTO projects (
    project_id, name, description, start_date, expected_end_date, real_end_date, project_state, project_type
) VALUES
      (1, 'Intranet Update', 'Actualización de la intranet interna', '2024-01-10', '2024-03-15', NULL, 'IN_PROGRESS', 'INTERNAL'),
      (2, 'Client Portal', 'Desarrollo de portal para clientes', '2024-02-01', '2024-06-30', NULL, 'PLANNED', 'CLIENT'),
      (3, 'Onboarding Training', 'Programa de formación para nuevos empleados', '2023-11-01', '2023-12-15', '2023-12-10', 'COMPLETED', 'TRAINING'),
      (4, 'CRM Migration', 'Migración del sistema CRM actual', '2023-09-01', '2023-12-01', '2023-11-30', 'COMPLETED', 'CLIENT'),
      (5, 'Security Audit', 'Auditoría de seguridad interna', '2024-03-01', '2024-03-31', NULL, 'IN_PROGRESS', 'INTERNAL'),
      (6, 'AI Workshop', 'Taller de inteligencia artificial', '2024-01-20', '2024-01-25', '2024-01-23', 'COMPLETED', 'TRAINING'),
      (7, 'ERP Integration', 'Integración de sistema ERP para cliente', '2024-04-01', '2024-08-31', NULL, 'PLANNED', 'CLIENT'),
      (8, 'Mobile App', 'Desarrollo de app interna de empleados', '2024-02-15', '2024-05-15', NULL, 'IN_PROGRESS', 'INTERNAL'),
      (9, 'Cloud Migration', 'Migración a servicios cloud', '2023-10-01', '2023-12-31', NULL, 'CANCELLED', 'CLIENT'),
      (10, 'Leadership Training', 'Formación en liderazgo', '2023-07-10', '2023-07-20', '2023-07-18', 'COMPLETED', 'TRAINING');

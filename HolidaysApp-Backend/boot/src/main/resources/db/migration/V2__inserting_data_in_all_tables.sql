INSERT INTO organizations (name) VALUES
    ('Google'),
    ('Microsoft'),
    ('Amazon'),
    ('Apple'),
    ('Meta'),
    ('Netflix'),
    ('Tesla'),
    ('IBM'),
    ('Oracle'),
    ('Intel');

INSERT INTO projects (
    name, description, start_date, expected_end_date, real_end_date,
    project_state, project_type
) VALUES
      ('HolidaysApp Backend', 'Desarrollo del backend de gestión de vacaciones', '2024-01-15', '2024-05-30', '2024-05-15', 'COMPLETED', 'INTERNAL'),
      ('HolidaysApp Frontend', 'Desarrollo del frontend en Angular', '2024-02-01', '2024-06-01', NULL, 'IN_PROGRESS', 'INTERNAL'),
      ('HR System', 'Sistema de RRHH para gestión de empleados', '2023-11-10', '2024-04-10', '2024-04-08', 'COMPLETED', 'CLIENT'),
      ('Intranet Rediseño', 'Rediseño de la intranet corporativa', '2024-03-01', '2024-08-01', NULL, 'IN_PROGRESS', 'INTERNAL'),
      ('ChatBot Soporte', 'Desarrollo de un chatbot para soporte técnico', '2024-04-01', '2024-10-01', NULL, 'PLANNED', 'R&D'),
      ('API Proveedores', 'Creación de una API para gestión de proveedores', '2024-01-20', '2024-06-20', NULL, 'IN_PROGRESS', 'CLIENT'),
      ('App Nóminas', 'Aplicación móvil para ver nóminas de empleados', '2023-12-01', '2024-03-15', '2024-03-10', 'COMPLETED', 'INTERNAL'),
      ('Sistema Tickets', 'Sistema de gestión de tickets para IT', '2024-02-10', '2024-07-10', NULL, 'IN_PROGRESS', 'CLIENT'),
      ('Portal Cliente', 'Portal web para acceso de clientes a servicios', '2024-01-05', '2024-06-05', NULL, 'IN_PROGRESS', 'CLIENT'),
      ('Migración Cloud', 'Migración de servidores locales a la nube', '2024-03-15', '2024-09-15', NULL, 'PLANNED', 'INTERNAL');

INSERT INTO roles (rol) VALUES
    ('ADMIN'),
    ('USUARIO'),
    ('INVITADO');

INSERT INTO departments (
    org_id, name, business_unit, division, cost_center, location, time_zone
) VALUES
      (1, 'Engineering', 'Tech', 'Development', 'CC101', 'Madrid', 'CET'),
      (2, 'Human Resources', 'Admin', 'Recruitment', 'CC102', 'Barcelona', 'CET'),
      (3, 'Marketing', 'Business', 'Content', 'CC103', 'Valencia', 'CET'),
      (4, 'Finance', 'Admin', 'Accounting', 'CC104', 'Bilbao', 'CET'),
      (5, 'IT Support', 'Tech', 'Helpdesk', 'CC105', 'Sevilla', 'CET'),
      (6, 'Sales', 'Business', 'Domestic', 'CC106', 'Zaragoza', 'CET'),
      (7, 'Legal', 'Admin', 'Compliance', 'CC107', 'Madrid', 'CET'),
      (8, 'Logistics', 'Operations', 'Distribution', 'CC108', 'Alicante', 'CET'),
      (9, 'Product', 'Tech', 'Management', 'CC109', 'Málaga', 'CET'),
      (10, 'Customer Success', 'Business', 'Support', 'CC110', 'Murcia', 'CET');

INSERT INTO users (rol_id, username, password, enabled) VALUES
    (1, 'rrodes', 'pass12345678', true),
    (2, 'mhernandez', 'pass12345678', true);

INSERT INTO employees (
    dept_id, project_id, user_id, nif, birthday,
    country_of_birth, birth_city, personal_phone,
    first_name, last_name, title, gender, marital_status,
    nationality, second_nationality, primary_residence,
    personal_email, secondary_email, work_email
) VALUES
      (
          2, 1, 1, '12345678A', '1990-05-10',
          'Spain', 'Alicante', '+34612345678',
          'Roberto', 'Rodes', 'Sr.', 'Male', 'Single',
          'Spanish', NULL, 'Alicante',
          'roberto.rodes@gmail.com', NULL, 'rtrh@gft.com'
      ),
      (
          3, 2, 2, '87654321B', '1988-09-22',
          'Spain', 'Petrer', '+5491133344555',
          'Miguel', 'Hernandez', 'Sr.', 'Male', 'Single',
          'Spanish', 'Italian', 'Barcelona',
          'miguel.hernandez@hotmail.com', 'miguel.secondary@outlook.com', 'm.hernandez@gft.com'
      );

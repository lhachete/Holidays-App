-- V16__inserting_data_into_employees.sql

INSERT INTO employees (
    dept_id,
    project_id,
    user_id,
    nif,
    birthday,
    country_of_birth,
    birth_city,
    personal_phone,
    first_name,
    last_name,
    title,
    gender,
    marital_status,
    nationality,
    second_nationality,
    primary_residence,
    personal_email,
    secondary_email,
    work_email
) VALUES
      -- Roberto Rodes → user_id = 1
      (1, 2, 1, '12345678A', '1990-05-10',
       'Spain', 'Alicante', '+34612345678', 'Roberto', 'Rodes',
       'Sr.', 'Male', 'Single', 'Spanish', NULL,
       'Alicante', 'roberto.rodes@gmail.com', NULL, 'rtrh@gft.com'
      ),

      -- Miguel Hernandez → user_id = 2
      (2, 3, 2, '87654321B', '1988-09-22',
       'Spain', 'Petrer', '+5491133344555', 'Miguel', 'Hernandez',
       'Sr.', 'Male', 'Single', 'Spanish', 'Italian',
       'Barcelona', 'miguel.hernandez@hotmail.com',
       'miguel.secondary@outlook.com', 'm.hernandez@gft.com'
      );
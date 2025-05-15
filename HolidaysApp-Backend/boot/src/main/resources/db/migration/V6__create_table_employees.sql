CREATE TABLE employees (
                           person_id          SERIAL PRIMARY KEY,
                           dept_id            INTEGER   NOT NULL,
                           project_id         INTEGER,
                           user_id            INTEGER   NOT NULL,
                           nif                VARCHAR(20),
                           birthday           DATE,
                           country_of_birth   VARCHAR(100),
                           birth_city         VARCHAR(100),
                           personal_phone     VARCHAR(50),
                           first_name         VARCHAR(100) NOT NULL,
                           last_name          VARCHAR(100) NOT NULL,
                           title              VARCHAR(255),
                           gender             VARCHAR(50),
                           marital_status     VARCHAR(50),
                           nationality        VARCHAR(100),
                           second_nationality VARCHAR(100),
                           primary_residence  VARCHAR(100),
                           personal_email     VARCHAR(255),
                           secondary_email    VARCHAR(255),
                           work_email         VARCHAR(255),
                           CONSTRAINT fk_employees_dept
                               FOREIGN KEY (dept_id)    REFERENCES departments(dept_id),
                           CONSTRAINT fk_employees_project
                               FOREIGN KEY (project_id) REFERENCES projects(project_id),
                           CONSTRAINT fk_employees_user
                               FOREIGN KEY (user_id)    REFERENCES users(user_id)
);
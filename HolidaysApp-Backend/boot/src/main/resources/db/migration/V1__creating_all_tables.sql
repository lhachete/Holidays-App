-- Crea la tabla organizations
CREATE TABLE organizations (
                               org_id SERIAL PRIMARY KEY,
                               name VARCHAR(255) NOT NULL UNIQUE
);

-- Añade restricción para que el nombre sea único
ALTER TABLE organizations
    ADD CONSTRAINT unique_name UNIQUE (name);

-- Crea la tabla departments
CREATE TABLE departments (
                             dept_id SERIAL PRIMARY KEY,
                             org_id INTEGER NOT NULL,
                             name VARCHAR(255) NOT NULL,
                             business_unit VARCHAR(255),
                             division VARCHAR(255),
                             cost_center VARCHAR(255),
                             location VARCHAR(255) NOT NULL,
                             time_zone VARCHAR(255),

    -- Clave foránea que conecta con organizations
                             CONSTRAINT fk_organization
                                 FOREIGN KEY (org_id)
                                     REFERENCES organizations(org_id)
);

-- Crea la tabla roles
CREATE TABLE roles (
                       rol_id SERIAL PRIMARY KEY,
                       rol VARCHAR(50) NOT NULL UNIQUE
);

-- Crea la tabla users
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       rol_id INTEGER NOT NULL,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       enabled BOOLEAN NOT NULL,

-- Clave foránea hacia roles
                       CONSTRAINT fk_user_role
                           FOREIGN KEY (rol_id)
                               REFERENCES roles(rol_id)
                               ON DELETE RESTRICT
);

CREATE TABLE projects (
                          project_id        SERIAL PRIMARY KEY,
                          name              VARCHAR(255) NOT NULL,
                          description       VARCHAR(500),
                          start_date        DATE NOT NULL,
                          expected_end_date DATE NOT NULL,
                          real_end_date     DATE,
                          project_state     VARCHAR(50) NOT NULL,
                          project_type      VARCHAR(50) NOT NULL
);

CREATE TABLE employees (
                           person_id          SERIAL PRIMARY KEY,
                           dept_id            INTEGER NOT NULL,
                           project_id         INTEGER,
                           user_id            INTEGER NOT NULL,
                           nif                VARCHAR(20) NOT NULL UNIQUE,
                           birthday           DATE NOT NULL,
                           country_of_birth   VARCHAR(100) NOT NULL,
                           birth_city         VARCHAR(100) NOT NULL,
                           personal_phone     VARCHAR(50) NOT NULL,
                           first_name         VARCHAR(100) NOT NULL,
                           last_name          VARCHAR(100) NOT NULL,
                           title              VARCHAR(255) NOT NULL,
                           gender             VARCHAR(50) NOT NULL,
                           marital_status     VARCHAR(50) NOT NULL,
                           nationality        VARCHAR(100) NOT NULL,
                           second_nationality VARCHAR(100),
                           primary_residence  VARCHAR(100) NOT NULL,
                           personal_email     VARCHAR(255) NOT NULL,
                           secondary_email    VARCHAR(255),
                           work_email         VARCHAR(255) NOT NULL,
                           CONSTRAINT fk_employees_dept
                               FOREIGN KEY (dept_id)    REFERENCES departments(dept_id),
                           CONSTRAINT fk_employees_project
                               FOREIGN KEY (project_id) REFERENCES projects(project_id),
                           CONSTRAINT fk_employees_user
                               FOREIGN KEY (user_id)    REFERENCES users(user_id)
);

CREATE TABLE holidays (
                          holiday_id           SERIAL PRIMARY KEY,
                          user_id              INTEGER   NOT NULL,
                          reviewed_by_admin_id INTEGER,
                          review_date          DATE,
                          review_comment       VARCHAR(500),
                          holiday_start_date   DATE      NOT NULL,
                          holiday_end_date     DATE      NOT NULL,
                          vacation_type        VARCHAR(50) NOT NULL,
                          vacation_state       VARCHAR(50) NOT NULL,
                          created_at           TIMESTAMPTZ NOT NULL,
                          deleted_at           TIMESTAMPTZ,
                          updated_at           TIMESTAMPTZ,
                          created_by           INTEGER,
                          updated_by           INTEGER,
                          deleted_by           INTEGER,
                          is_deleted           BOOLEAN   NOT NULL DEFAULT FALSE,
                          CONSTRAINT fk_holidays_user
                              FOREIGN KEY (user_id)              REFERENCES users(user_id),
                          CONSTRAINT fk_holidays_reviewer
                              FOREIGN KEY (reviewed_by_admin_id) REFERENCES users(user_id),
                          CONSTRAINT fk_holidays_created_by
                              FOREIGN KEY (created_by)           REFERENCES users(user_id),
                          CONSTRAINT fk_holidays_updated_by
                              FOREIGN KEY (updated_by)           REFERENCES users(user_id),
                          CONSTRAINT fk_holidays_deleted_by
                              FOREIGN KEY (deleted_by)           REFERENCES users(user_id)
);
-- Table: roles
CREATE TABLE roles (
    rol_id SERIAL PRIMARY KEY,
    rol role_type NOT NULL
);

-- Table: users
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    rol_id INTEGER NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    profile VARCHAR(255),
    CONSTRAINT fk_users_roles FOREIGN KEY (rol_id) REFERENCES roles(rol_id)
);

-- Table: departments
CREATE TABLE departments (
     dept_id SERIAL PRIMARY KEY,
     organization_id INTEGER NOT NULL,
     name VARCHAR(255) NOT NULL,
     business_unit VARCHAR(255),
     division VARCHAR(255),
     cost_center VARCHAR(255),
     location VARCHAR(255) NOT NULL,
     time_zone VARCHAR(255),
     CONSTRAINT fk_departments_organization FOREIGN KEY (organization_id) REFERENCES organizations(org_id)
);

-- Table: projects
CREATE TABLE projects (
  project_id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  start_date DATE NOT NULL,
  expected_end_date DATE,
  real_end_date DATE,
  project_state project_state NOT NULL,
  project_type project_type NOT NULL,
  vacation_type vacation_type NOT NULL
);

-- Table: employees
CREATE TABLE employees (
    person_id SERIAL PRIMARY KEY,
    dept_id INTEGER NOT NULL,
    project_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    nif VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL,
    country_of_birth VARCHAR(255),
    birth_city VARCHAR(255) NOT NULL,
    personal_phone VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    title title NOT NULL,
    gender gender NOT NULL,
    marital_status marital_status NOT NULL,
    nationality VARCHAR(255) NOT NULL,
    second_nationality VARCHAR(255),
    primary_residence VARCHAR(255) NOT NULL,
    personal_email VARCHAR(255) NOT NULL,
    secondary_email VARCHAR(255),
    work_email VARCHAR(255) NOT NULL,
    CONSTRAINT fk_employees_department FOREIGN KEY (dept_id) REFERENCES departments(dept_id),
    CONSTRAINT fk_employees_project FOREIGN KEY (project_id) REFERENCES projects(project_id),
    CONSTRAINT fk_employees_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Table: holidays
CREATE TABLE holidays (
  holiday_id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  reviewed_by_admin_id INTEGER,
  review_date DATE,
  review_comment VARCHAR(255),
  holiday_start_date DATE NOT NULL,
  holiday_end_date DATE NOT NULL,
  vacation_state vacation_state NOT NULL,
  CONSTRAINT fk_holidays_user FOREIGN KEY (user_id) REFERENCES users(user_id),
  CONSTRAINT fk_holidays_reviewed_admin FOREIGN KEY (reviewed_by_admin_id) REFERENCES users(user_id)
);
CREATE TABLE users (
                       user_id  SERIAL PRIMARY KEY,
                       rol_id   INTEGER   NOT NULL,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       enabled  BOOLEAN   NOT NULL DEFAULT TRUE,
                       profile  VARCHAR(500),
                       CONSTRAINT fk_users_role
                           FOREIGN KEY (rol_id) REFERENCES roles(rol_id)
);
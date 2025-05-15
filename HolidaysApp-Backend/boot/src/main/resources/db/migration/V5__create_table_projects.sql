CREATE TABLE projects (
                          project_id        SERIAL PRIMARY KEY,
                          name              VARCHAR(255) NOT NULL,
                          description       VARCHAR(500),
                          start_date        DATE,
                          expected_end_date DATE,
                          real_end_date     DATE,
                          project_state     VARCHAR(50),
                          project_type      VARCHAR(50)
);
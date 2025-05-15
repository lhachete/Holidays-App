CREATE TABLE departments (
                             dept_id        SERIAL PRIMARY KEY,
                             organization_id INTEGER   NOT NULL,
                             name           VARCHAR(255) NOT NULL,
                             business_unit  VARCHAR(255),
                             division       VARCHAR(255),
                             cost_center    VARCHAR(255),
                             location       VARCHAR(255),
                             time_zone      VARCHAR(255),
                             CONSTRAINT fk_departments_organization
                                 FOREIGN KEY (organization_id) REFERENCES organizations(org_id)
);
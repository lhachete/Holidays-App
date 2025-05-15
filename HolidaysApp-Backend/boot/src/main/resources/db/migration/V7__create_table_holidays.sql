CREATE TABLE holidays (
                          holiday_id           SERIAL PRIMARY KEY,
                          user_id              INTEGER   NOT NULL,
                          reviewed_by_admin_id INTEGER,
                          review_date          DATE,
                          review_comment       VARCHAR(500),
                          holiday_start_date   DATE      NOT NULL,
                          holiday_end_date     DATE      NOT NULL,
                          vacation_type        VARCHAR(50),
                          vacation_state       VARCHAR(50),
                          created_at           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
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
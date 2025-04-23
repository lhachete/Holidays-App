-- Añadir campos de trazabilidad
ALTER TABLE holidays
    ADD COLUMN created_at TIMESTAMP,
    ADD COLUMN updated_at TIMESTAMP,
    ADD COLUMN deleted_at TIMESTAMP,
    ADD COLUMN created_by INTEGER,
    ADD COLUMN updated_by INTEGER,
    ADD COLUMN deleted_by INTEGER,
    ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE,
    ADD CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES users(user_id),
    ADD CONSTRAINT fk_updated_by FOREIGN KEY (updated_by) REFERENCES users(user_id),
    ADD CONSTRAINT fk_deleted_by FOREIGN KEY (deleted_by) REFERENCES users(user_id);
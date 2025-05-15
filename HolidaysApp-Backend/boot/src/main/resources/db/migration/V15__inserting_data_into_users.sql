INSERT INTO users (
    rol_id, username, password, enabled, profile
) VALUES
      (1, 'rrodes',     'password123', TRUE, 'Desarrollador backend con experiencia en Java y Spring Boot'),  -- ADMIN → rol_id = 1
      (2, 'mhernandez','password123', TRUE, 'Desarrollador Frontend con experiencia en React y Angular');      -- USUARIO → rol_id = 2
INSERT INTO holidays (
    user_id,
    reviewed_by_admin_id,
    review_date,
    review_comment,
    holiday_start_date,
    holiday_end_date,
    vacation_type,
    vacation_state,
    created_at,
    updated_at,
    is_deleted,
    created_by,
    updated_by
) VALUES
-- Primer registro: vacaciones pendientes, sin revisar
(
    1,
    NULL,
    NULL,
    NULL,
    '2025-07-01',
    '2025-07-15',
    'vacaciones anuales',
    'pendiente',
    NOW(),
    NULL,
    FALSE,
    1,
    NULL
),
-- Segundo registro: vacaciones aprobadas y revisadas
(
    2,
    1,
    '2025-05-15',
    'Vacaciones aprobadas sin objeciones.',
    '2025-08-05',
    '2025-08-20',
    'vacaciones anuales',
    'aprobado',
    NOW(),
    NOW(),
    FALSE,
    2,
    1
);
-- =============================================
-- V1: Create admin_users table
-- Gateway Database
-- =============================================

CREATE TABLE IF NOT EXISTS admin_users (
    id              BIGSERIAL       PRIMARY KEY,
    username        VARCHAR(50)     NOT NULL UNIQUE,
    password        VARCHAR(255)    NOT NULL,
    email           VARCHAR(100)    NOT NULL UNIQUE,
    full_name       VARCHAR(100),
    role            VARCHAR(20)     NOT NULL DEFAULT 'SUPER_ADMIN',
    enabled         BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);

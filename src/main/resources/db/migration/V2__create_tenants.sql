-- =============================================
-- V2: Create tenants table
-- Gateway Database
-- =============================================

CREATE TABLE IF NOT EXISTS tenants (
    id                      BIGSERIAL       PRIMARY KEY,
    company_code            VARCHAR(50)     NOT NULL UNIQUE,
    company_name            VARCHAR(200)    NOT NULL,
    company_name_ar         VARCHAR(200),
    logo_url                VARCHAR(500),
    hosting_type            VARCHAR(20)     NOT NULL,
    server_url              VARCHAR(500)    NOT NULL,
    db_host                 VARCHAR(255),
    db_port                 INTEGER,
    db_name                 VARCHAR(100),
    contact_name            VARCHAR(100),
    contact_phone           VARCHAR(20),
    contact_email           VARCHAR(150),
    max_employees           INTEGER,
    subscription_expiry     TIMESTAMP,
    is_active               BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at              TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    notes                   VARCHAR(1000)
);
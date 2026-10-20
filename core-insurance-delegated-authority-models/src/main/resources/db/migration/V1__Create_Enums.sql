-- Flyway V1: enum types for core-insurance-delegated-authority

CREATE TYPE binder_status_enum AS ENUM (
    'DRAFT',
    'ACTIVE',
    'SUSPENDED',
    'EXPIRED'
);

CREATE TYPE line_of_business_enum AS ENUM (
    'MOTOR',
    'PROPERTY',
    'LIABILITY',
    'LIFE',
    'HEALTH',
    'MARINE',
    'AVIATION',
    'ACCIDENT',
    'OTHER'
);

CREATE TYPE authority_limit_type_enum AS ENUM (
    'SUM_INSURED',
    'PREMIUM',
    'TERRITORY',
    'LINE'
);


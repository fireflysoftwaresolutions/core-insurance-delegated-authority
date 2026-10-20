-- Flyway V2: tables for core-insurance-delegated-authority

-- Delegated-authority binder between a carrier and a coverholder
CREATE TABLE binder (
    binder_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    binder_number VARCHAR(64) NOT NULL UNIQUE,
    contract_id UUID NOT NULL,
    carrier_party_id UUID NOT NULL,
    coverholder_distributor_id UUID NOT NULL,
    line_of_business line_of_business_enum NOT NULL,
    effective_from DATE NOT NULL,
    effective_to DATE NULL,
    binder_status binder_status_enum NOT NULL,
    currency VARCHAR(3) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Limit inside a binder: sum insured, premium, territory, or line
CREATE TABLE authority_limit (
    authority_limit_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    binder_id UUID NOT NULL REFERENCES binder (binder_id) ON DELETE CASCADE,
    limit_type authority_limit_type_enum NOT NULL,
    maximum_amount DECIMAL(19, 4) NULL,
    currency VARCHAR(3) NULL,
    territory VARCHAR(128) NULL,
    line_of_business line_of_business_enum NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Appointment of a distributor under a binder
CREATE TABLE coverholder_appointment (
    coverholder_appointment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    binder_id UUID NOT NULL REFERENCES binder (binder_id) ON DELETE CASCADE,
    distributor_id UUID NOT NULL,
    party_id UUID NULL,
    appointed_from DATE NOT NULL,
    appointed_to DATE NULL,
    active BOOLEAN NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_binder_tenant_id ON binder (tenant_id);
CREATE INDEX idx_binder_contract_id ON binder (contract_id);
CREATE INDEX idx_binder_carrier_party_id ON binder (carrier_party_id);
CREATE INDEX idx_binder_coverholder_distributor_id ON binder (coverholder_distributor_id);
CREATE INDEX idx_authority_limit_binder_id ON authority_limit (binder_id);
CREATE INDEX idx_coverholder_appointment_binder_id ON coverholder_appointment (binder_id);
CREATE INDEX idx_coverholder_appointment_distributor_id ON coverholder_appointment (distributor_id);
CREATE INDEX idx_coverholder_appointment_party_id ON coverholder_appointment (party_id);
COMMENT ON COLUMN binder.tenant_id IS 'Logical reference: Firefly deployment tenant. No foreign key across services.';
COMMENT ON COLUMN binder.contract_id IS 'Logical reference: core-common-contract-mgmt contract. No foreign key across services.';
COMMENT ON COLUMN binder.carrier_party_id IS 'Logical reference: core-common-customer-mgmt party. No foreign key across services.';
COMMENT ON COLUMN binder.coverholder_distributor_id IS 'Logical reference: core-common-distributor-mgmt distributor. No foreign key across services.';
COMMENT ON COLUMN coverholder_appointment.distributor_id IS 'Logical reference: core-common-distributor-mgmt distributor. No foreign key across services.';
COMMENT ON COLUMN coverholder_appointment.party_id IS 'Logical reference: core-common-customer-mgmt party. No foreign key across services.';

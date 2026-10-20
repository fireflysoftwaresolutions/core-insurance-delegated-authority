/*
 * Copyright 2026 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.insurance.authority.models.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.firefly.core.insurance.authority.interfaces.enums.BinderStatus;
import com.firefly.core.insurance.authority.interfaces.enums.LineOfBusiness;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Delegated-authority binder between a carrier and a coverholder.
 * Maps to the 'binder' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("binder")
public class Binder {

    @Id
    @Column("binder_id")
    private UUID binderId;

    @Column("tenant_id")
    private UUID tenantId;

    @Column("binder_number")
    private String binderNumber;

    @Column("contract_id")
    private UUID contractId;

    @Column("carrier_party_id")
    private UUID carrierPartyId;

    @Column("coverholder_distributor_id")
    private UUID coverholderDistributorId;

    @Column("line_of_business")
    private LineOfBusiness lineOfBusiness;

    @Column("effective_from")
    private LocalDate effectiveFrom;

    @Column("effective_to")
    private LocalDate effectiveTo;

    @Column("binder_status")
    private BinderStatus binderStatus;

    @Column("currency")
    private String currency;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

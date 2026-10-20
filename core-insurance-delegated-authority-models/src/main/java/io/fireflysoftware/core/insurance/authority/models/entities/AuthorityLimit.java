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


package io.fireflysoftware.core.insurance.authority.models.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import io.fireflysoftware.core.insurance.authority.interfaces.enums.AuthorityLimitType;
import io.fireflysoftware.core.insurance.authority.interfaces.enums.LineOfBusiness;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Limit inside a binder: sum insured, premium, territory, or line.
 * Maps to the 'authority_limit' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("authority_limit")
public class AuthorityLimit {

    @Id
    @Column("authority_limit_id")
    private UUID authorityLimitId;

    @Column("binder_id")
    private UUID binderId;

    @Column("limit_type")
    private AuthorityLimitType limitType;

    @Column("maximum_amount")
    private BigDecimal maximumAmount;

    @Column("currency")
    private String currency;

    @Column("territory")
    private String territory;

    @Column("line_of_business")
    private LineOfBusiness lineOfBusiness;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

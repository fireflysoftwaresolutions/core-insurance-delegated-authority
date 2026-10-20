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


package io.fireflysoftware.core.insurance.authority.interfaces.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import io.fireflysoftware.core.insurance.authority.interfaces.enums.BinderStatus;
import io.fireflysoftware.core.insurance.authority.interfaces.enums.LineOfBusiness;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.utils.annotations.FilterableId;

/**
 * Delegated-authority binder between a carrier and a coverholder.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BinderDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID binderId;

    /** Logical reference: Firefly deployment tenant. This service does not own that record. */
    @FilterableId
    @NotNull(message = "tenantId is required")
    private UUID tenantId;

    @NotBlank(message = "binderNumber is required")
    @Size(max = 64, message = "binderNumber must not exceed 64 characters")
    private String binderNumber;

    /** Logical reference: core-common-contract-mgmt contract. This service does not own that record. */
    @FilterableId
    @NotNull(message = "contractId is required")
    private UUID contractId;

    /** Logical reference: core-common-customer-mgmt party. This service does not own that record. */
    @FilterableId
    @NotNull(message = "carrierPartyId is required")
    private UUID carrierPartyId;

    /** Logical reference: core-common-distributor-mgmt distributor. This service does not own that record. */
    @FilterableId
    @NotNull(message = "coverholderDistributorId is required")
    private UUID coverholderDistributorId;

    @NotNull(message = "lineOfBusiness is required")
    private LineOfBusiness lineOfBusiness;

    @NotNull(message = "effectiveFrom is required")
    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;

    @NotNull(message = "binderStatus is required")
    private BinderStatus binderStatus;

    @Pattern(regexp = "[A-Z]{3}", message = "currency must be a 3-letter ISO code")
    private String currency;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

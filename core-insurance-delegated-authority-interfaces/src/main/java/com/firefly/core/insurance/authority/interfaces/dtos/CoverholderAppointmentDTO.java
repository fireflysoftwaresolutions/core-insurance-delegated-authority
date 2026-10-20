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


package com.firefly.core.insurance.authority.interfaces.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fireflyframework.utils.annotations.FilterableId;

/**
 * Appointment of a distributor under a binder.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoverholderAppointmentDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID coverholderAppointmentId;

    @FilterableId
    @NotNull(message = "Binder id is required")
    private UUID binderId;

    /** Logical reference: core-common-distributor-mgmt distributor. This service does not own that record. */
    @FilterableId
    @NotNull(message = "distributorId is required")
    private UUID distributorId;

    /** Logical reference: core-common-customer-mgmt party. This service does not own that record. */
    @FilterableId
    private UUID partyId;

    @NotNull(message = "appointedFrom is required")
    private LocalDate appointedFrom;

    private LocalDate appointedTo;

    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

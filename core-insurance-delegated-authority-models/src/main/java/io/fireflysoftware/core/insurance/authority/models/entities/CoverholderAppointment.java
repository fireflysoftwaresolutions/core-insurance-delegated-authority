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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Appointment of a distributor under a binder.
 * Maps to the 'coverholder_appointment' table.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("coverholder_appointment")
public class CoverholderAppointment {

    @Id
    @Column("coverholder_appointment_id")
    private UUID coverholderAppointmentId;

    @Column("binder_id")
    private UUID binderId;

    @Column("distributor_id")
    private UUID distributorId;

    @Column("party_id")
    private UUID partyId;

    @Column("appointed_from")
    private LocalDate appointedFrom;

    @Column("appointed_to")
    private LocalDate appointedTo;

    @Column("active")
    private Boolean active;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

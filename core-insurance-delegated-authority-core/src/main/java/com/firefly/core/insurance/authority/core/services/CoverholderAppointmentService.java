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


package com.firefly.core.insurance.authority.core.services;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.insurance.authority.interfaces.dtos.CoverholderAppointmentDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for appointment of a distributor under a binder.
 */
public interface CoverholderAppointmentService {

    /**
     * Filters coverholderAppointments with the given criteria.
     *
     * @param parentId and filterRequest filter criteria
     * @return a page of CoverholderAppointmentDTO
     */
    Mono<PaginationResponse<CoverholderAppointmentDTO>> filterCoverholderAppointments(UUID binderId, FilterRequest<CoverholderAppointmentDTO> filterRequest);

    /**
     * Creates a CoverholderAppointment.
     *
     * @param parentId and dto values to store
     * @return the stored DTO
     */
    Mono<CoverholderAppointmentDTO> createCoverholderAppointment(UUID binderId, CoverholderAppointmentDTO dto);

    /**
     * Updates a CoverholderAppointment.
     *
     * @param parentId, id and dto
     * @return the updated DTO
     */
    Mono<CoverholderAppointmentDTO> updateCoverholderAppointment(UUID binderId, UUID coverholderAppointmentId, CoverholderAppointmentDTO dto);

    /**
     * Deletes a CoverholderAppointment.
     *
     * @param parentId and id identifier
     * @return completion signal
     */
    Mono<Void> deleteCoverholderAppointment(UUID binderId, UUID coverholderAppointmentId);

    /**
     * Loads a CoverholderAppointment by id.
     *
     * @param parentId and id identifier
     * @return the DTO
     */
    Mono<CoverholderAppointmentDTO> getCoverholderAppointmentById(UUID binderId, UUID coverholderAppointmentId);
}

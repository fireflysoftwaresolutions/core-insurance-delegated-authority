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
import com.firefly.core.insurance.authority.interfaces.dtos.BinderDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for delegated-authority binder between a carrier and a coverholder.
 */
public interface BinderService {

    /**
     * Filters binders with the given criteria.
     *
     * @param filterRequest filter criteria
     * @return a page of BinderDTO
     */
    Mono<PaginationResponse<BinderDTO>> filterBinders(FilterRequest<BinderDTO> filterRequest);

    /**
     * Creates a Binder.
     *
     * @param dto values to store
     * @return the stored DTO
     */
    Mono<BinderDTO> createBinder(BinderDTO dto);

    /**
     * Updates a Binder.
     *
     * @param id and dto
     * @return the updated DTO
     */
    Mono<BinderDTO> updateBinder(UUID binderId, BinderDTO dto);

    /**
     * Deletes a Binder.
     *
     * @param id identifier
     * @return completion signal
     */
    Mono<Void> deleteBinder(UUID binderId);

    /**
     * Loads a Binder by id.
     *
     * @param id identifier
     * @return the DTO
     */
    Mono<BinderDTO> getBinderById(UUID binderId);
}

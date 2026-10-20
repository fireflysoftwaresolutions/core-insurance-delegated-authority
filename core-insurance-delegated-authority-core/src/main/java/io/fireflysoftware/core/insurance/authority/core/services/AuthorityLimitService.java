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


package io.fireflysoftware.core.insurance.authority.core.services;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.insurance.authority.interfaces.dtos.AuthorityLimitDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository operations for limit inside a binder: sum insured, premium, territory, or line.
 */
public interface AuthorityLimitService {

    /**
     * Filters authorityLimits with the given criteria.
     *
     * @param parentId and filterRequest filter criteria
     * @return a page of AuthorityLimitDTO
     */
    Mono<PaginationResponse<AuthorityLimitDTO>> filterAuthorityLimits(UUID binderId, FilterRequest<AuthorityLimitDTO> filterRequest);

    /**
     * Creates a AuthorityLimit.
     *
     * @param parentId and dto values to store
     * @return the stored DTO
     */
    Mono<AuthorityLimitDTO> createAuthorityLimit(UUID binderId, AuthorityLimitDTO dto);

    /**
     * Updates a AuthorityLimit.
     *
     * @param parentId, id and dto
     * @return the updated DTO
     */
    Mono<AuthorityLimitDTO> updateAuthorityLimit(UUID binderId, UUID authorityLimitId, AuthorityLimitDTO dto);

    /**
     * Deletes a AuthorityLimit.
     *
     * @param parentId and id identifier
     * @return completion signal
     */
    Mono<Void> deleteAuthorityLimit(UUID binderId, UUID authorityLimitId);

    /**
     * Loads a AuthorityLimit by id.
     *
     * @param parentId and id identifier
     * @return the DTO
     */
    Mono<AuthorityLimitDTO> getAuthorityLimitById(UUID binderId, UUID authorityLimitId);
}

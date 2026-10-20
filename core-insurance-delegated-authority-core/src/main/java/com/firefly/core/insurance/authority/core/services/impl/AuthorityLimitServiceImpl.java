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


package com.firefly.core.insurance.authority.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.web.error.exceptions.ResourceNotFoundException;
import com.firefly.core.insurance.authority.core.mappers.AuthorityLimitMapper;
import com.firefly.core.insurance.authority.core.services.AuthorityLimitService;
import com.firefly.core.insurance.authority.interfaces.dtos.AuthorityLimitDTO;
import com.firefly.core.insurance.authority.models.entities.AuthorityLimit;
import com.firefly.core.insurance.authority.models.repositories.AuthorityLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class AuthorityLimitServiceImpl implements AuthorityLimitService {

    @Autowired
    private AuthorityLimitRepository repository;

    @Autowired
    private AuthorityLimitMapper mapper;

    @Override
    public Mono<PaginationResponse<AuthorityLimitDTO>> filterAuthorityLimits(UUID binderId, FilterRequest<AuthorityLimitDTO> filterRequest) {
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new AuthorityLimitDTO());
        }
        filterRequest.getFilters().setBinderId(binderId);
        return FilterUtils.createFilter(AuthorityLimit.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<AuthorityLimitDTO> createAuthorityLimit(UUID binderId, AuthorityLimitDTO dto) {
        return Mono.just(dto)
                .doOnNext(value -> value.setBinderId(binderId))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<AuthorityLimitDTO> updateAuthorityLimit(UUID binderId, UUID authorityLimitId, AuthorityLimitDTO dto) {
        return repository.findById(authorityLimitId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("AuthorityLimit", authorityLimitId.toString())))
                .flatMap(existing -> {
                    if (!binderId.equals(existing.getBinderId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "AuthorityLimit with id '" + authorityLimitId + "' does not belong to binder '" + binderId + "'"));
                    }
                    mapper.updateEntityFromDto(dto, existing);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteAuthorityLimit(UUID binderId, UUID authorityLimitId) {
        return repository.findById(authorityLimitId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("AuthorityLimit", authorityLimitId.toString())))
                .flatMap(existing -> {
                    if (!binderId.equals(existing.getBinderId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "AuthorityLimit with id '" + authorityLimitId + "' does not belong to binder '" + binderId + "'"));
                    }
                    return repository.deleteById(authorityLimitId);
                });
    }

    @Override
    public Mono<AuthorityLimitDTO> getAuthorityLimitById(UUID binderId, UUID authorityLimitId) {
        return repository.findById(authorityLimitId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("AuthorityLimit", authorityLimitId.toString())))
                .flatMap(existing -> {
                    if (!binderId.equals(existing.getBinderId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "AuthorityLimit with id '" + authorityLimitId + "' does not belong to binder '" + binderId + "'"));
                    }
                    return Mono.just(mapper.toDTO(existing));
                });
    }
}

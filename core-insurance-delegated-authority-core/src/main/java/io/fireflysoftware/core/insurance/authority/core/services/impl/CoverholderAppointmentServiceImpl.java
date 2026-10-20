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


package io.fireflysoftware.core.insurance.authority.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import org.fireflyframework.web.error.exceptions.ResourceNotFoundException;
import io.fireflysoftware.core.insurance.authority.core.mappers.CoverholderAppointmentMapper;
import io.fireflysoftware.core.insurance.authority.core.services.CoverholderAppointmentService;
import io.fireflysoftware.core.insurance.authority.interfaces.dtos.CoverholderAppointmentDTO;
import io.fireflysoftware.core.insurance.authority.models.entities.CoverholderAppointment;
import io.fireflysoftware.core.insurance.authority.models.repositories.CoverholderAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class CoverholderAppointmentServiceImpl implements CoverholderAppointmentService {

    @Autowired
    private CoverholderAppointmentRepository repository;

    @Autowired
    private CoverholderAppointmentMapper mapper;

    @Override
    public Mono<PaginationResponse<CoverholderAppointmentDTO>> filterCoverholderAppointments(UUID binderId, FilterRequest<CoverholderAppointmentDTO> filterRequest) {
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new CoverholderAppointmentDTO());
        }
        filterRequest.getFilters().setBinderId(binderId);
        return FilterUtils.createFilter(CoverholderAppointment.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<CoverholderAppointmentDTO> createCoverholderAppointment(UUID binderId, CoverholderAppointmentDTO dto) {
        return Mono.just(dto)
                .doOnNext(value -> value.setBinderId(binderId))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CoverholderAppointmentDTO> updateCoverholderAppointment(UUID binderId, UUID coverholderAppointmentId, CoverholderAppointmentDTO dto) {
        return repository.findById(coverholderAppointmentId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("CoverholderAppointment", coverholderAppointmentId.toString())))
                .flatMap(existing -> {
                    if (!binderId.equals(existing.getBinderId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "CoverholderAppointment with id '" + coverholderAppointmentId + "' does not belong to binder '" + binderId + "'"));
                    }
                    mapper.updateEntityFromDto(dto, existing);
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteCoverholderAppointment(UUID binderId, UUID coverholderAppointmentId) {
        return repository.findById(coverholderAppointmentId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("CoverholderAppointment", coverholderAppointmentId.toString())))
                .flatMap(existing -> {
                    if (!binderId.equals(existing.getBinderId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "CoverholderAppointment with id '" + coverholderAppointmentId + "' does not belong to binder '" + binderId + "'"));
                    }
                    return repository.deleteById(coverholderAppointmentId);
                });
    }

    @Override
    public Mono<CoverholderAppointmentDTO> getCoverholderAppointmentById(UUID binderId, UUID coverholderAppointmentId) {
        return repository.findById(coverholderAppointmentId)
                .switchIfEmpty(Mono.error(ResourceNotFoundException.forResource("CoverholderAppointment", coverholderAppointmentId.toString())))
                .flatMap(existing -> {
                    if (!binderId.equals(existing.getBinderId())) {
                        return Mono.error(ResourceNotFoundException.withReason(
                                "RESOURCE_SCOPE_MISMATCH",
                                "CoverholderAppointment with id '" + coverholderAppointmentId + "' does not belong to binder '" + binderId + "'"));
                    }
                    return Mono.just(mapper.toDTO(existing));
                });
    }
}

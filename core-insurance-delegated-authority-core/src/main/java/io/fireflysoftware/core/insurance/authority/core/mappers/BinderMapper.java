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


package io.fireflysoftware.core.insurance.authority.core.mappers;

import io.fireflysoftware.core.insurance.authority.interfaces.dtos.BinderDTO;
import io.fireflysoftware.core.insurance.authority.models.entities.Binder;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * MapStruct mapper for Binder.
 */
@Mapper(componentModel = "spring")
public interface BinderMapper {

    /**
     * Converts the entity to its DTO.
     *
     * @param entity persisted Binder
     * @return the DTO
     */
    BinderDTO toDTO(Binder entity);

    /**
     * Converts the DTO to a new entity.
     *
     * @param dto inbound BinderDTO
     * @return the entity
     */
    Binder toEntity(BinderDTO dto);

    /**
     * Copies non-null DTO values onto an existing entity.
     * The identifier, parent link, and creation timestamp stay with the stored row.
     *
     * @param dto inbound values
     * @param entity stored entity
     */
    @Mapping(target = "binderId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BinderDTO dto, @MappingTarget Binder entity);
}

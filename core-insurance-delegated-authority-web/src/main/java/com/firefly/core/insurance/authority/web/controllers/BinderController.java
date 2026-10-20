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


package com.firefly.core.insurance.authority.web.controllers;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.insurance.authority.core.services.BinderService;
import com.firefly.core.insurance.authority.interfaces.dtos.BinderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * REST API for delegated-authority binder between a carrier and a coverholder.
 */
@RestController
@RequestMapping("/api/v1/binders")
@RequiredArgsConstructor
@Validated
@Tag(name = "Binders", description = "Delegated-authority binder between a carrier and a coverholder")
public class BinderController {

    private final BinderService binderService;

    @PostMapping("/filter")
    @Operation(summary = "Filter binders", description = "Retrieve a page of binders matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<BinderDTO>>> filterBinders(
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<BinderDTO> filterRequest) {
        return binderService.filterBinders(filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create Binder", description = "Create a Binder")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Binder created", content = @Content(schema = @Schema(implementation = BinderDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<BinderDTO>> createBinder(
            @Parameter(description = "Binder to create", required = true)
            @Valid @RequestBody BinderDTO dto) {
        return binderService.createBinder(dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{binderId}")
    @Operation(summary = "Get Binder by id", description = "Retrieve one Binder")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Binder found", content = @Content(schema = @Schema(implementation = BinderDTO.class))),
        @ApiResponse(responseCode = "404", description = "Binder not found", content = @Content)
    })
    public Mono<ResponseEntity<BinderDTO>> getBinderById(
            @Parameter(description = "Unique identifier of the Binder", required = true)
            @PathVariable UUID binderId) {
        return binderService.getBinderById(binderId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{binderId}")
    @Operation(summary = "Update Binder", description = "Update an existing Binder")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Binder updated", content = @Content(schema = @Schema(implementation = BinderDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "Binder not found", content = @Content)
    })
    public Mono<ResponseEntity<BinderDTO>> updateBinder(
            @Parameter(description = "Unique identifier of the Binder", required = true)
            @PathVariable UUID binderId,
            @Parameter(description = "Updated Binder", required = true)
            @Valid @RequestBody BinderDTO dto) {
        return binderService.updateBinder(binderId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{binderId}")
    @Operation(summary = "Delete Binder", description = "Delete a Binder")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Binder deleted"),
        @ApiResponse(responseCode = "404", description = "Binder not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteBinder(
            @Parameter(description = "Unique identifier of the Binder", required = true)
            @PathVariable UUID binderId) {
        return binderService.deleteBinder(binderId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

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


package io.fireflysoftware.core.insurance.authority.web.controllers;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import io.fireflysoftware.core.insurance.authority.core.services.CoverholderAppointmentService;
import io.fireflysoftware.core.insurance.authority.interfaces.dtos.CoverholderAppointmentDTO;
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
 * REST API for appointment of a distributor under a binder.
 */
@RestController
@RequestMapping("/api/v1/binders/{binderId}/appointments")
@RequiredArgsConstructor
@Validated
@Tag(name = "Coverholder Appointments", description = "Appointment of a distributor under a binder")
public class CoverholderAppointmentController {

    private final CoverholderAppointmentService coverholderAppointmentService;

    @PostMapping("/filter")
    @Operation(summary = "Filter coverholderAppointments", description = "Retrieve a page of coverholderAppointments matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<CoverholderAppointmentDTO>>> filterCoverholderAppointments(
            @Parameter(description = "Unique identifier of the Binder", required = true)
            @PathVariable UUID binderId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<CoverholderAppointmentDTO> filterRequest) {
        return coverholderAppointmentService.filterCoverholderAppointments(binderId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create CoverholderAppointment", description = "Create a CoverholderAppointment")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "CoverholderAppointment created", content = @Content(schema = @Schema(implementation = CoverholderAppointmentDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<CoverholderAppointmentDTO>> createCoverholderAppointment(
            @Parameter(description = "Unique identifier of the Binder", required = true)
            @PathVariable UUID binderId,
            @Parameter(description = "CoverholderAppointment to create", required = true)
            @Valid @RequestBody CoverholderAppointmentDTO dto) {
        return coverholderAppointmentService.createCoverholderAppointment(binderId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{coverholderAppointmentId}")
    @Operation(summary = "Get CoverholderAppointment by id", description = "Retrieve one CoverholderAppointment")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "CoverholderAppointment found", content = @Content(schema = @Schema(implementation = CoverholderAppointmentDTO.class))),
        @ApiResponse(responseCode = "404", description = "CoverholderAppointment not found", content = @Content)
    })
    public Mono<ResponseEntity<CoverholderAppointmentDTO>> getCoverholderAppointmentById(
            @Parameter(description = "Unique identifier of the Binder", required = true)
            @PathVariable UUID binderId,
            @Parameter(description = "Unique identifier of the CoverholderAppointment", required = true)
            @PathVariable UUID coverholderAppointmentId) {
        return coverholderAppointmentService.getCoverholderAppointmentById(binderId, coverholderAppointmentId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{coverholderAppointmentId}")
    @Operation(summary = "Update CoverholderAppointment", description = "Update an existing CoverholderAppointment")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "CoverholderAppointment updated", content = @Content(schema = @Schema(implementation = CoverholderAppointmentDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "CoverholderAppointment not found", content = @Content)
    })
    public Mono<ResponseEntity<CoverholderAppointmentDTO>> updateCoverholderAppointment(
            @Parameter(description = "Unique identifier of the Binder", required = true)
            @PathVariable UUID binderId,
            @Parameter(description = "Unique identifier of the CoverholderAppointment", required = true)
            @PathVariable UUID coverholderAppointmentId,
            @Parameter(description = "Updated CoverholderAppointment", required = true)
            @Valid @RequestBody CoverholderAppointmentDTO dto) {
        return coverholderAppointmentService.updateCoverholderAppointment(binderId, coverholderAppointmentId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{coverholderAppointmentId}")
    @Operation(summary = "Delete CoverholderAppointment", description = "Delete a CoverholderAppointment")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "CoverholderAppointment deleted"),
        @ApiResponse(responseCode = "404", description = "CoverholderAppointment not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteCoverholderAppointment(
            @Parameter(description = "Unique identifier of the Binder", required = true)
            @PathVariable UUID binderId,
            @Parameter(description = "Unique identifier of the CoverholderAppointment", required = true)
            @PathVariable UUID coverholderAppointmentId) {
        return coverholderAppointmentService.deleteCoverholderAppointment(binderId, coverholderAppointmentId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

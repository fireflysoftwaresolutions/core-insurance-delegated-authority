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
import com.firefly.core.insurance.authority.core.services.AuthorityLimitService;
import com.firefly.core.insurance.authority.interfaces.dtos.AuthorityLimitDTO;
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
 * REST API for limit inside a binder: sum insured, premium, territory, or line.
 */
@RestController
@RequestMapping("/api/v1/binders/{binderId}/limits")
@RequiredArgsConstructor
@Validated
@Tag(name = "Authority Limits", description = "Limit inside a binder: sum insured, premium, territory, or line")
public class AuthorityLimitController {

    private final AuthorityLimitService authorityLimitService;

    @PostMapping("/filter")
    @Operation(summary = "Filter authorityLimits", description = "Retrieve a page of authorityLimits matching the filter")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of matching records"),
        @ApiResponse(responseCode = "400", description = "Invalid filter request", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<AuthorityLimitDTO>>> filterAuthorityLimits(
            @Parameter(description = "Unique identifier of the Binder", required = true)
            @PathVariable UUID binderId,
            @Parameter(description = "Filter criteria", required = true)
            @Valid @RequestBody FilterRequest<AuthorityLimitDTO> filterRequest) {
        return authorityLimitService.filterAuthorityLimits(binderId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create AuthorityLimit", description = "Create a AuthorityLimit")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "AuthorityLimit created", content = @Content(schema = @Schema(implementation = AuthorityLimitDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    public Mono<ResponseEntity<AuthorityLimitDTO>> createAuthorityLimit(
            @Parameter(description = "Unique identifier of the Binder", required = true)
            @PathVariable UUID binderId,
            @Parameter(description = "AuthorityLimit to create", required = true)
            @Valid @RequestBody AuthorityLimitDTO dto) {
        return authorityLimitService.createAuthorityLimit(binderId, dto)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @GetMapping("/{authorityLimitId}")
    @Operation(summary = "Get AuthorityLimit by id", description = "Retrieve one AuthorityLimit")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "AuthorityLimit found", content = @Content(schema = @Schema(implementation = AuthorityLimitDTO.class))),
        @ApiResponse(responseCode = "404", description = "AuthorityLimit not found", content = @Content)
    })
    public Mono<ResponseEntity<AuthorityLimitDTO>> getAuthorityLimitById(
            @Parameter(description = "Unique identifier of the Binder", required = true)
            @PathVariable UUID binderId,
            @Parameter(description = "Unique identifier of the AuthorityLimit", required = true)
            @PathVariable UUID authorityLimitId) {
        return authorityLimitService.getAuthorityLimitById(binderId, authorityLimitId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{authorityLimitId}")
    @Operation(summary = "Update AuthorityLimit", description = "Update an existing AuthorityLimit")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "AuthorityLimit updated", content = @Content(schema = @Schema(implementation = AuthorityLimitDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
        @ApiResponse(responseCode = "404", description = "AuthorityLimit not found", content = @Content)
    })
    public Mono<ResponseEntity<AuthorityLimitDTO>> updateAuthorityLimit(
            @Parameter(description = "Unique identifier of the Binder", required = true)
            @PathVariable UUID binderId,
            @Parameter(description = "Unique identifier of the AuthorityLimit", required = true)
            @PathVariable UUID authorityLimitId,
            @Parameter(description = "Updated AuthorityLimit", required = true)
            @Valid @RequestBody AuthorityLimitDTO dto) {
        return authorityLimitService.updateAuthorityLimit(binderId, authorityLimitId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{authorityLimitId}")
    @Operation(summary = "Delete AuthorityLimit", description = "Delete a AuthorityLimit")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "AuthorityLimit deleted"),
        @ApiResponse(responseCode = "404", description = "AuthorityLimit not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteAuthorityLimit(
            @Parameter(description = "Unique identifier of the Binder", required = true)
            @PathVariable UUID binderId,
            @Parameter(description = "Unique identifier of the AuthorityLimit", required = true)
            @PathVariable UUID authorityLimitId) {
        return authorityLimitService.deleteAuthorityLimit(binderId, authorityLimitId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

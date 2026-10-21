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

import io.fireflysoftware.core.insurance.authority.interfaces.enums.BinderStatus;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Allowed moves for a binder. A draft is the only status that can be removed.
 */
final class BinderLifecycle {

    private static final Map<BinderStatus, Set<BinderStatus>> ALLOWED = Map.of(
            BinderStatus.DRAFT, EnumSet.of(BinderStatus.ACTIVE),
            BinderStatus.ACTIVE, EnumSet.of(BinderStatus.SUSPENDED, BinderStatus.EXPIRED),
            BinderStatus.SUSPENDED, EnumSet.of(BinderStatus.ACTIVE, BinderStatus.EXPIRED)
    );

    private BinderLifecycle() {
    }

    static boolean canTransition(BinderStatus from, BinderStatus to) {
        if (from == to) {
            return true;
        }
        Set<BinderStatus> next = ALLOWED.get(from);
        return next != null && next.contains(to);
    }
}

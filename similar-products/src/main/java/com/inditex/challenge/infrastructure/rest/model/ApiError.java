package com.inditex.challenge.infrastructure.rest.model;

import java.time.Instant;

public record ApiError(Instant timestamp, int status, String error) {
}

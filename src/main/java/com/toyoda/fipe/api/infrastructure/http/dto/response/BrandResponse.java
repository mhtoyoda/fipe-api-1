package com.toyoda.fipe.api.infrastructure.http.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BrandResponse(@JsonProperty("codigo") Long code, @JsonProperty("nome") String name) {
}

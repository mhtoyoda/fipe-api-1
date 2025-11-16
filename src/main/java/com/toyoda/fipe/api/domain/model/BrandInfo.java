package com.toyoda.fipe.api.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BrandInfo(
    @JsonProperty("codigo") Long code,
    @JsonProperty("nome") String name
) {
}

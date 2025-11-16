package com.toyoda.fipe.api.domain.model;

public record VehicleInfo(String value,
                          String brand,
                          String model,
                          Integer yearodel,
                          String combustible,
                          String codeFipe,
                          String monthReference,
                          Integer type,
                          String acronym) {
}

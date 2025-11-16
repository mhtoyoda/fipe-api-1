package com.toyoda.fipe.api.adapter.in.mapper;

import com.toyoda.fipe.api.domain.model.BrandInfo;
import com.toyoda.fipe.api.infrastructure.http.dto.response.BrandResponse;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public BrandResponse toBrandResponse(final BrandInfo brandInfo) {
        return new BrandResponse(brandInfo.code(), brandInfo.name());
    }
}

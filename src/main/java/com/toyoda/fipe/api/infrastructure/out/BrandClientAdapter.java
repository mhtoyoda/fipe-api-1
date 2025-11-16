package com.toyoda.fipe.api.infrastructure.out;

import com.toyoda.fipe.api.application.port.output.http.BrandClientPort;
import com.toyoda.fipe.api.domain.model.BrandInfo;
import com.toyoda.fipe.api.infrastructure.http.client.FipeClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrandClientAdapter implements BrandClientPort {

    private final FipeClient fipeClient;

    public BrandClientAdapter(FipeClient fipeClient) {
        this.fipeClient = fipeClient;
    }

    @Override
    public List<BrandInfo> getBrands() {
        return fipeClient.getMarcas();
    }
}

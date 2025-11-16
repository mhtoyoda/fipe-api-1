package com.toyoda.fipe.api.infrastructure.http.client;

import com.toyoda.fipe.api.domain.model.BrandInfo;
import com.toyoda.fipe.api.domain.model.ModelInfos;
import com.toyoda.fipe.api.domain.model.VehicleInfo;
import com.toyoda.fipe.api.domain.model.YearInfo;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "fipe-client",
    url = "${service.api.url.fipe}",
    configuration = com.toyoda.fipe.api.config.FeignClientConfig.class
)
public interface FipeClient {

    @GetMapping("/marcas")
    List<BrandInfo> getBrands();

    @GetMapping("/marcas/{marcaId}/modelos")
    ModelInfos getModels(@PathVariable("marcaId") String marcaId);

    @GetMapping("/marcas/{marcaId}/modelos/{modeloId}/anos")
    List<YearInfo> getYears(
        @PathVariable("marcaId") String marcaId,
        @PathVariable("modeloId") String modeloId
    );

    @GetMapping("/marcas/{marcaId}/modelos/{modeloId}/anos/{anoId}")
    VehicleInfo getVehicle(
        @PathVariable("marcaId") String brandId,
        @PathVariable("modeloId") String modelId,
        @PathVariable("anoId") String yeaarId
    );

}


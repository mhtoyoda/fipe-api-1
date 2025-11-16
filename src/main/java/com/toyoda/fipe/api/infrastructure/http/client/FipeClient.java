package com.toyoda.fipe.api.infrastructure.http.client;

import com.toyoda.fipe.api.domain.model.BrandInfo;
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
    List<BrandInfo> getMarcas();

    @GetMapping("/marcas/{marcaId}/modelos")
    FipeModelos getModelos(@PathVariable("marcaId") String marcaId);

    @GetMapping("/marcas/{marcaId}/modelos/{modeloId}/anos")
    List<FipeAno> getAnos(
        @PathVariable("marcaId") String marcaId,
        @PathVariable("modeloId") String modeloId
    );

    @GetMapping("/marcas/{marcaId}/modelos/{modeloId}/anos/{anoId}")
    FipeVeiculo getVeiculo(
        @PathVariable("marcaId") String marcaId,
        @PathVariable("modeloId") String modeloId,
        @PathVariable("anoId") String anoId
    );

    record FipeMarca(String codigo, String nome) {}

    record FipeModelos(List<FipeModelo> modelos, List<FipeAno> anos) {}

    record FipeModelo(String codigo, String nome) {}

    record FipeAno(String codigo, String nome) {}

    record FipeVeiculo(
        String Valor,
        String Marca,
        String Modelo,
        Integer AnoModelo,
        String Combustivel,
        String CodigoFipe,
        String MesReferencia,
        Integer TipoVeiculo,
        String SiglaCombustivel
    ) {}
}


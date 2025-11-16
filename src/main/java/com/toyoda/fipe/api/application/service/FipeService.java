package com.toyoda.fipe.api.application.service;

import com.toyoda.fipe.api.domain.model.BrandInfo;
import com.toyoda.fipe.api.infrastructure.http.client.FipeClient;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FipeService {

    private static final Logger logger = LoggerFactory.getLogger(FipeService.class);

    private final FipeClient fipeClient;

    public FipeService(FipeClient fipeClient) {
        this.fipeClient = fipeClient;
    }

    public List<BrandInfo> buscarTodasMarcas() {
        logger.info("Buscando todas as marcas de veículos");
        return fipeClient.getMarcas();
    }

    public FipeClient.FipeModelos buscarModelosPorMarca(String marcaId) {
        logger.info("Buscando modelos da marca: {}", marcaId);
        return fipeClient.getModelos(marcaId);
    }

    public List<FipeClient.FipeAno> buscarAnosPorModelo(String marcaId, String modeloId) {
        logger.info("Buscando anos do modelo: {} da marca: {}", modeloId, marcaId);
        return fipeClient.getAnos(marcaId, modeloId);
    }

    public FipeClient.FipeVeiculo buscarVeiculo(String marcaId, String modeloId, String anoId) {
        logger.info("Buscando veículo - Marca: {}, Modelo: {}, Ano: {}", marcaId, modeloId, anoId);
        return fipeClient.getVeiculo(marcaId, modeloId, anoId);
    }
}



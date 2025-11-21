package com.toyoda.fipe.api.application.usecase;


import com.toyoda.fipe.api.application.port.input.FullImportBrandsUseCase;
import com.toyoda.fipe.api.application.port.output.cache.CachePort;
import com.toyoda.fipe.api.application.port.output.http.BrandClientPort;
import com.toyoda.fipe.api.application.port.output.messaging.MessageProducerPort;
import com.toyoda.fipe.api.domain.model.BrandInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FullImportBrandsUseCaseImpl implements FullImportBrandsUseCase {

    private final static String KEY_BRANDS = "KEY_BRANDS";
    private final MessageProducerPort messageProducerPort;
    private final BrandClientPort clientPort;
    private final CachePort cachePort;

    public FullImportBrandsUseCaseImpl(MessageProducerPort messageProducerPort, BrandClientPort clientPort, CachePort cachePort) {
        this.messageProducerPort = messageProducerPort;
        this.clientPort = clientPort;
        this.cachePort = cachePort;
    }

    @Override
    public void execute() {
        List<BrandInfo> brands = (List<BrandInfo>) cachePort.get(KEY_BRANDS).orElseGet(this::getBrands);
        messageProducerPort.sendMessage(brands);
    }

    private Object getBrands() {
        List<BrandInfo> brandInfos = clientPort.getBrands();
        cachePort.save(KEY_BRANDS, brandInfos);
        return brandInfos;
    }
}

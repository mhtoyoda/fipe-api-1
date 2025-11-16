package com.toyoda.fipe.api.application.usecase;


import com.toyoda.fipe.api.application.port.input.FullImportBrandsUseCase;
import com.toyoda.fipe.api.application.port.output.http.BrandClientPort;
import com.toyoda.fipe.api.application.port.output.messaging.MessageProducerPort;
import com.toyoda.fipe.api.domain.model.BrandInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FullImportBrandsUseCaseImpl implements FullImportBrandsUseCase {

    private final MessageProducerPort messageProducerPort;
    private final BrandClientPort clientPort;

    public FullImportBrandsUseCaseImpl(MessageProducerPort messageProducerPort, BrandClientPort clientPort) {
        this.messageProducerPort = messageProducerPort;
        this.clientPort = clientPort;
    }

    @Override
    public void execute() {
        List<BrandInfo> brands = clientPort.getBrands();
        messageProducerPort.sendMessage(brands);
    }
}

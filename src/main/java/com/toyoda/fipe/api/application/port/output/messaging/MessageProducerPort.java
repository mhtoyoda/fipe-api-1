package com.toyoda.fipe.api.application.port.output.messaging;

public interface MessageProducerPort {
    void sendMessage(final Object object);
}

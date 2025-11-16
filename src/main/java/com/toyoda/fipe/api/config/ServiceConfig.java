package com.toyoda.fipe.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.toyoda.fipe.api.adapter",
    "com.toyoda.fipe.api.application",
    "com.toyoda.fipe.api.domain",
    "com.toyoda.fipe.api.config"
})
public class ServiceConfig {
}
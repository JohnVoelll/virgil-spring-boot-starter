package com.indeed.virgil.spring.boot.starter.config;

import com.indeed.virgil.spring.boot.starter.services.DefaultMessageConverter;
import com.indeed.virgil.spring.boot.starter.services.IMessageConverter;
import com.indeed.virgil.spring.boot.starter.services.MessageConverterService;
import com.indeed.virgil.spring.boot.starter.services.MessageOperator;
import com.indeed.virgil.spring.boot.starter.services.RabbitMqConnectionService;
import com.indeed.virgil.spring.boot.starter.util.VirgilMessageUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(VirgilPropertyConfig.class)
class VirgilConfig {

    private final VirgilPropertyConfig virgilPropertyConfig;

    VirgilConfig(VirgilPropertyConfig virgilPropertyConfig) {
        this.virgilPropertyConfig = virgilPropertyConfig;
    }

    @Bean
    RabbitMqConnectionService rabbitMqConnectionService() {
        return new RabbitMqConnectionService(virgilPropertyConfig);
    }

    @Bean
    MessageOperator messageOperator(
        final RabbitMqConnectionService rabbitMqConnectionService,
        final MessageConverterService messageConverterService
    ) {
        return new MessageOperator(virgilPropertyConfig, rabbitMqConnectionService, messageConverterService);
    }

    @Bean
    MessageConverterService messageConverterService(
        final IMessageConverter messageConverter
    ) {
        return new MessageConverterService(messageConverter);
    }

    @Bean
    @ConditionalOnMissingBean
    IMessageConverter messageConverter(final VirgilMessageUtils virgilMessageUtils) {
        return new DefaultMessageConverter(virgilMessageUtils);
    }

    @Bean
    VirgilMessageUtils virgilMessageUtils() {
        return new VirgilMessageUtils();
    }
}

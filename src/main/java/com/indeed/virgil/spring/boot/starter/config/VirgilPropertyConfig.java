package com.indeed.virgil.spring.boot.starter.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@ConfigurationProperties(prefix = "virgil")
@Validated
public class VirgilPropertyConfig {
    private static final Logger LOG = LoggerFactory.getLogger(VirgilPropertyConfig.class);

    private final Map<String, QueueProperties> queues;

    private final Map<String, BinderProperties> binders;

    @ConstructorBinding
    public VirgilPropertyConfig(
        final Map<String, QueueProperties> queues,
        final Map<String, BinderProperties> binders
    ) {
        this.queues = queues;
        this.binders = binders;
    }

    public Map<String, QueueProperties> getQueues() {
        return queues;
    }

    public Map<String, BinderProperties> getBinders() {
        return binders;
    }

    @Nullable
    public QueueProperties getQueueProperties(final String name) {
        final QueueProperties queueProperties = getQueues().getOrDefault(name, null);
        if (queueProperties != null) {
            final BinderProperties readBinderProperties = getBinderProperties(queueProperties.getReadBinderName());
            if (readBinderProperties != null) {
                queueProperties.setReadBinderProperties(readBinderProperties);
            }

            final BinderProperties republishBinderProperties = getBinderProperties(queueProperties.getReadBinderName());
            if (republishBinderProperties != null) {
                queueProperties.setRepublishBinderProperties(republishBinderProperties);
            }
        }

        return queueProperties;
    }

    @Nullable
    public BinderProperties getBinderProperties(final String name) {
        return getBinders().getOrDefault(name, null);
    }

    public QueueProperties getDefaultQueue() {
        final String firstQueueName = getQueueNames().get(0);

        return Objects.requireNonNull(getQueueProperties(firstQueueName));
    }

    /**
     * Returns a list of queue keys from the config
     * @return
     */
    public List<String> getQueueNames() {
        return new ArrayList<>(getQueues().keySet());
    }

    public static final class QueueProperties {
        private String readName;
        private String readBinderName;
        private BinderProperties readBinderProperties;
        private String republishName;
        private String republishBindingRoutingKey = "#";
        private String republishBinderName;
        private BinderProperties republishBinderProperties;

        @ConstructorBinding
        public QueueProperties(
            final String readName,
            final String readBinderName,
            final BinderProperties readBinderProperties,
            final String republishName,
            final String republishBindingRoutingKey,
            final String republishBinderName,
            final BinderProperties republishBinderProperties
        ) {
            this.readName = readName;
            this.readBinderName = readBinderName;
            this.readBinderProperties = readBinderProperties;
            this.republishName = republishName;
            this.republishBindingRoutingKey = republishBindingRoutingKey;
            this.republishBinderName = republishBinderName;
            this.republishBinderProperties = republishBinderProperties;
        }

        public String getReadName() {
            return readName;
        }

        public String getReadBinderName() {
            return readBinderName;
        }

        public BinderProperties getReadBinderProperties() {
            return readBinderProperties;
        }

        protected void setReadBinderProperties(final BinderProperties readBinderProperties) {
            this.readBinderProperties = readBinderProperties;
        }

        public String getRepublishName() {
            return republishName;
        }

        public String getRepublishBindingRoutingKey() {
            return republishBindingRoutingKey;
        }

        public String getRepublishBinderName() {
            return republishBinderName;
        }

        public BinderProperties getRepublishBinderProperties() {
            return republishBinderProperties;
        }

        protected void setRepublishBinderProperties(final BinderProperties readBinderProperties) {
            this.readBinderProperties = readBinderProperties;
        }
    }

    public static final class BinderProperties {
        private final String name;
        private final String type;
        private final RabbitProperties rabbitProperties;

        @ConstructorBinding
        public BinderProperties(
            final String name,
            final String type,
            final RabbitProperties rabbitProperties
        ) {
            this.name = name;
            this.type = type;
            this.rabbitProperties = rabbitProperties;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public RabbitProperties getRabbitProperties() {
            return rabbitProperties;
        }
    }
}

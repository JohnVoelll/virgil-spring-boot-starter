package com.indeed.virgil.spring.boot.starter.config;

import com.indeed.virgil.spring.boot.starter.util.EndpointConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class TestVirgilPropertiesEnvironmentPostProcessor {

    VirgilPropertiesEnvironmentPostProcessor instance;

    @BeforeEach
    void setup() {
        instance = new VirgilPropertiesEnvironmentPostProcessor();
    }

    @Test
    void shouldLoadAllEndpointsWithIVirgilEndpoint() {
        //Act
        final String[][] result = (String[][]) ReflectionTestUtils.getField(instance, "DEFAULT_ENDPOINTS");

        //Assert
        assertThat(result.length).isEqualTo(6);
    }

    @Test
    void shouldLoadPublishMessageEndpoint() {
        //Act
        final String[][] results = (String[][]) ReflectionTestUtils.getField(instance, "DEFAULT_ENDPOINTS");

        //Assert
        assertEndpointProperties(results, EndpointConstants.PUBLISH_MESSAGE_ENDPOINT_ID, EndpointConstants.ENDPOINT_DEFAULT_PATH_MAPPING + EndpointConstants.PUBLISH_MESSAGE_ENDPOINT_ID);
    }

    @Test
    void shouldLoadDropAllMessagesEndpoint() {
        //Act
        final String[][] results = (String[][]) ReflectionTestUtils.getField(instance, "DEFAULT_ENDPOINTS");

        //Assert
        assertEndpointProperties(results, EndpointConstants.DROP_ALL_MESSAGES_ENDPOINT_ID, EndpointConstants.ENDPOINT_DEFAULT_PATH_MAPPING + EndpointConstants.DROP_ALL_MESSAGES_ENDPOINT_ID);
    }

    @Test
    void shouldLoadDropMessageEndpoint() {
        //Act
        final String[][] results = (String[][]) ReflectionTestUtils.getField(instance, "DEFAULT_ENDPOINTS");

        //Assert
        assertEndpointProperties(results, EndpointConstants.DROP_MESSAGE_ENDPOINT_ID, EndpointConstants.ENDPOINT_DEFAULT_PATH_MAPPING + EndpointConstants.DROP_MESSAGE_ENDPOINT_ID);
    }

    @Test
    void shouldLoadGetDlqMessagesEndpoint() {
        //Act
        final String[][] results = (String[][]) ReflectionTestUtils.getField(instance, "DEFAULT_ENDPOINTS");

        //Assert
        assertEndpointProperties(results, EndpointConstants.GET_DLQ_MESSAGES_ENDPOINT_ID, EndpointConstants.ENDPOINT_DEFAULT_PATH_MAPPING + EndpointConstants.GET_DLQ_MESSAGES_ENDPOINT_ID);
    }

    @Test
    void shouldLoadGetQueueSizeEndpoint() {
        //Act
        final String[][] results = (String[][]) ReflectionTestUtils.getField(instance, "DEFAULT_ENDPOINTS");

        //Assert
        assertEndpointProperties(results, EndpointConstants.GET_QUEUE_SIZE_ENDPOINT_ID, EndpointConstants.ENDPOINT_DEFAULT_PATH_MAPPING + EndpointConstants.GET_QUEUE_SIZE_ENDPOINT_ID);
    }

    @Nested
    class TestMappingProperty {
        final ArgumentCaptor<MapPropertySource> valueCapture = ArgumentCaptor.forClass(MapPropertySource.class);

        @BeforeEach
        void setup() {
            //Arrange
            final SpringApplication mockSpringApplication = Mockito.mock(SpringApplication.class);
            final ConfigurableEnvironment mockConfigurableEnvironment = Mockito.mock(ConfigurableEnvironment.class);
            final MutablePropertySources mockPropertySources = Mockito.mock(MutablePropertySources.class);

            doNothing().when(mockPropertySources).addFirst(valueCapture.capture());
            when(mockConfigurableEnvironment.getPropertySources()).thenReturn(mockPropertySources);

            //Act
            instance.postProcessEnvironment(mockConfigurableEnvironment, mockSpringApplication);
        }

        @Test
        void shouldAddPathMappingPropertyForPublishMessageEndpoint() {
            //Assert
            final MapPropertySource result = valueCapture.getValue();
            final String mappingPropertyValue = (String) result.getProperty("management.endpoints.web.path-mapping.publish-message");

            assertThat(mappingPropertyValue).isEqualTo(EndpointConstants.ENDPOINT_DEFAULT_PATH_MAPPING + EndpointConstants.PUBLISH_MESSAGE_ENDPOINT_ID);
        }

        @Test
        void shouldAddPathMappingPropertyForDropMessageEndpoint() {
            //Assert
            final MapPropertySource result = valueCapture.getValue();
            final String mappingPropertyValue = (String) result.getProperty("management.endpoints.web.path-mapping.drop-message");

            assertThat(mappingPropertyValue).isEqualTo(EndpointConstants.ENDPOINT_DEFAULT_PATH_MAPPING + EndpointConstants.DROP_MESSAGE_ENDPOINT_ID);
        }

        @Test
        void shouldAddPathMappingPropertyForDropAllMessagesEndpoint() {
            //Assert
            final MapPropertySource result = valueCapture.getValue();
            final String mappingPropertyValue = (String) result.getProperty("management.endpoints.web.path-mapping.drop-all-messages");

            assertThat(mappingPropertyValue).isEqualTo(EndpointConstants.ENDPOINT_DEFAULT_PATH_MAPPING + EndpointConstants.DROP_ALL_MESSAGES_ENDPOINT_ID);
        }

        @Test
        void shouldAddPathMappingPropertyForGetDlqMessagesEndpoint() {
            //Assert
            final MapPropertySource result = valueCapture.getValue();
            final String mappingPropertyValue = (String) result.getProperty("management.endpoints.web.path-mapping.get-dlq-messages");

            assertThat(mappingPropertyValue).isEqualTo(EndpointConstants.ENDPOINT_DEFAULT_PATH_MAPPING + EndpointConstants.GET_DLQ_MESSAGES_ENDPOINT_ID);
        }

        @Test
        void shouldAddPathMappingPropertyForGetQueueSizeEndpoint() {
            //Assert
            final MapPropertySource result = valueCapture.getValue();
            final String mappingPropertyValue = (String) result.getProperty("management.endpoints.web.path-mapping.get-queue-size");

            assertThat(mappingPropertyValue).isEqualTo(EndpointConstants.ENDPOINT_DEFAULT_PATH_MAPPING + EndpointConstants.GET_QUEUE_SIZE_ENDPOINT_ID);
        }

        @Test
        void shouldAddPathMappingPropertyForGetQueueEndpoint() {
            //Assert
            final MapPropertySource result = valueCapture.getValue();
            final String mappingPropertyValue = (String) result.getProperty("management.endpoints.web.path-mapping.get-queues");

            assertThat(mappingPropertyValue).isEqualTo(EndpointConstants.ENDPOINT_DEFAULT_PATH_MAPPING + EndpointConstants.GET_QUEUES_ENDPOINT_ID);
        }
    }

    @Test
    void shouldAppendExposedEndpoints() {
        //Arrange
        final SpringApplication mockSpringApplication = Mockito.mock(SpringApplication.class);
        final ConfigurableEnvironment mockConfigurableEnvironment = Mockito.mock(ConfigurableEnvironment.class);
        final MutablePropertySources mockPropertySources = Mockito.mock(MutablePropertySources.class);
        final ArgumentCaptor<MapPropertySource> valueCapture = ArgumentCaptor.forClass(MapPropertySource.class);

        doNothing().when(mockPropertySources).addFirst(valueCapture.capture());
        when(mockConfigurableEnvironment.getPropertySources()).thenReturn(mockPropertySources);

        final List<String> expectedItems = Arrays.asList(
            "drop-all-messages",
            "get-dlq-messages",
            "publish-message",
            "get-queues",
            "drop-message",
            "get-queue-size"
        );

        //Act
        instance.postProcessEnvironment(mockConfigurableEnvironment, mockSpringApplication);

        //Assert
        final MapPropertySource result = valueCapture.getValue();
        final String mappingPropertyValue = (String) result.getProperty("management.endpoints.web.exposure.include");

        assertThat(mappingPropertyValue).isEqualTo(String.join(",", expectedItems));
    }

    @Test
    void shouldAppendProbedEndpoints() {
        //Arrange
        final SpringApplication mockSpringApplication = Mockito.mock(SpringApplication.class);
        final ConfigurableEnvironment mockConfigurableEnvironment = Mockito.mock(ConfigurableEnvironment.class);
        final MutablePropertySources mockPropertySources = Mockito.mock(MutablePropertySources.class);
        final ArgumentCaptor<MapPropertySource> valueCapture = ArgumentCaptor.forClass(MapPropertySource.class);

        doNothing().when(mockPropertySources).addFirst(valueCapture.capture());
        when(mockConfigurableEnvironment.getPropertySources()).thenReturn(mockPropertySources);

        final List<String> expectedItems = Arrays.asList(
            "drop-message:virgil/drop-message",
            "get-queue-size:virgil/get-queue-size",
            "publish-message:virgil/publish-message",
            "drop-all-messages:virgil/drop-all-messages",
            "get-dlq-messages:virgil/get-dlq-messages",
            "get-queues:virgil/get-queues"
        );

        //Act
        instance.postProcessEnvironment(mockConfigurableEnvironment, mockSpringApplication);

        //Assert
        final MapPropertySource result = valueCapture.getValue();
        final String mappingPropertyValue = (String) result.getProperty("spring.boot.admin.probed-endpoints");

        assertThat(mappingPropertyValue).isEqualTo(String.join(",", expectedItems));
    }

    @Test
    void shouldAddResourceLocationForVirgil() {
        //Arrange
        final SpringApplication mockSpringApplication = Mockito.mock(SpringApplication.class);
        final ConfigurableEnvironment mockConfigurableEnvironment = Mockito.mock(ConfigurableEnvironment.class);
        final MutablePropertySources mockPropertySources = Mockito.mock(MutablePropertySources.class);
        final ArgumentCaptor<MapPropertySource> valueCapture = ArgumentCaptor.forClass(MapPropertySource.class);

        doNothing().when(mockPropertySources).addFirst(valueCapture.capture());
        when(mockConfigurableEnvironment.getPropertySources()).thenReturn(mockPropertySources);

        //Act
        instance.postProcessEnvironment(mockConfigurableEnvironment, mockSpringApplication);

        //Assert
        final MapPropertySource result = valueCapture.getValue();
        final String mappingPropertyValue = (String) result.getProperty("spring.boot.admin.ui.extension-resource-locations");

        assertThat(mappingPropertyValue).isEqualTo("classpath:META-INF/extensions/custom/");
    }

    void assertEndpointProperties(final String[][] results, final String expectedId, final String expectedPath) {
        boolean isFound = false;
        String[] foundItem = new String[0];
        //loop thru and find expectedId
        for (final String[] item : results) {
            if (item[0].equals(expectedId)) {
                foundItem = item;
                isFound = true;
            }
        }

        if (isFound) {
            assertThat(foundItem[0]).as("Expected endpointId does not exist.").isEqualTo(expectedId);
            assertThat(foundItem[1]).as("Expected endpointPath does not match actual.").isEqualTo(expectedPath);
            return;
        }

        //if not found, fail
        fail(String.format("Expected endpoint not found. [ExpectedEndpointId: %s]", expectedId));
    }
}

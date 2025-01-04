package com.indeed.virgil.spring.boot.starter.endpoints;

import com.indeed.virgil.spring.boot.starter.services.MessageOperator;
import com.indeed.virgil.spring.boot.starter.util.EndpointConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.indeed.virgil.spring.boot.starter.util.EndpointConstants.ENDPOINT_DEFAULT_PATH_MAPPING;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TestPublishMessageEndpoint {

    @Mock
    private MessageOperator messageOperator;

    private PublishMessageEndpoint publishMessageEndpoint;

    @BeforeEach
    void setup() {
        publishMessageEndpoint = new PublishMessageEndpoint(messageOperator);
    }

    @Test
    void shouldImplementIVirgilEndpoint() {
        //Act
        final Class<?> c = PublishMessageEndpoint.class;

        //Assert
        assertThat(IVirgilEndpoint.class.isAssignableFrom(c)).isTrue();
    }

    @Test
    void testGetEndpointId_shouldReturnExpectedEndpointId() {
        //Arrange

        //Act
        final String result = publishMessageEndpoint.getEndpointId();

        //Assert
        assertThat(result).isEqualTo(EndpointConstants.PUBLISH_MESSAGE_ENDPOINT_ID);
    }

    @Test
    void testGetEndpointPath_shouldReturnExpectedEndpointPath() {
        //Arrange

        //Act
        final String result = publishMessageEndpoint.getEndpointPath();

        //Assert
        assertThat(result).isEqualTo(ENDPOINT_DEFAULT_PATH_MAPPING + EndpointConstants.PUBLISH_MESSAGE_ENDPOINT_ID);
    }
}

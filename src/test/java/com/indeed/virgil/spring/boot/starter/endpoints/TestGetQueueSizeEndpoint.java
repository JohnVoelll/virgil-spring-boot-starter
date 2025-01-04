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
public class TestGetQueueSizeEndpoint {

    @Mock
    private MessageOperator messageOperator;

    private GetQueueSizeEndpoint getQueueSizeEndpoint;

    @BeforeEach
    void setup() {
        getQueueSizeEndpoint = new GetQueueSizeEndpoint(messageOperator);
    }

    @Test
    void shouldImplementIVirgilEndpoint() {
        //Act
        final Class<?> c = GetQueueSizeEndpoint.class;

        //Assert
        assertThat(IVirgilEndpoint.class.isAssignableFrom(c)).isTrue();
    }

    @Test
    void testGetEndpointId_shouldReturnExpectedEndpointId() {
        //Arrange

        //Act
        final String result = getQueueSizeEndpoint.getEndpointId();

        //Assert
        assertThat(result).isEqualTo(EndpointConstants.GET_QUEUE_SIZE_ENDPOINT_ID);
    }

    @Test
    void testGetEndpointPath_shouldReturnExpectedEndpointPath() {
        //Arrange

        //Act
        final String result = getQueueSizeEndpoint.getEndpointPath();

        //Assert
        assertThat(result).isEqualTo(ENDPOINT_DEFAULT_PATH_MAPPING + EndpointConstants.GET_QUEUE_SIZE_ENDPOINT_ID);
    }
}

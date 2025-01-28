package com.kalk.security.server.security.openfga;

import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.configuration.ClientConfiguration;
import dev.openfga.sdk.errors.FgaInvalidParameterException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("openfga")
public class OpenFgaConfiguration {

    @Bean
    public OpenFgaClient openFgaClient() throws FgaInvalidParameterException {
        var config = new ClientConfiguration()
                .apiUrl("http://localhost:8080") // If not specified, will default to "https://localhost:8080"
                .storeId("01JJKKY1XC1DXF3NZV3YR2B084") // Not required when calling createStore() or listStores()
                .authorizationModelId("01JJNVSMNSPZJAMTTPGWSGV0JF"); // Optional, can be overridden per request

        return new OpenFgaClient(config);
    }
}

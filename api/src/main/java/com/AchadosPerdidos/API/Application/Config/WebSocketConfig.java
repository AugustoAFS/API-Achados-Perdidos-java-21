package com.AchadosPerdidos.API.Application.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.lang.NonNull;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${WS_ENDPOINT:/ws}")
    private String endpoint;

    @Value("${WS_ALLOWED_ORIGINS:*}")
    private String allowedOrigins;

    @Value("${WS_APP_DEST_PREFIX:/app}")
    private String appDestinationPrefix;

    @Value("${WS_BROKER_PREFIX:/topic}")
    private String brokerPrefix;

    @Value("${WS_ENABLE_SOCKJS:true}")
    private boolean enableSockJs;

    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry config) {
        config.enableSimpleBroker(brokerPrefix);
        config.setApplicationDestinationPrefixes(appDestinationPrefix);
    }

    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        var endpointRegistration = registry.addEndpoint(endpoint)
                .setAllowedOriginPatterns(resolveAllowedOrigins());

        if (enableSockJs) {
            endpointRegistration.withSockJS();
        }
    }

    @NonNull
    private String[] resolveAllowedOrigins() {
        if (!StringUtils.hasText(allowedOrigins)) {
            return new String[]{"*"};
        }
        return StringUtils.commaDelimitedListToStringArray(allowedOrigins);
    }
}

/**
 * Author: Anvar Olimov
 * User:user
 * Date:8/6/2024
 * Time:5:08 PM
 */


package dental.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig  implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/dental")
                .setAllowedOrigins("http://localhost:3000",
                        "https://185.177.59.64",
                        "http://185.177.59.64",
                        "https://185.177.59.64:3000",
                        "http://185.177.59.64:3000");
        registry.addEndpoint("/dental")
                .setAllowedOrigins("http://localhost:3000",
                        "https://185.177.59.64",
                        "http://185.177.59.64",
                        "https://185.177.59.64:3000",
                        "http://185.177.59.64:3000")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/topic");
    }
}


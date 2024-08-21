package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity security) {
        security.authorizeExchange(authorizeExchangeSpec -> {
                    authorizeExchangeSpec.pathMatchers("/", "/images/**").permitAll();
                    authorizeExchangeSpec.anyExchange().authenticated();
                })
                .oauth2Login(Customizer.withDefaults()).
                oauth2ResourceServer(customizer -> customizer.jwt(Customizer.withDefaults()));
        return security.build();
    }
}

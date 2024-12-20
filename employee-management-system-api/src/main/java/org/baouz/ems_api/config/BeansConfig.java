package org.baouz.ems_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpHeaders.*;

@Configuration
public class BeansConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return new ApplicationAuditorAware();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE"));
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        config.setAllowedHeaders(
                List.of(ORIGIN,
                        ACCEPT,
                        AUTHORIZATION,
                        CONTENT_TYPE)
        );
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);

    }
}

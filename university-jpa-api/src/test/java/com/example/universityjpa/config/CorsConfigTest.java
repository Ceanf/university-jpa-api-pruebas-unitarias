package com.example.universityjpa.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CorsConfigTest {

    @Test
    void corsConfigurer_deberiaConfigurarCorsCorrectamente() {
        // Arrange
        CorsConfig corsConfig = new CorsConfig();

        CorsRegistry registry = mock(CorsRegistry.class);
        CorsRegistration registration = mock(CorsRegistration.class);

        when(registry.addMapping("/api/**"))
                .thenReturn(registration);

        when(registration.allowedOrigins("*"))
                .thenReturn(registration);

        when(registration.allowedMethods(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"))
                .thenReturn(registration);

        // Act
        WebMvcConfigurer configurer =
                corsConfig.corsConfigurer();
        configurer.addCorsMappings(registry);

        // Assert
        assertNotNull(configurer);

        verify(registry).addMapping("/api/**");
        verify(registration).allowedOrigins("*");
        verify(registration).allowedMethods(
                "GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}

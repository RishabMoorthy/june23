package com.stubserver.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // UI port read from the shared config.properties (bridged into Spring's environment).
    // The allowed CORS origin for the UI is built from this value, so changing ui.port in
    // config.properties is all that's needed per server — no hardcoded port, no code change.
    // Required: if ui.port is missing from config.properties the app fails fast at startup.
    @Value("${ui.port}")
    private int uiPort;

    // Optional extra origins (deployed domain/IP), comma-separated. Empty by default.
    @Value("${app.cors-origin:}")
    private String corsOrigin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        List<String> origins = new ArrayList<>();
        origins.add("http://localhost:" + uiPort);

        if (corsOrigin != null && !corsOrigin.isBlank()) {
            for (String o : corsOrigin.split(",")) {
                String trimmed = o.trim();
                if (!trimmed.isEmpty()) origins.add(trimmed);
            }
        }

        registry.addMapping("/**")
                .allowedOrigins(origins.toArray(new String[0]))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("X-Limit-Applied", "X-Original-Count", "X-Selected-Count",
                        "Content-Disposition")
                .allowCredentials(true)
                .maxAge(3600);
    }
}

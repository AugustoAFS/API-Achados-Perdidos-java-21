package com.AchadosPerdidos.API.Application.Config;

import com.AchadosPerdidos.API.Application.Services.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private static final List<String> DEFAULT_PROD_ORIGINS = List.of(
            "https://achadosperdidos.com",
            "https://www.achadosperdidos.com",
            "https://api.achadosperdidos.com",
            "https://api-achadosperdidos.com.br",
            "https://www.api-achadosperdidos.com.br"
    );
    private static final List<String> DEFAULT_DEV_ORIGIN_PATTERNS = List.of(
            "http://localhost:*",
            "http://127.0.0.1:*"
    );

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final EnvironmentConfig environmentConfig;

    @Value("${SECURITY_ALLOWED_ORIGINS:}")
    private String allowedOriginsEnv;

    @Value("${SECURITY_ALLOWED_ORIGIN_PATTERNS:http://localhost:*}")
    private String allowedOriginPatternsEnv;

    @Value("${SECURITY_ALLOWED_METHODS:GET,POST,PUT,DELETE,OPTIONS,PATCH}")
    private String allowedMethodsEnv;

    @Value("${SECURITY_ALLOWED_HEADERS:*}")
    private String allowedHeadersEnv;

    @Value("${SECURITY_EXPOSED_HEADERS:Authorization,Content-Type,X-Requested-With,X-Token-Expiry}")
    private String exposedHeadersEnv;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, EnvironmentConfig environmentConfig) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.environmentConfig = environmentConfig;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/configuration/**"
                        ).permitAll()
                        .requestMatchers(
                                "/actuator/**",
                                "/error"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/redefinir-senha").permitAll()
                        .requestMatchers("/api/usuarios/criar").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/*/redefinir-senha").permitAll()
                        .requestMatchers("/api/google-auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/campus").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(new ArrayList<>(parseList(allowedMethodsEnv)));
        configuration.setAllowedHeaders(new ArrayList<>(parseList(allowedHeadersEnv)));
        configuration.setExposedHeaders(new ArrayList<>(parseList(exposedHeadersEnv)));
        configuration.setMaxAge(3600L);

        if (environmentConfig.isProduction()) {
            configuration.setAllowedOrigins(new ArrayList<>(resolveProdOrigins()));
        } else {
            List<String> patterns = parseList(allowedOriginPatternsEnv);
            if (patterns.isEmpty()) {
                patterns = DEFAULT_DEV_ORIGIN_PATTERNS;
            }
            configuration.setAllowedOriginPatterns(new ArrayList<>(patterns));
        }

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @NonNull
    private List<String> resolveProdOrigins() {
        List<String> envOrigins = parseList(allowedOriginsEnv);
        return envOrigins.isEmpty() ? new ArrayList<>(DEFAULT_PROD_ORIGINS) : envOrigins;
    }

    private List<String> parseList(String raw) {
        if (raw == null || raw.isBlank()) {
            return new ArrayList<>();
        }
        return Stream.of(raw.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}


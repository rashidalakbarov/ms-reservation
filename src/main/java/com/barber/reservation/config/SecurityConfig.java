package com.barber.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Endpoint-lər üçün icazələr daha dəqiq təyin edilir
    private static final String[] PUBLIC_ENDPOINTS = {
            "/users/**",// User qeydiyyatı, login və s.
            "/v3/api-docs/**",      // Swagger UI
            "/swagger-ui/**",       // Swagger UI
            "/swagger-ui.html",     // Swagger UI
            "/swagger-resources/**",// Swagger UI
            "/webjars/**",          // Swagger UI
            "/admin/reservations/**",
            "/admin/barber/**"          // Admin paneli edit
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                                // Hər kəs üçün açıq endpoint-lər
                                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()

                                // Login olmuş istifadəçilər (ROLE_USER) bərbərlərin siyahısını görə bilər
                                // Bu endpoint-i yaratmaq lazımdır (məs. BarberController altında)
                                .requestMatchers(HttpMethod.GET, "/api/barbers/**")
                                .hasAnyAuthority("ROLE_CUSTOMER", "ROLE_ADMIN")

                                // Login olmuş istifadəçilər (ROLE_USER) öz profillərinə baxa/redaktə edə bilər
                                // Bu endpoint-ləri yaratmaq lazımdır (məs. UserController altında, /api/users/me kimi)
                                // .requestMatchers(HttpMethod.GET, "/api/users/me").hasAuthority("ROLE_USER")
                                // .requestMatchers(HttpMethod.PUT, "/api/users/me").hasAuthority("ROLE_USER")

                                // Adminlər rezervasiyaları idarə edə bilər
//                        .requestMatchers("/admin/reservations/**").hasAuthority("ROLE_ADMIN")

                                // Adminlər bərbərləri idarə edə bilər (CRUD)
                                // Bu endpoint-ləri yaratmaq lazımdır (məs. AdminBarberController altında)
                                // .requestMatchers("/admin/barbers/**").hasAuthority("ROLE_ADMIN")

                                // Adminlər istifadəçiləri idarə edə bilər
                                // Bu endpoint-ləri yaratmaq lazımdır (məs. AdminUserController altında)
//                        .requestMatchers("/admin/users/**").hasAuthority("ROLE_ADMIN")


                                // Digər bütün sorğular autentifikasiya tələb edir
                                .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Production üçün "*" əvəzinə konkret frontend origin(lər)i göstərmək daha təhlükəsizdir
        configuration.setAllowedOrigins(List.of("*")); // Example: List.of("http://localhost:4200", "https://yourdomain.com")
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(false); // Credentials (e.g., Cookies) göndərilmirsə false saxlayın
        configuration.setMaxAge(3600L); // 1 saat

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
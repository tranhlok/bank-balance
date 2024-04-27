package dev.codescreen.config; // Replace with your package name

 // Ensure this matches your package structure

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // Define the security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/ping").permitAll()  // Allow all requests to /ping
                        .anyRequest().authenticated()     // Require authentication for all other requests
                )
                .httpBasic(httpBasicCustomizer -> {})  // Enable HTTP Basic Authentication
                // Disable CSRF protection
                .csrf(csrf -> csrf.disable());
        // Disable CSRF protection for non-browser API interaction

        return http.build();
    }

    // In-memory authentication to demonstrate the concept
//    @Bean
//    public AuthenticationManagerBuilder authenticationManagerBuilder(PasswordEncoder passwordEncoder) throws Exception {
//        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(passwordEncoder);
//        builder
//                .inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder.encode("password"))
//                .authorities("ROLE_USER");
//        return builder;
//    }

    // Define a password encoder bean (required for password encoding in Spring Security 5)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuration to ignore security for certain requests
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/ping");  // Ignoring /ping for demonstration
    }
}

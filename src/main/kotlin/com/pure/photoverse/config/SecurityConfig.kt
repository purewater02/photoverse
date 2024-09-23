package com.pure.photoverse.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors {
                corsCustomizer ->
            corsCustomizer.configurationSource(corsConfigurationSource())
        }.csrf {
                csrfConfigurer: CsrfConfigurer<HttpSecurity> ->
            csrfConfigurer.disable()
        }.authorizeHttpRequests {
                authorizeRequests ->
            authorizeRequests
                .requestMatchers("/actuator/**", "/api-docs/**", "swagger-ui/**", "favicon.ico").permitAll()
                .requestMatchers("/api/posts/public/**").permitAll()
                .anyRequest().authenticated()
        }.formLogin {
                formLogin ->
            formLogin.disable()
        }.httpBasic {
                httpBasic ->
            httpBasic.disable()
        }.addFilterBefore(FirebaseTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowedOrigins = listOf("http://localhost:3000")
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        config.allowedHeaders = listOf("*")
        config.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)

        return source
    }
}

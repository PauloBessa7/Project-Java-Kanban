package com.Kanban.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.Kanban.Components.UserAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    public static final String [] ENDPOINTS_WITH_FREE_AUTHENTICATION = {
            "/auth/login", // url para login
            "/auth" // url para cadastro
    };

    // Endpoints que são acessados somente por:

    //Qualquer pessoa que tenha logado
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/tasks",
            "/tasks/{id}",
            "/tasks/{id}/move",
    };
    // Qualquer User
    public static final String [] ENDPOINTS_USER = {
            "/tasks",
            "/tasks/{id}",
            "/tasks/{id}/move",
    };
    // Qualquer um ADM
    public static final String [] ENDPOINTS_ADMIN = {
            "/tasks",
            "/tasks/{id}",
            "/tasks/{id}/move",
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Desativa CSRF
                .csrf(csrf -> csrf.disable())

                // Configuração de gerenciamento de sessão (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configura as regras de autorização
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ENDPOINTS_WITH_FREE_AUTHENTICATION).permitAll()
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                        .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR")
                        .requestMatchers(ENDPOINTS_USER).hasRole("USER")
                        .anyRequest().denyAll()
                )

                // Adiciona o filtro de autenticação de usuário antes do filtro padrão de autenticação do Spring
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // Finaliza a construção da configuração
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
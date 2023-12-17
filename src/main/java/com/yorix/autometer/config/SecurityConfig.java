package com.yorix.autometer.config;

import com.yorix.autometer.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/car/new-car", "/car/order", "/auc/new-lot").hasAnyAuthority(Role.POWER.name(), Role.ADMIN.name())
                        .requestMatchers("/user/**", "/car/**", "/calculator").authenticated()
                        .requestMatchers("/", "/css/**", "/js/**", "/favicon.ico", "/media/**", "/img/**", "/auc/**").permitAll()
                        .anyRequest().hasAnyAuthority(Role.POWER.name(), Role.ADMIN.name())
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/user/save-visit", true)
                )
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    @Bean
    protected AuthenticationManager configure(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
}

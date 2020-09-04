package com.yorix.autometer.config;

import com.yorix.autometer.model.Role;
import com.yorix.autometer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**", "/favicon.ico", "/media/**").permitAll()
                .antMatchers(HttpMethod.POST).hasAnyAuthority(Role.POWER.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT).hasAnyAuthority(Role.POWER.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE).hasAnyAuthority(Role.POWER.name(), Role.ADMIN.name())
                .antMatchers("/cars/new-car/", "/cars/orders/").hasAnyAuthority(Role.POWER.name(), Role.ADMIN.name())
                .antMatchers("/img/*", "/user/save/visit/", "/cars/**", "/calculator/").authenticated()
                .anyRequest().hasAnyAuthority(Role.POWER.name(), Role.ADMIN.name())
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/user/save/visit/", true)
                .and()
                .logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}

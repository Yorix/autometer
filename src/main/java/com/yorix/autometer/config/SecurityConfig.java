package com.yorix.autometer.config;

import com.yorix.autometer.model.Role;
import com.yorix.autometer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(securedEnabled = true)
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
        http
                .authorizeRequests()
                    .antMatchers("/css/**", "/js/**", "**/favicon.ico").permitAll()
                    .antMatchers(HttpMethod.POST).hasAnyAuthority(Role.POWER.name(), Role.ADMIN.name())
                    .antMatchers(HttpMethod.PUT).hasAnyAuthority(Role.POWER.name(), Role.ADMIN.name())
                    .antMatchers(HttpMethod.DELETE).hasAnyAuthority(Role.POWER.name(), Role.ADMIN.name())
                    .antMatchers("/user/**").hasAnyAuthority(Role.POWER.name(), Role.ADMIN.name())
                    .antMatchers("/db/").hasAuthority(Role.ADMIN.name())
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);

//                .inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder.encode("1")).roles("ADMIN")
//                .and()
//                .withUser("power").password(passwordEncoder.encode("1")).roles("POWER")
//                .and()
//                .withUser("user").password(passwordEncoder.encode("1")).roles("USER");
    }
}
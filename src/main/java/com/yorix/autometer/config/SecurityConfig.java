package com.yorix.autometer.config;

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
        http
                .authorizeRequests()
                    .antMatchers("/css/**", "/js/**", "**/favicon.ico").permitAll()
//                    .antMatchers(HttpMethod.POST).hasAnyRole("POWER", "ADMIN")
//                    .antMatchers(HttpMethod.PUT).hasAnyRole("POWER", "ADMIN")
//                    .antMatchers(HttpMethod.DELETE).hasAnyRole("POWER", "ADMIN")
//                    .antMatchers("/user/**").hasAnyRole("POWER", "ADMIN")
//                    .antMatchers("/db/").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login").permitAll()
//                    .failureUrl("/login-error")
                    .and()
                .logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
//                .inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder.encode("1")).roles("ADMIN")
//                .and()
//                .withUser("power").password(passwordEncoder.encode("1")).roles("POWER")
//                .and()
//                .withUser("user").password(passwordEncoder.encode("1")).roles("USER");

                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}

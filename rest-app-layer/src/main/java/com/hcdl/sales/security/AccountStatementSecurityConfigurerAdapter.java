package com.hcdl.sales.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Configuration
@EnableWebSecurity
public class AccountStatementSecurityConfigurerAdapter {

    private final AccountStatementAuthenticationEntryPoint accountServiceAuthenticationEntryPoint;

    @Autowired
    public AccountStatementSecurityConfigurerAdapter(AccountStatementAuthenticationEntryPoint accountServiceAuthenticationEntryPoint) {
        this.accountServiceAuthenticationEntryPoint = accountServiceAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/console/**").permitAll() // allow h2 console without authentication
                .antMatchers("/health-check").permitAll() // allow health check without authentication
                .anyRequest().authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(accountServiceAuthenticationEntryPoint);
        http.csrf().disable();
        http.cors();
        http.headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //  config.setAllowedOriginPatterns(asList("http://localhost:9355"));
        config.setAllowedOriginPatterns(singletonList("*")); // Not recommended for production use
        config.setAllowedHeaders(asList("Origin", "Content-Type", "Accept", "responseType", "Authorization"));
        config.setAllowedMethods(asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder().username("test_user").password(passwordEncoder().encode("userpassword")).roles("USER").build();
        UserDetails test = User.builder().username("test").password(passwordEncoder().encode("testpassword")).roles("USER").build();
        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user, test, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

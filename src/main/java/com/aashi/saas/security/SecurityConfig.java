package com.aashi.saas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity

public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService customUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
              )
            .httpBasic(Customizer.withDefaults());
      

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncode()
    {
    	return new BCryptPasswordEncoder();
    }
//    @Bean
//	public AuthenticationManager athenticationManager(PasswordEncoder passwordEncoder)
//	{
//		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(customUserService);
//		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
//		return new ProviderManager(daoAuthenticationProvider);	
//	}
}

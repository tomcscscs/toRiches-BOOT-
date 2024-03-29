package org.edupoll.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(t -> t.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll() //
				.anyRequest().permitAll());

		http.anonymous(t -> t.disable());

		http.formLogin(t -> t.loginPage("/login").permitAll());

		http.logout(t -> t.logoutUrl("/auth/logout").logoutSuccessUrl("/").permitAll());
		return http.build();
	}
}

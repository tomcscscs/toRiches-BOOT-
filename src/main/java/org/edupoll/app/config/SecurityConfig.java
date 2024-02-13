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
		.requestMatchers("/static/**").permitAll()//
		.requestMatchers("/", "/index", "/signin", "/register/**", "forgetpassword").permitAll()//
		.anyRequest().permitAll());
				
		http.anonymous(t -> t.disable());

		http.formLogin(t -> t.loginPage("/signin").loginProcessingUrl("/signin/security").permitAll() //
				.failureHandler((request, response, exception) -> {
					response.sendRedirect("/signin?username=" + request.getParameter("username")+"&error");
				}));

		return http.build();
	}
}

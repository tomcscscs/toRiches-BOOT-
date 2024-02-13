package org.edupoll.app.config;

import java.io.IOException;

import org.edupoll.app.config.support.CustomAuthenticationSuccessHandler;
import org.edupoll.app.service.LoginLogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(t -> t.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll() //
				.requestMatchers("/static/**").permitAll()
				.requestMatchers("/", "/index", "/signin", "/register/**", "/forgotpassword").permitAll()
				.requestMatchers("/trade/*", "/trade/api/**").permitAll().anyRequest().authenticated());

		http.anonymous(t -> t.disable());

		http.formLogin(t -> t.loginPage("/signin").loginProcessingUrl("/signin/security").permitAll() //
				.successHandler(customAuthenticationSuccessHandler) // 
				.failureHandler((request, response, exception) -> {
					response.sendRedirect("/signin?username=" + request.getParameter("username") + "&error");
				}));

		return http.build();
	}
}

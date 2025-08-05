package com.cdac.security;

import java.util.List;
import org.springframework.http.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	public UserDetailsService userDetailsService(CustomUserDetailsService customUserDetailsService) {
		return customUserDetailsService;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		
//		http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth
//				.requestMatchers("/api/users/signup", "/api/users/signin").permitAll()
//
//				// useronly endpoints
//				.requestMatchers("/api/vehicles/**").hasRole("USER").requestMatchers("/api/bookings/**").hasAnyRole("USER","ADMIN")
//				.requestMatchers("/api/payments/**").hasRole("USER").requestMatchers("/api/reviews/**").hasRole("USER")
//
//				// admin only endpoints
//				.requestMatchers("/api/users/**").hasRole("ADMIN").requestMatchers("/api/washers/**").hasRole("ADMIN")
//
//				// shared endpoints
//				.requestMatchers("/api/packages/**").hasAnyRole("USER", "ADMIN")
//				.requestMatchers("/api/notifications/**").hasAnyRole("USER", "ADMIN")
//
//				.anyRequest().authenticated())
//				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//		return http.build();
//	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	    http
	        .cors(cors -> cors
	            .configurationSource(request -> {
	                CorsConfiguration config = new CorsConfiguration();
	                config.setAllowedOrigins(List.of("http://localhost:5173")); // React frontend
	                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	                config.setAllowedHeaders(List.of("*"));
	                config.setAllowCredentials(true);
	                return config;
	            })
	        )
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/users/signup", "/api/users/signin").permitAll()
	            .requestMatchers(HttpMethod.GET, "/api/packages").permitAll()


	            // User-only endpoints
	            .requestMatchers("/api/vehicles/**").hasRole("USER")
	            .requestMatchers("/api/bookings/**").hasAnyRole("USER", "ADMIN")
	            .requestMatchers("/api/payments/**").hasRole("USER")
	            .requestMatchers("/api/reviews/**").hasRole("USER")

	            // Admin-only endpoints
	            .requestMatchers(HttpMethod.GET, "/api/packages/**").hasAnyRole("USER", "ADMIN") // Allow viewing
	            .requestMatchers("/api/packages/**").hasRole("ADMIN") // Creation/updating/deleting only by ADMIN
	            .requestMatchers("/api/users/**").hasRole("ADMIN")
//	            .requestMatchers("/api/washers/**").hasRole("WASHER")
	            .requestMatchers("/api/washers/**").permitAll()

	            // Shared endpoints
	            .requestMatchers("/api/packages/**").hasAnyRole("USER", "ADMIN")
	            .requestMatchers("/api/notifications/**").hasAnyRole("USER", "ADMIN")
	            
	            //washer end points
	            .requestMatchers("/api/washers/signup", "/api/washers/signin").permitAll()
	            .requestMatchers("/api/packages/**").hasRole("WASHER")
	            .requestMatchers("/api/washers/profile/**").hasRole("WASHER")
	            .requestMatchers("/api/washer/**").hasRole("WASHER")



	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

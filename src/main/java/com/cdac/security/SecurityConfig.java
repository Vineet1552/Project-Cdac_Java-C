//package com.cdac.security;
//
//import java.util.List;
//import org.springframework.http.HttpMethod;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//
//	@Autowired
//	private JwtRequestFilter jwtRequestFilter;
//
//	public UserDetailsService userDetailsService(CustomUserDetailsService customUserDetailsService) {
//		return customUserDetailsService;
//	}
//
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//		return config.getAuthenticationManager();
//	}
//
////	@Bean
////	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////		
////		http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth
////				.requestMatchers("/api/users/signup", "/api/users/signin").permitAll()
////
////				// useronly endpoints
////				.requestMatchers("/api/vehicles/**").hasRole("USER").requestMatchers("/api/bookings/**").hasAnyRole("USER","ADMIN")
////				.requestMatchers("/api/payments/**").hasRole("USER").requestMatchers("/api/reviews/**").hasRole("USER")
////
////				// admin only endpoints
////				.requestMatchers("/api/users/**").hasRole("ADMIN").requestMatchers("/api/washers/**").hasRole("ADMIN")
////
////				// shared endpoints
////				.requestMatchers("/api/packages/**").hasAnyRole("USER", "ADMIN")
////				.requestMatchers("/api/notifications/**").hasAnyRole("USER", "ADMIN")
////
////				.anyRequest().authenticated())
////				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
////
////		return http.build();
////	}
//	
//	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//		 http
//	        .cors(cors -> cors
//	            .configurationSource(request -> {
//	                CorsConfiguration config = new CorsConfiguration();
//
//	                // 1) explicit allowed origins (do NOT use "*")
//	                config.setAllowedOrigins(List.of("http://localhost:5173"));
//
//	                // 2) allow credentials (so Authorization header and cookies are accepted)
//	                config.setAllowCredentials(true);
//
//	                // 3) allowed HTTP methods and headers (include Authorization)
//	                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//	                config.setAllowedHeaders(List.of("*")); // or List.of("Authorization", "Content-Type", "Accept")
//
//	                // 4) optionally expose headers back to client (if you need them)
//	                config.setExposedHeaders(List.of("Authorization", "Content-Type"));
//
//	                return config;
//	            })
//	        )
//	        .csrf(csrf -> csrf.disable())
//	        .authorizeHttpRequests(auth -> auth
//	            .requestMatchers("/api/users/signup", "/api/users/signin").permitAll()
//	            .requestMatchers("/api/users/**").authenticated()
//	            .requestMatchers(HttpMethod.GET, "/api/packages").permitAll()
//
//
//	            // User-only endpoints
//	            .requestMatchers("/api/vehicles/**").hasRole("USER")
//	            .requestMatchers("/api/bookings/**").authenticated()
//	            .requestMatchers("/api/payments/**").hasRole("USER")
//	            .requestMatchers("/api/reviews/**").hasRole("USER")
//
//	            // Admin-only endpoints
//	            .requestMatchers(HttpMethod.GET, "/api/packages/**").hasAnyRole("USER", "ADMIN") // Allow viewing
//	            .requestMatchers("/api/packages/**").hasRole("ADMIN") // Creation/updating/deleting only by ADMIN
//	            .requestMatchers(HttpMethod.GET, "/api/washer/packages").permitAll()
//
//	            // Shared endpoints
//	            .requestMatchers("/api/packages/**").hasAnyRole("USER", "ADMIN")
//	            .requestMatchers("/api/notifications/**").hasAnyRole("USER", "ADMIN")
//	            
//	            //washer end points
//	            .requestMatchers("/api/washers/signup", "/api/washers/signin").permitAll()
//	            .requestMatchers("/api/packages/**").hasRole("WASHER")
//	            .requestMatchers("/api/washers/profile/**").hasRole("WASHER")
//	            .requestMatchers("/api/washer/**").hasRole("WASHER")
//
//
//
//	            .anyRequest().authenticated()
//	        )
//	        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//	    return http.build();
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//}



package com.cdac.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    // --- Authentication manager bean kept as before ---
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // --- Password encoder bean ---
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // --- Global CORS configuration bean ---
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Accept wildcard patterns (works with allowCredentials)
        config.setAllowedOriginPatterns(List.of("*")); // <-- use patterns, not allowedOrigins("*")
        config.setAllowCredentials(true);

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    // --- Security filter chain (do NOT accept CorsConfigurationSource as a parameter) ---
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // use the global CorsConfigurationSource bean
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // permit preflight and auth endpoints
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/users/signup", "/api/users/signin").permitAll()
                .requestMatchers("/api/washers/signup", "/api/washers/signin").permitAll()
                
             // Permit public access to GET /api/washers
                .requestMatchers(HttpMethod.GET, "/api/washers").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/washer").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/washer/packages/**").permitAll()

                // public reads
                .requestMatchers(HttpMethod.GET, "/api/packages").permitAll()
//                .requestMatchers(HttpMethod.GET, "/api/washer/packages/**").permitAll()

                // bookings require authentication
                .requestMatchers("/api/bookings/**").authenticated()

                // role-based endpoints
                .requestMatchers("/api/vehicles/**").hasRole("USER")
                .requestMatchers("/api/payments/**").hasRole("USER")
                .requestMatchers("/api/reviews/**").hasRole("USER")

                // package management (admin)
                .requestMatchers(HttpMethod.POST, "/api/packages/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,  "/api/packages/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/packages/**").hasRole("ADMIN")

                // washer-specific endpoints
//                .requestMatchers("/api/washer/**").hasRole("WASHER")
//                .requestMatchers("/api/washers/profile/**").hasRole("WASHER")

                // allow authenticated users to access /api/users/** (service does owner/admin checks)
                .requestMatchers("/api/users/**").authenticated()

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

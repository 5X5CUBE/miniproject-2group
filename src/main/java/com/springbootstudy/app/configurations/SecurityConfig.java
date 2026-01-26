package com.springbootstudy.app.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf -> 
		csrf.ignoringRequestMatchers("/h2-console/**"));
		http.csrf(csrf -> csrf.disable());
		
			http.authorizeHttpRequests(auth -> 
			auth.requestMatchers("/mainhome", "/usersjoin", "/login","/css/**","/js/**","/images/**","/idCheck","/nickCheck","/userlogin","/joinResult" ,"/findId","/checkUserForReset","/resetPassword").permitAll()
			.anyRequest().authenticated());
			http.formLogin(form -> form
		            .loginPage("/login").permitAll());
			
			http.logout(logout -> logout
				.logoutUrl("/userLogout")
				.logoutSuccessUrl("/mainhome")
				.invalidateHttpSession(true));
		

		
		return http.build();
	}
	
}

package com.mr.rebujito.grupo1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private JWTAuthentication JWTAuthentication;

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeHttpRequests()
			
			// RESTRICTIONS
			// LOGIN
			.requestMatchers(HttpMethod.POST, "/login").permitAll()
			
			// PARTNERS
			.requestMatchers(HttpMethod.GET, "/partner/enrolledstands").hasAnyAuthority("PARTNER")
			.requestMatchers(HttpMethod.GET, "/partner/enrolledstand/{standUsername}").hasAnyAuthority("PARTNER")
			.requestMatchers(HttpMethod.PUT, "/partner/update/profile").hasAnyAuthority("PARTNER")
			.requestMatchers(HttpMethod.POST, "/partner/register").anonymous()
			
			// STANDS
			.requestMatchers(HttpMethod.GET, "/stand/menu").hasAnyAuthority("STAND")
			.requestMatchers(HttpMethod.GET, "/stand/licenses").hasAnyAuthority("STAND")
			.requestMatchers(HttpMethod.GET, "/stand/license/{townHallUsername}").hasAnyAuthority("STAND")
			.requestMatchers(HttpMethod.PUT, "/stand/update/profile").hasAnyAuthority("STAND")
			.requestMatchers(HttpMethod.PUT, "/stand/product/update/{productId}").hasAnyAuthority("STAND")
			.requestMatchers(HttpMethod.POST, "/stand/register").anonymous()
			.requestMatchers(HttpMethod.POST, "/stand/product/create").hasAnyAuthority("STAND")
			.requestMatchers(HttpMethod.POST, "/stand/partner/add/{partnerUsername}").hasAnyAuthority("STAND")
			.requestMatchers(HttpMethod.POST, "/stand/license/request/{townHallUsername}").hasAnyAuthority("STAND")
			.requestMatchers(HttpMethod.DELETE, "/stand/product/delete/{productId}").hasAnyAuthority("STAND")
			.requestMatchers(HttpMethod.DELETE, "/stand/partner/delete/{partnerUsername}").hasAnyAuthority("STAND")
			.requestMatchers(HttpMethod.DELETE, "/stand/license/delete/{townHallUsername}").hasAnyAuthority("STAND")
			.requestMatchers(HttpMethod.GET, "/stand").anonymous()
			.requestMatchers(HttpMethod.GET, "/stand/{standUsername}").anonymous()
			.requestMatchers(HttpMethod.GET, "/stand/menus").anonymous()
			.requestMatchers(HttpMethod.GET, "/stand/menu/{standUsername}").anonymous()
			
			// TOWNHALLS
			.requestMatchers(HttpMethod.GET, "/townhall/approved/licenses/stands").hasAnyAuthority("TOWNHALL")
			.requestMatchers(HttpMethod.GET, "/townhall/approved/license/stand/{standUsername}").hasAnyAuthority("TOWNHALL")
			.requestMatchers(HttpMethod.GET, "/townhall/licenses").hasAnyAuthority("TOWNHALL")
			.requestMatchers(HttpMethod.GET, "/townhall/license/{standUsername}").hasAnyAuthority("TOWNHALL")
			.requestMatchers(HttpMethod.PUT, "/townhall/license/update/{standUsername}").hasAnyAuthority("TOWNHALL")
			.requestMatchers(HttpMethod.PUT, "/townhall/update/profile").hasAnyAuthority("TOWNHALL")
			.requestMatchers(HttpMethod.GET, "/townhall").anonymous()
			
			// ADMIN
			.requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority("ADMIN")
			.requestMatchers(HttpMethod.PUT, "/admin/update/profile").hasAnyAuthority("ADMIN")
			.anyRequest().authenticated()
			.and()
			.httpBasic();
        http.addFilterBefore(JWTAuthentication, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}

package com.rvbraga.sigec.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	UserDetailsService myUserDetailsService;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authConfig ->{				
				authConfig.requestMatchers(HttpMethod.GET,"/css/**").permitAll();				
				authConfig.requestMatchers(HttpMethod.GET,"/js/**").permitAll();
				authConfig.requestMatchers(HttpMethod.GET,"/img/**").permitAll();
				authConfig.requestMatchers(HttpMethod.GET,"/sigec/dev/**").permitAll();
				authConfig.requestMatchers(HttpMethod.POST,"/sigec/dev/**").permitAll();
				authConfig.requestMatchers(HttpMethod.GET,"/sigec/cliente").hasAnyAuthority("ROLE_USER");
				authConfig.requestMatchers(HttpMethod.GET,"/sigec/clientes").hasAnyAuthority("ROLE_USER");
				authConfig.requestMatchers(HttpMethod.GET,"/sigec/financeiro").hasRole("ADMIN");
				authConfig.requestMatchers(HttpMethod.GET,"sigec/gerencial").hasRole("ADMIN");				
				authConfig.anyRequest().authenticated(); 
			})
			.csrf(csrf -> csrf.disable())
			.userDetailsService(myUserDetailsService)
			.formLogin(formLogin ->{ 
				formLogin
				.usernameParameter("username")
				.passwordParameter("password")
					.loginPage("/sigec/login")		//onde carregar a página de login
					.loginProcessingUrl("/sigec/login") //qual o caminho está setado na página no método post
					.defaultSuccessUrl("/sigec/home",true)
					.permitAll();
			})
//			.formLogin(Customizer.withDefaults())
			
			.httpBasic(Customizer.withDefaults());
			
			return http.build();
		
	}
	

	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}

package com.cognizant.bibliotecadigital.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.cognizant.bibliotecadigital.service.UsuarioService;
import com.cognizant.bibliotecadigital.service.UsuarioServiceInterface;

@Configuration
public class SecurityConfig extends WebSecurityConfiguration {
	
	@Autowired
	private UsuarioServiceInterface usuarioServiceinterface;
	
	 protected void configure(HttpSecurity http) throws Exception {
	        http
	                .authorizeRequests()
	                    .antMatchers(
	                            "/registration",
	                            "/js/**",
	                            "/css/**",
	                            "/img/**",
	                            "/webjars/**").permitAll()
	                    .anyRequest().authenticated()
	                .and()
	                    .formLogin()
	                        .loginPage("/login/Login")
	                            .permitAll()
	                .and()
	                    .logout()
	                        .invalidateHttpSession(true)
	                        .clearAuthentication(true)
	                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	                        .logoutSuccessUrl("/login?logout")
	                .permitAll();
	    }
	 
	 
	 @Bean
	    public BCryptPasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }
	 
	 
	 
	 @Bean
	    public DaoAuthenticationProvider authenticationProvider(){
	        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
	        auth.setUserDetailsService(usuarioServiceinterface);
	        auth.setPasswordEncoder(passwordEncoder());
	        return auth;
	    }

	    
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(authenticationProvider());
	    }
	 

	
}

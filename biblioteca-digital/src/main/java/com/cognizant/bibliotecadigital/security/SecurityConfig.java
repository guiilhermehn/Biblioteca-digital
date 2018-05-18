package com.cognizant.bibliotecadigital.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cognizant.bibliotecadigital.model.Papel;
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	public static PasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private UsuarioService usuarioService; 

	@Bean
	public PasswordEncoder passwordEncoder() {
		return bcryptPasswordEncoder();
	}
	
	@Autowired
	private DataSource dataSource;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(usuarioService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		//auth.authenticationProvider(authenticationProvider());
		auth.jdbcAuthentication()
			.usersByUsernameQuery("select email, senha, true from usuario where email=?")
			.authoritiesByUsernameQuery("select u.email, p.nome from usuario u " + 
					"join usuario_papel up on u.id = up.usuario_id " + 
					"join papel p on p.id = up.papel_id " + 
                    "where u.email=?")
			.dataSource(dataSource)
			.passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests().antMatchers("/assets/**", "/register/**").permitAll()
				//.antMatchers("/").authenticated()
                .anyRequest().authenticated()
				.antMatchers("/livros").hasRole("ADMIN")
				.antMatchers("/gerenciar").hasRole("ADMIN")
				.and()
					.formLogin().loginPage("/login").usernameParameter("email").passwordParameter("senha")
						.failureUrl("/login?error=erroLogin").defaultSuccessUrl("/").permitAll()						
				.and()
					.logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.and()
					.exceptionHandling().accessDeniedPage("/consulta");
	}
}

package com.cognizant.bibliotecadigital.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cognizant.bibliotecadigital.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public static PasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UsuarioService usuarioService;

	/*
	 * ************************************************* Faz a criptografia da senha
	 * para o padrão "bcrypt"
	 ***************************************************/
	@Bean
	public PasswordEncoder passwordEncoder() {
		return bcryptPasswordEncoder();
	}

	@Autowired
	private DataSource dataSource;

	/*
	 * ************************************************************* Faz a
	 * autenticação do usuário direto com o banco Ele faz a criptografia da senha
	 * digitada no formulário de login e valida com o banco
	 ***************************************************************/
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(usuarioService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	/*
	 * *****************************************************************************
	 * *********** Faz a autenticação do usuário para saber qual o seu papel
	 * (usuário comum ou admin) (os métodos DaoAuthentication e
	 * configure(AuthenticationManagerBuilder) são complementares para o Spring
	 * Security)
	 ******************************************************************************************/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().usersByUsernameQuery("select email, senha, true from usuario where email=?")
				.authoritiesByUsernameQuery(
						"select u.email, p.nome from usuario u " + "join usuario_papel up on u.id = up.usuario_id "
								+ "join papel p on p.id = up.papel_id " + "where u.email=?")
				.dataSource(dataSource).passwordEncoder(passwordEncoder());
	}

	/*
	 * ******************************************************************* Trata das
	 * sessões de login do sistema Faz as restrições das páginas baseado no papel
	 * (ROLE) do usuário restringindo usuários comuns (ROLE_USUARIO) de acessar
	 * páginas exclusivas de usuários admin (ROLE_ADMIN) E permite fazer o login com
	 * o e-mail Define a página de acesso negado (ERRO 401)
	 *********************************************************************/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/assets/**", "/register/**").permitAll()

				.antMatchers("/livros", "/livros/**").hasRole("ADMIN")

				.antMatchers("/emprestimos/livrosDevolvidos").hasRole("ADMIN")

				.and().formLogin().loginPage("/login").usernameParameter("email").passwordParameter("senha")
				.failureUrl("/login?error=erroLogin").defaultSuccessUrl("/consulta").permitAll().and().logout()
				.logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("JSESSIONID")
				.and().exceptionHandling().accessDeniedPage("/erroAutorizacao");
	}
}

package com.example.algamoneyapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration  // A anotação @EnableWebSecurity já possui a @Configuration. Mas coloco para lembrar que é uma classe de configuração
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	/**Sobrescrever o metodo configure(AuthenticationManagerBuilder*/

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication()
		.withUser("admin").password("{noop}admin").roles("ROLE");
	}


	/**Sobrescrver o metodo configure(HttpSecurity http)*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/categorias").permitAll()
			.anyRequest().authenticated()
		.and().httpBasic()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Sem manter sessão
		.and()
		.csrf().disable();
	}

}
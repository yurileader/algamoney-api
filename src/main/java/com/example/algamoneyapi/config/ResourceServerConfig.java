package com.example.algamoneyapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration  // A anotação @EnableWebSecurity já possui a @Configuration. Mas coloco para lembrar que é uma classe de configuração
@EnableResourceServer // Incluir para implementar o oauth2
@EnableWebSecurity
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		}

	



	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/categorias").permitAll()
			.anyRequest().authenticated()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Sem manter sessão
		.and()
		.csrf().disable();
	}


	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		
		resources.stateless(true);
	}
	
	//Metodo para leitura de senha encodada
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
}
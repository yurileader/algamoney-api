	package com.example.algamoneyapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
@EnableWebSecurity
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager; //  É quem vai gerenciar a autencicação
	
	@Autowired
	private UserDetailsService userDetailsService;


	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

	    clients.inMemory()
	            .withClient("angular")
	            .secret("$2a$10$6YnGQCdNUjH9yz.sgRAcjOSQBKO.Nh9WjY1S73jASXfXCx3e9oIkm")//senha: angular01
	            .scopes("read", "write")
	            .authorizedGrantTypes("password", "refresh_token")
	            .accessTokenValiditySeconds(60)
	            .refreshTokenValiditySeconds(3600 * 24)
	            
	            .and()
	            
	            .withClient("mobile")
	            .secret("$2a$10$zdQ2QcKeq1/VHNtPu3mRNeX6wy7nqVKjU26zN35JTreSS6rfeJoOW") //senha: mobile01
	            .scopes("read")
	            .authorizedGrantTypes("password", "refresh_token")
	            .accessTokenValiditySeconds(60)
	            .refreshTokenValiditySeconds(3600 * 24)
	            
	            ;
	}

	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints
			.tokenStore(tokenStore())
			.accessTokenConverter(this.accessTokenConverter())
			.reuseRefreshTokens(false)
			.userDetailsService(this.userDetailsService)
		    .authenticationManager(authenticationManager); //<<< É onde ficará armazenado o token
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("algaworks");
		return accessTokenConverter;
	}


	@Bean
	public TokenStore tokenStore() {

		return new JwtTokenStore(accessTokenConverter());
	}

}
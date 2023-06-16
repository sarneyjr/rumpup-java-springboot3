package com.rumpup.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/* 
Esta classe irá conter o gerenciamento de permissões de acesso para cada url. Para
isso, utiliza-se o método HttpSecurity.
*/

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {



	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		  http.authorizeRequests() .antMatchers( "/users/**", "/roles/**",
		  "/customers/**", "/orders/**", "/product/**", "/productOfferings/{id}")
		  .permitAll() .antMatchers( "/product/**", "/orders/{id}", "/customers/{id}",
		  "/users/{id}") .permitAll() .anyRequest().denyAll();
		 

	}
}
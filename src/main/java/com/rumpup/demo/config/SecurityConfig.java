package com.rumpup.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rumpup.demo.services.UserService;

/*
 * THE FOLLOWING ANNOTATIONS WILL BE USED IN THEIR RESPECTIVE CLASSES
 *
 * @EnableResourceServer
 *
 * @EnableAuthorizationServer
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws
	Exception {
	/*
	*auth.inMemoryAuthentication().withUser("user@gmail.com").password("123").roles("USER");
	*/
	auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// CSRF COMES ENABLED BY DEFAULT AND SERVES
		// TO SAFEGUARD THE WEB APPLICATION,
		// BUT AS WE ARE NOT USING THE WEB
		// APPLICATION, IT IS NOT NECESSARY
		// .csrf().disable()
		// TO ENABLE THE CORS METHOD
		// .cors()
		// TO THE APPLICATION DOES NOT SAVE SECTION.
		// THE SECTION WILL BE SAVED VIA THE
		// ACCESS TOKEN (TOKEN EXPIRES)
		// .and().sessionManagement().sessionCreationPolicy(SessionCreationPolic
		// y.STATELESS);
	}
}

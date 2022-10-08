package com.atdaiwa.crowd.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity security) throws Exception {
		security.authorizeRequests().antMatchers("/admin/to/login/page.html").permitAll().antMatchers("/bootstrap/**")
				.permitAll().antMatchers("/crowd/**").permitAll().antMatchers("/css/**").permitAll()
				.antMatchers("/fonts/**").permitAll().antMatchers("/img/**").permitAll().antMatchers("/jQuery/**")
				.permitAll().antMatchers("/layer/**").permitAll().antMatchers("/script/**").permitAll()
				.antMatchers("/ztree/**").permitAll().anyRequest().authenticated().and().csrf().disable().formLogin()
				.loginPage("/admin/to/login/page.html").loginProcessingUrl("/spsecurity/do/login.html")
				.usernameParameter("loginAccount").passwordParameter("userPassword")
				.defaultSuccessUrl("/admin/to/main/page.html").and().logout().logoutUrl("/spsecurity/do/logout.html")
				.logoutSuccessUrl("/admin/to/login/page.html");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
}

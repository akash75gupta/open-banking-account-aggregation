package io.akash.accountaggregator.crsimulator.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class MasterSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     
    }
    
    @Override
	protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable()
    	.authorizeRequests().anyRequest().permitAll()
    	.and().httpBasic()
    	.and().sessionManagement().disable();
	};
}

package com.backend.todolist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.backend.todolist.auth.jwt.JwtConfigurer;
import com.backend.todolist.auth.jwt.JwtTokenGenerator;

// Disabled legacy security config. Keep for reference, but do not load.
@Configuration
@Profile("legacy-security-disabled")
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	private static final String[] AUTH_WHITELIST = {
			"/v2/api-docs",
			"/swagger-resources",
			"/swagger-resources/**",
			"/configuration/ui",
			"/configuration/security",
			"/swagger-ui.html",
			"/webjars/**"
	};
	
    @Autowired
    JwtTokenGenerator jwtTokenGenerator;
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/api/auth/signin").permitAll()
                .antMatchers("/api/auth/signup").permitAll()
                .antMatchers("/api/todo/**/**").authenticated()
                .antMatchers("/api/todo/**").authenticated()
                .anyRequest().authenticated()
            .and()
            .apply(new JwtConfigurer(jwtTokenGenerator));
    }
}
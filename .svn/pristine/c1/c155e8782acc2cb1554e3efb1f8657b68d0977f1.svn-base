package com.iycharge.server.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class AdminWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	//4步
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsMode.ALLOW_FROM));
    	//http.headers().frameOptions().sameOrigin();

    	http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/admin/**").authenticated();
        http
                .formLogin().loginPage("/login").permitAll().and()
                .logout().permitAll();
    }

    @Configuration
    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
    	//3步
        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .inMemoryAuthentication()
                    .withUser("admin").password("123456").roles("ADMIN", "USER");
        }

    }
}

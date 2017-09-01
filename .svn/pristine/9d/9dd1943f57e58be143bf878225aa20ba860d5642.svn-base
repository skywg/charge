package com.iycharge.server.api.security;

import com.iycharge.server.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class ApiWebSecurityConfiguration {

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
	            .authorizeRequests()
	            .antMatchers("/login").permitAll()
	            .antMatchers("/auth").permitAll()
	            .antMatchers("/logout").permitAll()
	            .antMatchers("/admin/**").permitAll()
	            .antMatchers("/app/**").permitAll()
	            .antMatchers("/components/**").permitAll()
	            .antMatchers("/css/**").permitAll()
	            .antMatchers("/images/**").permitAll()
	            .antMatchers("/js/**").permitAll()
	            .antMatchers("/ajax/**").permitAll()
	            .antMatchers("/chargers/**").permitAll()
	            .antMatchers("/api/devices/**").permitAll()
	            .antMatchers("/api/stations/**").permitAll()
	            .antMatchers("/api/chargers/**").permitAll()
	            .antMatchers("/hooks/**").permitAll()
	            .antMatchers("/oauth/send-otp-code").permitAll() 
	            .antMatchers("/api/user/register").permitAll()
	            .antMatchers("/api/user/forget").permitAll()
	            .antMatchers("/api/app/**").permitAll()
	            .antMatchers("/api/app/**").permitAll()
	            .antMatchers("/api/user/bind").permitAll()
	           // .antMatchers("/api/reviews/add").permitAll()
	            .antMatchers("/api/chargers/code").permitAll()
	            .antMatchers("/api/chargers/qrcode").permitAll()
	            .antMatchers("/api/scloudrt/**").permitAll()
	            .antMatchers("/api/contents/**").permitAll()
	            .antMatchers("/share/pages/**").permitAll()
	            .anyRequest().fullyAuthenticated();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        
        @Value("${access_token.validity_period:3600}")
        private int accessTokenValiditySeconds = 3600;
        
        @Autowired
        private AccountService accountService;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

            clients.inMemory().withClient("youetong-android")
                    .authorizedGrantTypes("bind","social","password", "refresh_token","regist")
                    .scopes("read", "write")
                    .secret("secret") 
                    .accessTokenValiditySeconds(accessTokenValiditySeconds)
                    .and()
                    .withClient("youetong-ios")
                    .authorizedGrantTypes("bind","social","password", "refresh_token","regist")
                    .scopes("read", "write")
                    .accessTokenValiditySeconds(accessTokenValiditySeconds)
                    .secret("secret");
        }

        @Bean
        public TokenStore tokenStore() {
            return new JwtTokenStore(jwtTokenEnhancer());
        }

        @Bean
        protected JwtAccessTokenConverter jwtTokenEnhancer() {
            return new JwtAccessTokenConverter();
        }

        private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
            List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));

            granters.add(new OneTimePasswordTokenGranter(accountService, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
            granters.add(new SocialNetworkTokenGranter(accountService, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
            granters.add(new PasswordTokenGranter(accountService, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
            granters.add(new BindTokenGranter(accountService, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
            return new CompositeTokenGranter(granters);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer());

            endpoints.tokenGranter(tokenGranter(endpoints));
        }

    }
}

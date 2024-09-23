package com.oauthGoogle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
        throws Exception{

        httpSecurity.authorizeHttpRequests(authorizeRequest ->
                authorizeRequest
                        .anyRequest()
                        .authenticated())
                .oauth2Login(oauth2Login ->
                        oauth2Login.userInfoEndpoint(userInfo ->
                                userInfo.userService(oauth2UserService()))
                                .successHandler(authenticationSuccessHandler()));
        return httpSecurity.build();

    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new DefaultOAuth2UserService();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new MyCustomAuthenticationSuccessHandler();
    }
}

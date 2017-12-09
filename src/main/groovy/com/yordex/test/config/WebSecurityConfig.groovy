package com.yordex.test.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.servlet.config.annotation.EnableWebMvc

/**
 * Created by Ankush on 06/05/17.
 */
@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationProvider customAuthenticationProvider

    @Value("\$api.version")
    String apiVersion


    @Override
    void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(customAuthenticationProvider)
                .csrf()
                    .disable()
                .sessionManagement()
                     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .and()
                    .authorizeRequests().anyRequest().authenticated()
                        .antMatchers("/$apiVersion/login").permitAll()

    }



    @Override
     void configure(WebSecurity web) throws Exception {
        //Adding swagger endpoints to the list of endpoints that should be excempted from the Authentication process
        web
                .ignoring()
                .antMatchers(HttpMethod.GET, "/swagger-resources/**")
                .antMatchers(HttpMethod.GET, "/webjars/**")
                .antMatchers(HttpMethod.GET, '/v2/api-docs')
                .antMatchers(HttpMethod.GET, "/swagger**")
    }


}
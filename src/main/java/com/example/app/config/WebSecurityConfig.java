package com.example.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity// CSRF禁用，因为不使用session
                .csrf().disable()
                .authorizeRequests()
                // 直接放行
                .antMatchers("/auth/**", "/error/**", "/dev/**").permitAll()
                .antMatchers("/letter/**").anonymous()
                // 权限认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
    }
}

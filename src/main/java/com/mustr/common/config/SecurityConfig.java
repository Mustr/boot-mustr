package com.mustr.common.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mustr.common.service.impl.UserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;
    
    @Autowired  
    SessionRegistry sessionRegistry;  
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }
  
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/assets/**","/css/**","/js/**","/plugins/**","/images/**")
            .permitAll()
            .antMatchers("/common/role/**").hasAuthority("p/all")
            .antMatchers("/common/user/**").hasAuthority("p/all/manageUser")
            .antMatchers("/common/dept/**").hasAuthority("p/all/manageDept")
            .anyRequest()
            .authenticated()//都需要认证
        .and()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/index")
            .failureUrl("/login?error")
            .permitAll()
        .and()
            .sessionManagement()
            .maximumSessions(-1)//-1无限制
            .sessionRegistry(sessionRegistry)
            .expiredUrl("/login")//失效了就去登录页面
        .and()
        .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        .and()
            .headers()
            .frameOptions()
            .sameOrigin()//iframe 跨域问题
        .and()
            .csrf()
            .disable();
    }

    @Bean
    public SessionRegistry getSessionRegistry(){  
        SessionRegistry sessionRegistry = new SessionRegistryImpl();  
        return sessionRegistry;  
    } 
    
    @Bean
    public UserDetailsService getUserDetailsService() {
        UserDetailsService userDetailsService = new UserDetailsServiceImpl();
        return userDetailsService;
    }
}

package com.example.apgw;


import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
@Configuration
public class WebSecurityConfigurator extends WebSecurityConfigurerAdapter {

    /**
     * Security configuration. Disables CSRF.
     * Allows open access to index.html, js files and other assets.
     * All other files are protected, and require user to be authenticated.
     *
     * @param http Provided by Spring
     * @throws Exception If security configuration fails.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/index.html", "/all/*", "/assets/**", "/js/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
    }
}

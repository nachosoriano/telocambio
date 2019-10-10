package com.pfc.soriano.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * AutoConfiguration for JWT authentication.
 *
 * @author scastaneda
 */
@ConditionalOnProperty(name = "platform.security.enabled", havingValue = "true", matchIfMissing = false)
@Configuration
@EnableWebSecurity
@Order(110)
public class JwtAuthenticationAutoConfig extends WebSecurityConfigurerAdapter {

    public JwtAuthenticationTokenFilter authenticationTokenFilter() throws Exception {
        // Note: Descomentar para usar filtro de una sola ejecución
        //JwtAuthenticationTokenFilterOnce authenticationTokenFilter = new JwtAuthenticationTokenFilterOnce();
        JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        authenticationTokenFilter.setAuthenticationSuccessHandler(new BaseAuthenticationSuccessHandler());
        authenticationTokenFilter.setAuthenticationFailureHandler(new BaseAuthenticationFailureHandler());
        return authenticationTokenFilter;
    }

    @Autowired
    JwtAuthenticationProvider authProvider;

    @Bean
    JwtAuthenticationProvider authProvider() {
        return new JwtAuthenticationProvider();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()
                .authenticationProvider(authProvider)
                .addFilterAfter(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .requestMatchers().anyRequest().and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}

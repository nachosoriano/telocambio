/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author nacho
 */
@ConditionalOnProperty(name = "telocambio.documentation.enabled", havingValue = "true", matchIfMissing = false)
@Configuration
@Order(105)
public class SwaggerWebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/springfox-swagger-ui/**")
                .and().anonymous();
    }

}

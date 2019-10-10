/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.swagger;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 *
 * @author scastaneda
 */
@ConditionalOnProperty(name = "telocambio.documentation.enabled", havingValue = "false", matchIfMissing = true)
@Configuration
class SwaggerDisableFilter {

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new NoSwaggerFilter());

        registration.addUrlPatterns("/swagger-ui.html");
        registration.setName("NoSwaggerFilter");
        registration.setOrder(1);
        return registration;
    }

    class NoSwaggerFilter implements Filter {

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpStatus.NOT_FOUND.value());
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void destroy() {
        }
    }

};

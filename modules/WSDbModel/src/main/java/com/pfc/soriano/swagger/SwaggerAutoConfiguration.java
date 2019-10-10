/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.swagger;

import com.pfc.soriano.security.jwt.JwtAuthenticationAutoConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author scastaneda
 */
@ConditionalOnProperty(name = "telocambio.documentation.enabled", havingValue = "true", matchIfMissing = false)
@EnableSwagger2
@Configuration
public class SwaggerAutoConfiguration {

    @Autowired
    @Bean
    public Docket api(Optional<ApiInfo> apiInfo, Optional<List<Parameter>> globalParameter, Optional<SwaggerAllowedPaths> allowedPaths) {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .directModelSubstitute(DateTime.class, String.class)
                .enableUrlTemplating(false)
                .apiInfo(apiInfo.orElse(ApiInfo.DEFAULT))
                .globalOperationParameters(globalParameter.orElseGet(() -> new ArrayList<>()))
                .select().paths(allowedPaths.orElseGet(() -> new SwaggerAllowedPaths("/**")).getPathsPredicate())
                .build();

        return docket;
    }

    @ConditionalOnBean(JwtAuthenticationAutoConfig.class)
    @Bean
    public Parameter swaggerAuthorizationParameter() {
        return new ParameterBuilder()
                .name("Authorization")
                .description("JWT Token authentication")
                .modelRef(new ModelRef("string"))
                .defaultValue("Bearer auth_token")
                .parameterType("header").build();
    }

}

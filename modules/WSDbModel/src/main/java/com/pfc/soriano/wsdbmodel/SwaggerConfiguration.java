/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel;

import com.google.common.base.Predicates;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author nacho
 */
@Configuration
@EnableSwagger2
@SwaggerDefinition(info = @Info(
        description = "Storage groups management API.",
        version = "V3.0-alpha3",
        title = "Storage Groups API",
        contact = @Contact(
                name = "R & D",
                email = "nachosoriano@gmail.com",
                url = ""
        ),
        license = @License(
                name = "Propietary"
        )
))
public class SwaggerConfiguration {

    @Value(value = "${telocambio.security.enabled:false}")
    private boolean securityEnabled;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfo("TeLoCambio Rest API",
                                     "Description",
                                     "0.1",
                                     null,
                                     new springfox.documentation.service.Contact("RnD", "", "nachosoriano@gmail.com"),
                                     "Propietary", null, Collections.emptyList()))
                .globalOperationParameters(
                        Arrays.asList(new ParameterBuilder()
                                .name("Authorization")
                                .description("JWT Token authentication")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(securityEnabled)
                                .build()))
                .select()
                //                .apis(RequestHandlerSelectors.any())
                //                .paths(PathSelectors.any())
                .paths(Predicates.or(PathSelectors.regex("/administrador.*"),
                                     PathSelectors.regex("/categoria.*"),
                                     PathSelectors.regex("/municipio.*"),
                                     PathSelectors.regex("/provincia.*"),
                                     PathSelectors.regex("/subcategoria.*"),
                                     PathSelectors.regex("/trueque.*"),
                                     PathSelectors.regex("/usuario.*"),
                                     PathSelectors.regex("/valoracion.*")
                )
                )
                .build();
    }
}

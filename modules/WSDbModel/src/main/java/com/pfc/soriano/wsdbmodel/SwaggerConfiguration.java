/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel;

import com.pfc.soriano.swagger.SwaggerAllowedPaths;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

/**
 *
 * @author nacho
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public SwaggerAllowedPaths allowedPaths() {
        return new SwaggerAllowedPaths("/administrador.*",
                                       "/categoria.*",
                                       "/municipio.*",
                                       "/provincia.*",
                                       "/subcategoria.*",
                                       "/trueque.*",
                                       "/usuario.*",
                                       "/valoracion.*");
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("TeLoCambio Rest API")
                .description("Description")
                .contact(new springfox.documentation.service.Contact("RnD", "http://www.tedial.com", "nachosoriano@gmail.com"))
                .license("Propietary")
                .version("1.0")
                .build();
    }

}

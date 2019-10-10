/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.swagger;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import springfox.documentation.builders.PathSelectors;

/**
 *
 * @author nacho
 */
public class SwaggerAllowedPaths {

    List<String> paths;

    public SwaggerAllowedPaths(String... paths) {
        this.paths = Arrays.asList(paths);
    }

    public SwaggerAllowedPaths(String path) {
        this.paths = Arrays.asList(path);
    }

    public Predicate<String> getPathsPredicate() {
        List allPaths = paths.stream().map(PathSelectors::regex).collect(Collectors.toList());
        return Predicates.or(allPaths);
    }

}

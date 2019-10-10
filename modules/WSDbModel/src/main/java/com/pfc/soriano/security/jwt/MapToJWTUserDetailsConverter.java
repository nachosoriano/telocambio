package com.pfc.soriano.security.jwt;

/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and
 * open the template in the editor.
 */
import java.util.Collection;
import java.util.Map;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author nacho
 */
class MapToJWTUserDetailsConverter implements Converter<Map, JwtUserDetails> {

    @Override
    public JwtUserDetails convert(Map source) {
        JwtUserDetails result = null;
        if (source != null) {
            result = new JwtUserDetails();
            result.setDescription((String) source.getOrDefault("description", ""));
            result.setEmail((String) source.getOrDefault("email", ""));
            Integer id = (Integer) source.getOrDefault("id", -1);
            result.setId((id != null) ? (id) : (-1l));
            result.setName((String) source.getOrDefault("name", "Unknown"));
            result.getRoles().addAll((Collection<? extends String>) source.get("roles"));
        }
        return result;
    }

}

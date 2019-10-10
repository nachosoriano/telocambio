/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.administrador;

import com.pfc.soriano.wsdbmodel.entity.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
class RSAdministradorConverter implements Converter<RSAdministradorRequest, Administrador> {

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Administrador convert(RSAdministradorRequest source) {
        Administrador result = null;
        if (source != null) {
            result = new Administrador();
            result.setNombre(source.getNombre());
            result.setClave(source.getClave());
            result.setClave(encoder.encode(source.getClave()));
        }
        return result;
    }

}

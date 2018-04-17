/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.administrador;

import com.pfc.soriano.wsdbmodel.entity.Administrador;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
class AdministradorConverter implements Converter<Administrador, RSAdministrador> {

    @Override
    public RSAdministrador convert(Administrador source) {
        RSAdministrador result = null;
        if (source != null) {
            result = new RSAdministrador();
            result.setId(source.getId());
            result.setNombre(source.getNombre());
        }
        return result;
    }

}

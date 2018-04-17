/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.usuario;

import com.pfc.soriano.wsdbmodel.entity.Usuario;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
class UsuarioConverter implements Converter<Usuario, RSUsuario> {

    @Override
    public RSUsuario convert(Usuario source) {
        RSUsuario result = null;
        if (source != null) {
            result = new RSUsuario();
            result.setEmail(source.getEmail());
            result.setEstado(source.getEstado().toString());
            result.setId(source.getId());
            result.setMunicipio(source.getMunicipio().getNombre());
            result.setNombre(source.getNombre());
            result.setProvincia(source.getMunicipio().getProvincia().getNombre());
            result.setPuntuacion(source.getPuntuacion());
        }
        return result;
    }

}

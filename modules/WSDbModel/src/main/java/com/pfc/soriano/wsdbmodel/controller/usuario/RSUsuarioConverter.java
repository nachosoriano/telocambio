/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.usuario;

import com.pfc.soriano.wsdbmodel.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
class RSUsuarioConverter implements Converter<RSUsuarioRequest, Usuario> {

    @Autowired
    private PasswordEncoder encoder;

//    @Autowired
//    ProvinciaDAO provinciaDAO;
//    @Autowired
//    MunicipioDAO municipioDAO;
    @Override
    public Usuario convert(RSUsuarioRequest source) {
        Usuario result = null;
        if (source != null) {
            result = new Usuario();
            result.setClave(encoder.encode(source.getClave()));
            result.setEmail(source.getEmail());
            UsuarioEstado estado = UsuarioEstado.valueOf(source.getEstado());
            result.setEstado(estado);
//            Provincia provincia = provinciaDAO.findByNombre(source.getProvincia());
//            Municipio municipio = municipioDAO.findByProvinciaAndNombre(provincia, source.getMunicipio());
//            result.setMunicipio(municipio);
//            result.setNombre(source.getNombre());
        }
        return result;
    }

}

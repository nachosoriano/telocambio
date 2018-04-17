/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.municipio;

import com.pfc.soriano.wsdbmodel.entity.Municipio;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
class MunicipioConverter implements Converter<Municipio, RSMunicipio> {

    @Override
    public RSMunicipio convert(Municipio source) {
        RSMunicipio result = null;
        if (source != null) {
            result = new RSMunicipio();
            result.setId(source.getId());
            result.setCodigo(source.getCodigo());
            result.setDc(source.getDc());
            result.setNombre(source.getNombre());
            result.setProvincia(source.getProvincia().getNombre());
        }
        return result;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.provincia;

import com.pfc.soriano.wsdbmodel.entity.Provincia;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
class ProvinciaConverter implements Converter<Provincia, RSProvincia> {

    @Override
    public RSProvincia convert(Provincia source) {
        RSProvincia result = null;
        if (source != null) {
            result = new RSProvincia();
            result.setId(source.getId());
            result.setNombre(source.getNombre());
        }
        return result;
    }

}

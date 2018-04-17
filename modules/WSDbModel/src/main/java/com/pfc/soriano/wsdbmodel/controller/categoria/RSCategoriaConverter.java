/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.categoria;

import com.pfc.soriano.wsdbmodel.entity.Categoria;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
class RSCategoriaConverter implements Converter<RSCategoriaRequest, Categoria> {

    @Override
    public Categoria convert(RSCategoriaRequest source) {
        Categoria result = null;
        if (source != null) {
            result = new Categoria();
            result.setNombre(source.getNombre());
            result.setEstado(CategoriaEstado.valueOf(source.getEstado()));
        }
        return result;
    }

}

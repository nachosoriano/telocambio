/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.subcategoria;

import com.pfc.soriano.wsdbmodel.entity.Subcategoria;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
class SubcategoriaConverter implements Converter<Subcategoria, RSSubcategoria> {

    @Override
    public RSSubcategoria convert(Subcategoria source) {
        RSSubcategoria result = null;
        if (source != null) {
            result = new RSSubcategoria();
            result.setCategoria(source.getCategoria().getNombre());
            result.setEstado(source.getEstado().name());
            result.setId(source.getId());
            result.setNombre(source.getNombre());
        }
        return result;
    }

}

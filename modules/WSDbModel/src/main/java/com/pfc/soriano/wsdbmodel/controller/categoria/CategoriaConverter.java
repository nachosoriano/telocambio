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
class CategoriaConverter implements Converter<Categoria, RSCategoria> {

    @Override
    public RSCategoria convert(Categoria source) {
        RSCategoria result = null;
        if (source != null) {
            result = new RSCategoria();
            result.setId(source.getId());
            result.setNombre(source.getNombre());
            result.setEstado(source.getEstado().toString());
        }
        return result;
    }

}

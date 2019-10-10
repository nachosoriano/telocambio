/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.subcategoria;

import com.pfc.soriano.wsdbmodel.entity.Categoria;
import com.pfc.soriano.wsdbmodel.entity.Subcategoria;
import java.util.Optional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
class RSSubcategoriaRequestConverter implements Converter<RSSubcategoriaRequest, Subcategoria> {

    @Override
    public Subcategoria convert(RSSubcategoriaRequest source) {
        Subcategoria result = null;
        if (source != null) {
            result = new Subcategoria();
            result.setNombre(source.getNombre());
            Optional<Categoria> categoria = Optional.empty(); //categoriaDao.findByNombre(source.getCategoria());
            if (categoria.isPresent()) {
                result.setCategoria(categoria.get());
            }
        }
        return result;
    }

}

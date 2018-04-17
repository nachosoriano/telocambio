/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.subcategoria;

import com.pfc.soriano.wsdbmodel.dao.CategoriaDAO;
import com.pfc.soriano.wsdbmodel.entity.Categoria;
import com.pfc.soriano.wsdbmodel.entity.Subcategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
class RSSubcategoriaRequestConverter implements Converter<RSSubcategoriaRequest, Subcategoria> {

    @Autowired
    CategoriaDAO categoriaDao;

    @Override
    public Subcategoria convert(RSSubcategoriaRequest source) {
        Subcategoria result = null;
        if (source != null) {
            result = new Subcategoria();
            result.setNombre(source.getNombre());
            Categoria categoria = categoriaDao.findByNombre(source.getCategoria());
            result.setCategoria(categoria);
        }
        return result;
    }

}

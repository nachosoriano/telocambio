/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.trueque;

import com.pfc.soriano.wsdbmodel.dao.CategoriaDAO;
import com.pfc.soriano.wsdbmodel.dao.SubcategoriaDAO;
import com.pfc.soriano.wsdbmodel.dao.UsuarioDAO;
import com.pfc.soriano.wsdbmodel.entity.Categoria;
import com.pfc.soriano.wsdbmodel.entity.Subcategoria;
import com.pfc.soriano.wsdbmodel.entity.Trueque;
import com.pfc.soriano.wsdbmodel.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
class RSTruequeConverter implements Converter<RSTruequeRequest, Trueque> {

    @Autowired
    CategoriaDAO categoriaDAO;
    @Autowired
    SubcategoriaDAO subcategoriaDAO;
    @Autowired
    UsuarioDAO usuarioDAO;

    @Override
    public Trueque convert(RSTruequeRequest source) {
        Trueque result = null;
        if (source != null) {
            result = new Trueque();
            result.setDescripcionDemanda(source.getDescripcionDemanda());
            result.setDescripcionOferta(source.getDescripcionOferta());

            String[] spSubcat = source.getSubcatDemanda().split("-");
            Categoria categoria = categoriaDAO.findByNombre(spSubcat[0]);
            Subcategoria subcategoria = subcategoriaDAO.findByCategoriaIdAndNombre(categoria.getId(), spSubcat[1]);
            result.setSubcatDemanda(subcategoria);

            spSubcat = source.getSubcatOferta().split("-");
            categoria = categoriaDAO.findByNombre(spSubcat[0]);
            subcategoria = subcategoriaDAO.findByCategoriaIdAndNombre(categoria.getId(), spSubcat[1]);
            result.setSubcatOferta(subcategoria);

            result.setTitulo(source.getTitulo());
            Usuario usuario = usuarioDAO.findByEmail(source.getUsuario());
            result.setUsuario(usuario);
        }
        return result;
    }

}

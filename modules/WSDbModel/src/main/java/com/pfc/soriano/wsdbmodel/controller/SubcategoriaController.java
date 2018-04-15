package com.pfc.soriano.wsdbmodel.controller;

import com.pfc.soriano.wsdbmodel.dao.SubcategoriaDAO;
import com.pfc.soriano.wsdbmodel.entity.Subcategoria;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "WSDbModel/subcategoria")
public class SubcategoriaController {

    @Autowired
    SubcategoriaDAO subcategoriaDAO;

    @RequestMapping(value = "findByCategoriaId", method = RequestMethod.POST)
    @ResponseBody
    public Collection<Subcategoria> findByCategoriaId(@Param("categoriaId") Long categoriaId) {
        return subcategoriaDAO.findByCategoriaIdAndActivoOrderByNombre(categoriaId, true);
    }

    @RequestMapping(value = "findByCategoriaIdAll", method = RequestMethod.POST)
    @ResponseBody
    public Collection<Subcategoria> findByCategoriaIdAll(@Param("categoriaId") Long categoriaId) {
        return subcategoriaDAO.findByCategoriaIdOrderByNombre(categoriaId);
    }

    @RequestMapping(value = "desactiva", method = RequestMethod.POST)
    @ResponseBody
    public Subcategoria desactiva(@Param("id") Long id) {
        Subcategoria subcategoria = subcategoriaDAO.getOne(id);
        subcategoria.setActivo(!subcategoria.isActivo());
        return subcategoriaDAO.saveAndFlush(subcategoria);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public Subcategoria register(@RequestBody Subcategoria subcategoria) {
        return subcategoriaDAO.saveAndFlush(subcategoria);
    }

    @RequestMapping(value = "findByCategoriaIdAndNombre", method = RequestMethod.POST)
    @ResponseBody
    public Subcategoria findByCategoriaIdAndNombre(@Param("categoriaId") Long categoriaId, @Param("nombre") String nombre) {
        return subcategoriaDAO.findByCategoriaIdAndNombre(categoriaId, nombre);
    }
}

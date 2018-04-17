package com.pfc.soriano.wsdbmodel.controller;

import com.pfc.soriano.wsdbmodel.dao.CategoriaDAO;
import com.pfc.soriano.wsdbmodel.dao.UsuarioDAO;
import com.pfc.soriano.wsdbmodel.entity.Categoria;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "WSDbModel/categoria")
public class CategoriaController {

    @Autowired
    CategoriaDAO categoriaDAO;

    @Autowired
    UsuarioDAO usuarioDAO;

    @RequestMapping(value = "findActivas", method = RequestMethod.POST)
    @ResponseBody
    public Collection<Categoria> findByEstado(String estado) {
        return categoriaDAO.findByEstadoOrderByNombre(estado);
    }

    @RequestMapping(value = "findAll", method = RequestMethod.POST)
    @ResponseBody
    public Collection<Categoria> findAll() {

        return categoriaDAO.findAll(new Sort("nombre"));
    }

    @RequestMapping(value = "desactiva", method = RequestMethod.POST)
    @ResponseBody
    public Categoria desactiva(@Param("id") Long id) {
        Categoria categoria = categoriaDAO.getOne(id);
        categoria.setEstado(0);
        return categoriaDAO.saveAndFlush(categoria);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public Categoria register(@RequestBody Categoria categoria) {
        return categoriaDAO.saveAndFlush(categoria);
    }

    @RequestMapping(value = "findByNombre", method = RequestMethod.POST)
    @ResponseBody
    public Categoria findByNombre(@Param("nombre") String nombre) {
        return categoriaDAO.findByNombre(nombre);
    }
}

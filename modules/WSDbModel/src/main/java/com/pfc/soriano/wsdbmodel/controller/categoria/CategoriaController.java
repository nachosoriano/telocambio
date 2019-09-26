package com.pfc.soriano.wsdbmodel.controller.categoria;

import com.pfc.soriano.utils.ConverterUtils;
import com.pfc.soriano.wsdbmodel.dao.CategoriaDAO;
import com.pfc.soriano.wsdbmodel.entity.Categoria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "categoria")
@Api(tags = {"Categoria"})
@RestController
class CategoriaController {

    @Autowired
    CategoriaDAO categoriaDAO;

    @Autowired
    Converter<Categoria, RSCategoria> categoriaConverter;
    @Autowired
    Converter<RSCategoriaRequest, Categoria> rsCategoriaConverter;

    @ApiOperation(value = "Busca una categoria.", nickname = "findCategoria")
    @RequestMapping(path = "{nombre}", method = RequestMethod.GET)
    @Valid
    public RSCategoria find(String nombre) {
        Categoria categoria = categoriaDAO.findByNombre(nombre);
        return categoriaConverter.convert(categoria);
    }

    @ApiOperation(value = "Filtra categorias por estado.", nickname = "filterCategoria")
    @RequestMapping(method = RequestMethod.GET)
    @Valid
    public Collection<RSCategoria> filter(@RequestParam Optional<Integer> estado) {
        Collection<Categoria> categorias;
        if (estado.isPresent()) {
            categorias = categoriaDAO.findByEstadoOrderByNombre(estado.get());
        } else {
            categorias = categoriaDAO.findAll(new Sort(Sort.Direction.ASC, "nombre"));
        }
        return ConverterUtils.convertAll(categorias, categoriaConverter);
    }

    @ApiOperation(value = "Borrado de categoria.", nickname = "deleteCategoria")
    @RequestMapping(method = RequestMethod.DELETE)
    @Transactional(propagation = Propagation.REQUIRED)
    @Valid
    public RSCategoria desactiva(@Param("id") Long id) {
        Categoria categoria = categoriaDAO.getOne(id);
        categoria.setEstado(CategoriaEstado.BORRADO);
        categoria = categoriaDAO.saveAndFlush(categoria);
        return categoriaConverter.convert(categoria);
    }

    @ApiOperation(value = "Creación de categoria.", nickname = "createCategoria")
    @RequestMapping(method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    @Valid
    public RSCategoria register(@Valid @RequestBody RSCategoriaRequest categoriaRequest) {
        Categoria categoria = rsCategoriaConverter.convert(categoriaRequest);
        categoria = categoriaDAO.saveAndFlush(categoria);
        return categoriaConverter.convert(categoria);
    }

}

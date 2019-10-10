package com.pfc.soriano.wsdbmodel.controller.categoria;

import com.pfc.soriano.wsdbmodel.entity.Categoria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.rowset.Predicate;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
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

//    @Autowired
//    CategoriaDAO categoriaDAO;
    @Autowired
    CategoriaFilterFactory categoriaFilter;

    @Autowired
    Converter<Categoria, RSCategoria> categoriaConverter;
    @Autowired
    Converter<RSCategoriaRequest, Categoria> rsCategoriaConverter;

    @ApiOperation(value = "Filtra categorias.", nickname = "filterCategoria")
    @RequestMapping(method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "nombre", value = "Nombre de categoria a filtrar", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "estado", value = "Estado de categoria a filtrar", required = false, dataType = "string", paramType = "query", allowableValues = "ACTIVO, BORRADO")
    })
    @Valid
    public Page<RSCategoria> filter(@RequestParam Optional<String> nombre, @RequestParam Optional<String> estado, Optional<Integer> pageNumber, Optional<Integer> pageSize) {
        List<Predicate> lPredicate = new ArrayList<>();
        if (nombre.isPresent()) {
            lPredicate.add(categoriaFilter.getNombreFilter(nombre.get()));
        }
        if (estado.isPresent()) {
            lPredicate.add(categoriaFilter.getEstadoFilter(CategoriaEstado.valueOf(estado.get())));
        }
        Page<Categoria> filtered = null; //categoriaDAO.findAll(PageRequest.of(pageNumber.orElse(0), pageSize.orElse(20)));
        return filtered.map((t) -> categoriaConverter.convert(t));
    }

    @ApiOperation(value = "Borrado de categoria.", nickname = "deleteCategoria")
    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    @Transactional(propagation = Propagation.REQUIRED)
    @Valid
    public RSCategoria desactiva(@Param("id") Long id) {
        RSCategoria result = null;
        Optional<Categoria> categoria = null; //categoriaDAO.findById(id);
        if (categoria.isPresent()) {
            categoria.get().setEstado(CategoriaEstado.BORRADO);
            Categoria updated = null; //categoriaDAO.save(categoria.get());
            result = categoriaConverter.convert(updated);
        }
        return result;
    }

    @ApiOperation(value = "Creación de categoria.", nickname = "createCategoria")
    @RequestMapping(method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    @Valid
    public RSCategoria register(@Valid @RequestBody RSCategoriaRequest categoriaRequest) {
        Categoria categoria = rsCategoriaConverter.convert(categoriaRequest);
        //categoria = categoriaDAO.save(categoria);
        return categoriaConverter.convert(categoria);
    }

}

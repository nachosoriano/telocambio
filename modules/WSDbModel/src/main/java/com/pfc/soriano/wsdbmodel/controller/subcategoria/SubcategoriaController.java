package com.pfc.soriano.wsdbmodel.controller.subcategoria;

import com.pfc.soriano.wsdbmodel.controller.categoria.CategoriaEstado;
import com.pfc.soriano.wsdbmodel.entity.Categoria;
import com.pfc.soriano.wsdbmodel.entity.Subcategoria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "subcategoria")
@Api(tags = {"Subcategoria"})
@RestController
class SubcategoriaController {

//    @Autowired
//    SubcategoriaDAO subcategoriaDAO;
//    @Autowired
//    CategoriaDAO categoriaDAO;
    @Autowired
    Converter<Subcategoria, RSSubcategoria> subcategoriaConverter;
    @Autowired
    Converter<RSSubcategoriaRequest, Subcategoria> rsSubcategoriaConverter;

    @ApiOperation(value = "Busca subcategorias dentro de una categoría.", nickname = "filterSubcategoria")
    @RequestMapping(method = RequestMethod.GET)
    @Valid
    public Collection<RSSubcategoria> filter(@Param("categoria") String categoriaNombre, @Param("estado") Optional<String> estadoNombre) {
        Collection<Subcategoria> result = new ArrayList<>();
        Optional<Categoria> categoria = Optional.empty(); //categoriaDAO.findByNombre(categoriaNombre);
        if (categoria.isPresent()) {
            if (estadoNombre.isPresent()) {
                CategoriaEstado estado = CategoriaEstado.valueOf(estadoNombre.get());
                //result.addAll(subcategoriaDAO.findByCategoriaIdAndEstadoOrderByNombre(categoria.get().getId(), estado.intValue()));
            } else {
                //result.addAll(result = subcategoriaDAO.findByCategoriaIdOrderByNombre(categoria.get().getId()));
            }
        }
        return result.stream().map((t) -> {
            return subcategoriaConverter.convert(t);
        }).collect(Collectors.toList());
    }

    @ApiOperation(value = "Desactiva una subcategoria.", nickname = "deleteSubcategoria")
    @RequestMapping(method = RequestMethod.DELETE)
    @Valid
    public RSSubcategoria desactiva(@Param("id") Long id) {
        RSSubcategoria result = null;
        Optional<Subcategoria> subcategoria = null; //subcategoriaDAO.findById(id);
        if (subcategoria.isPresent()) {
            subcategoria.get().setEstado(CategoriaEstado.BORRADO);
            Subcategoria updated = null; //subcategoriaDAO.save(subcategoria.get());
            result = subcategoriaConverter.convert(updated);
        }
        return result;
    }

    @ApiOperation(value = "Crea una subcategoria.", nickname = "createSubcategoria")
    @RequestMapping(method = RequestMethod.POST)
    @Valid
    public RSSubcategoria register(@Valid @RequestBody RSSubcategoriaRequest subcategoriaRequest) {
        Subcategoria subcategoria = rsSubcategoriaConverter.convert(subcategoriaRequest);
        subcategoria = null; //subcategoriaDAO.save(subcategoria);
        return subcategoriaConverter.convert(subcategoria);
    }

}

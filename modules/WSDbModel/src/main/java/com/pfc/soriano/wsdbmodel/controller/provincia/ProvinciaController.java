package com.pfc.soriano.wsdbmodel.controller.provincia;

import com.pfc.soriano.wsdbmodel.entity.Provincia;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "provincia")
@Api(tags = {"Provincia"})
@RestController
class ProvinciaController {

//    @Autowired
//    ProvinciaDAO provinciaDAO;
    @Autowired
    Converter<Provincia, RSProvincia> provinciaConverter;

    @ApiOperation(value = "Busca todas las provincias.", nickname = "findAllProvincia")
    @RequestMapping(method = RequestMethod.GET)
    @Valid
    public Collection<RSProvincia> findAll() {
        Collection<Provincia> provincias = null; //(Collection<Provincia>) provinciaDAO.findAll();
        return provincias.stream().map((t) -> provinciaConverter.convert(t)).collect(Collectors.toList());
    }

}

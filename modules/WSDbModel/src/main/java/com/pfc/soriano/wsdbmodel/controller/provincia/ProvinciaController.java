package com.pfc.soriano.wsdbmodel.controller.provincia;

import com.pfc.soriano.utils.ConverterUtils;
import com.pfc.soriano.wsdbmodel.dao.ProvinciaDAO;
import com.pfc.soriano.wsdbmodel.entity.Provincia;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "provincia")
@Api(tags = {"Provincia"})
@RestController
class ProvinciaController {

    @Autowired
    ProvinciaDAO provinciaDAO;

    @Autowired
    Converter<Provincia, RSProvincia> provinciaConverter;

    @ApiOperation(value = "Busca todas las provincias.", nickname = "findAllProvincia")
    @RequestMapping(method = RequestMethod.GET)
    @Valid
    public Collection<RSProvincia> findAll() {
        Collection<Provincia> provincias = provinciaDAO.findAll(new Sort(Sort.Direction.ASC, "nombre"));
        return ConverterUtils.convertAll(provincias, provinciaConverter);
    }

}

package com.pfc.soriano.wsdbmodel.controller.municipio;

import com.pfc.soriano.utils.ConverterUtils;
import com.pfc.soriano.wsdbmodel.dao.MunicipioDAO;
import com.pfc.soriano.wsdbmodel.dao.ProvinciaDAO;
import com.pfc.soriano.wsdbmodel.entity.Municipio;
import com.pfc.soriano.wsdbmodel.entity.Provincia;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "municipio")
@Api(tags = {"Municipio"})
@RestController
class MunicipioController {

    @Autowired
    MunicipioDAO municipioDAO;
    @Autowired
    ProvinciaDAO provinciaDAO;

    @Autowired
    Converter<Municipio, RSMunicipio> municipioConverter;

    @ApiOperation(value = "Busca municipios de una provincia.", nickname = "findMunicipio")
    @RequestMapping(path = "{provinciaNombre}", method = RequestMethod.GET)
    @Valid
    public Collection<RSMunicipio> findByProvincia(Optional<String> provinciaNombre) {
        Collection<Municipio> result;
        if (provinciaNombre.isPresent()) {
            Provincia provincia = provinciaDAO.findByNombre(provinciaNombre.get());
            result = municipioDAO.findByProvinciaOrderByNombre(provincia);
        } else {
            result = municipioDAO.findAll(new Sort(Sort.Direction.ASC, "provincia"));
        }
        return ConverterUtils.convertAll(result, municipioConverter);
    }

}

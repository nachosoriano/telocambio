package com.pfc.soriano.wsdbmodel.controller;

import com.pfc.soriano.wsdbmodel.dao.MunicipioDAO;
import com.pfc.soriano.wsdbmodel.entity.Municipio;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "WSDbModel/municipio")
public class MunicipioController {

    @Autowired
    MunicipioDAO municipioDAO;

    @RequestMapping(value = "findByProvinciaId", method = RequestMethod.POST)
    @ResponseBody
    public Collection<Municipio> findByProvinciaId(@Param("provinciaId") Long provinciaId) {
        return municipioDAO.findByProvinciaIdOrderByNombre(provinciaId);
    }

}

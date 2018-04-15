package com.pfc.soriano.wsdbmodel.controller;

import com.pfc.soriano.wsdbmodel.dao.ProvinciaDAO;
import com.pfc.soriano.wsdbmodel.entity.Provincia;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "WSDbModel/provincia")
public class ProvinciaController {

    @Autowired
    ProvinciaDAO provinciaDAO;

    @RequestMapping(value = "findAll", method = RequestMethod.POST)
    @ResponseBody
    public Collection<Provincia> findAll() {
        return provinciaDAO.findAll(new Sort("nombre"));
    }

}

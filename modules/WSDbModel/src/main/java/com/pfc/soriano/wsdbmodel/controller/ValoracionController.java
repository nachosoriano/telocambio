package com.pfc.soriano.wsdbmodel.controller;

import com.pfc.soriano.wsdbmodel.dao.ValoracionDAO;
import com.pfc.soriano.wsdbmodel.entity.Valoracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "WSDbModel/valoracion")
public class ValoracionController {

    @Autowired
    ValoracionDAO valoracionDAO;

    @RequestMapping(value = "createValoracion", method = RequestMethod.POST)
    @ResponseBody
    public Valoracion createValoracion(@RequestBody Valoracion valoracion) {
        Valoracion result = valoracionDAO.findByUsuarioOrigenIdAndUsuarioDestinoId(valoracion.getUsuarioOrigen().getId(), valoracion.getUsuarioDestino().getId());
        if (result == null) {
            return valoracionDAO.saveAndFlush(valoracion);
        } else {
            valoracion.setId(result.getId());
            return valoracionDAO.saveAndFlush(valoracion);
        }
    }

    @RequestMapping(value = "findByUsuarioOrigenIdAndUsuarioDestinoId", method = RequestMethod.POST)
    @ResponseBody
    public Valoracion findByUsuarioOrigenIdAndUsuarioDestinoId(@Param("usuarioOrigenId") Long usuarioOrigenId, @Param("usuarioDestinoId") Long usuarioDestinoId) {
        return valoracionDAO.findByUsuarioOrigenIdAndUsuarioDestinoId(usuarioOrigenId, usuarioDestinoId);
    }

    @RequestMapping(value = "findByUsuarioDestinoId", method = RequestMethod.POST)
    @ResponseBody
    public Page<Valoracion> findByUsuarioDestinoId(@Param("usuarioDestinoId") Long usuarioDestinoId, Pageable page) {
        return valoracionDAO.findByUsuarioDestinoId(usuarioDestinoId, page);
    }
}

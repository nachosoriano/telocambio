/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.trueque;

import com.pfc.soriano.wsdbmodel.entity.Trueque;
import java.text.SimpleDateFormat;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
class TruequeConverter implements Converter<Trueque, RSTrueque> {

    @Override
    public RSTrueque convert(Trueque source) {
        RSTrueque result = null;
        if (source != null) {
            result = new RSTrueque();
            result.setDescripcionDemanda(source.getDescripcionDemanda());
            result.setDescripcionOferta(source.getDescripcionOferta());
            result.setEstado(source.getEstado().intValue());
            SimpleDateFormat timeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ssXXX");
            result.setFechaActualizacion(timeFormatter.format(source.getFechaActualizacion()));
            result.setFechaCreacion(timeFormatter.format(source.getFechaCreacion()));
            result.setId(source.getId());
            result.setSubcatDemanda(source.getSubcatDemanda().getNombre());
            result.setSubcatOferta(source.getSubcatOferta().getNombre());
            result.setTitulo(source.getTitulo());
            result.setUsuario(source.getUsuario().getEmail());
        }
        return result;
    }

}

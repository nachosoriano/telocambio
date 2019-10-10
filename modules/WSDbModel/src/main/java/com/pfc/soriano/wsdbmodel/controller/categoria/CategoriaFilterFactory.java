/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.categoria;

import javax.sql.rowset.Predicate;
import org.springframework.stereotype.Component;

/**
 *
 * @author nacho
 */
@Component
class CategoriaFilterFactory {

    //QCategoria qCategoria = new QCategoria("");
    public Predicate getNombreFilter(String nombre) {
        //return qCategoria.nombre.eq(nombre);
        return null;
    }

    public Predicate getEstadoFilter(CategoriaEstado estado) {
        return null;
    }
}

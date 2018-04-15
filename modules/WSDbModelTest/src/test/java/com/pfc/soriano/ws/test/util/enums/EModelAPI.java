/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.ws.test.util.enums;

/**
 *
 * @author NACHO
 */
public enum EModelAPI {

    CATEGORIAS("categorias"),
    CATEGORIA("categoria"),
    SUBCATEGORIAS("subcategorias"),
    SUBCATEGORIA("subcategoria"),
    TRUEQUES("trueques"),
    TRUEQUE("trueque"),
    USUARIOS("usuarios"),
    USUARIO("usuario"),
    VALORACIONES("valoraciones"),
    VALORACION("valoracion");

    private final String path;

    private EModelAPI(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.categoria;

/**
 *
 * @author scastaneda
 */
public enum CategoriaEstado {
    BORRADO(0),
    ACTIVO(1);

    private final int dbValue;

    private CategoriaEstado(int dbValue) {
        this.dbValue = dbValue;
    }

    public int intValue() {
        return dbValue;
    }

    public static CategoriaEstado fromIntValue(int value) {
        for (CategoriaEstado item : values()) {
            if (value == item.intValue()) {
                return item;
            }
        }
        throw new IllegalArgumentException("Invalid int value (" + value + ") for CategoriaEstado");
    }

}

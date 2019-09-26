/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.usuario;

/**
 *
 * @author nacho
 */
public enum UsuarioEstado {
    BORRADO(0),
    ACTIVO(1);

    private final int dbValue;

    private UsuarioEstado(int dbValue) {
        this.dbValue = dbValue;
    }

    public int intValue() {
        return dbValue;
    }

    public static UsuarioEstado fromIntValue(int value) {
        for (UsuarioEstado item : values()) {
            if (value == item.intValue()) {
                return item;
            }
        }
        throw new IllegalArgumentException("Invalid int value (" + value + ") for UsuarioEstado");
    }
}

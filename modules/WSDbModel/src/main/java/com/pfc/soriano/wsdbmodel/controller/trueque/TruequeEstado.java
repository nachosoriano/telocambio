/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.trueque;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 *
 * @author nacho
 */
public enum TruequeEstado {
    BORRADO(0),
    ACTIVO(1);

    private final int dbValue;

    private TruequeEstado(int dbValue) {
        this.dbValue = dbValue;
    }

    public int intValue() {
        return dbValue;
    }

    public static TruequeEstado fromIntValue(int value) {
        Collection<TruequeEstado> estados = Arrays.asList(values());
        Optional<TruequeEstado> result = estados.stream().filter((item) -> item.intValue() == value).findAny();
        if (result.isPresent()) {
            return result.get();
        }
        throw new IllegalArgumentException("Invalid int value (" + value + ") for TruequeEstado");
    }
}

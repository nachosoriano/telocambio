/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author NACHO
 */
public class FiltroImagenes implements FilenameFilter {

    private final String prefix;

    public FiltroImagenes(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean accept(File dir, String name) {
        if ((new File(dir, name).isFile())) {
            String nombre = name.substring(0, name.lastIndexOf("."));
            return (nombre.equals(prefix) || nombre.startsWith(prefix + "_"));
        } else {
            return false;
        }
    }

}

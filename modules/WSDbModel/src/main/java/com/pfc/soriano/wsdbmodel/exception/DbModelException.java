/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.exception;

/**
 *
 * @author NACHO
 */
public class DbModelException extends Exception {

    public DbModelException(String txt, Throwable ex) {
        super(txt, ex);
    }
}

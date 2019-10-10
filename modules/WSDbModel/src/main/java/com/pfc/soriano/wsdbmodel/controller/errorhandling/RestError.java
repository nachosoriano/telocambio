/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.errorhandling;

import java.io.Serializable;

/**
 *
 * @author scastaneda
 */
public class RestError implements Serializable {

    public enum Properties {
        ERROR_CODE("errorCode"),
        ERROR_MSG("errorMsg");
        private final String name;

        private Properties(String val) {
            this.name = val;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    String errorCode;
    String errorMsg;

    public RestError(Exception ex) {
        this.errorCode = ex.getClass().getSimpleName();
        errorMsg = ex.getLocalizedMessage();
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "RestError{" + "errorCode=" + errorCode + ", errorMsg=" + errorMsg + '}';
    }
}

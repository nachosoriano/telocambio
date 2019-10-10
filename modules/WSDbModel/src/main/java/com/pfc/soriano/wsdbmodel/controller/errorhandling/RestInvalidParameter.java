/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.errorhandling;

import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 *
 * @author scastaneda
 */
public class RestInvalidParameter extends RestError {

    public static final String ERROR_CODE_TOKEN = "INVALID_PARAMETER";

    public enum Properties {
        OBJECT("object"),
        FIELD("field"),
        INVALID_OBJECT("invalidObject");

        private final String name;

        private Properties(String value) {
            this.name = value;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    String object;
    String field;
    Object invalidObject;

    public RestInvalidParameter(MethodArgumentNotValidException ex) {
        super(ex);
        errorCode = ERROR_CODE_TOKEN;
        object = ex.getBindingResult().getObjectName();
        field = ex.getBindingResult().getFieldError().getField();
        invalidObject = ex.getBindingResult().getTarget();
    }

    public RestInvalidParameter(String object, String field, Object invalidValue, Exception ex) {
        super(ex);
        this.errorCode = ERROR_CODE_TOKEN;
        this.object = object;
        this.field = field;
        this.invalidObject = invalidValue;
    }

    public String getField() {
        return field;
    }

    public String getObject() {
        return object;
    }

    public Object getInvalidObject() {
        return invalidObject;
    }

    @Override
    public String toString() {
        return "RestInvalidParameter{" + "object=" + object + ", field=" + field + ", invalidObject=" + invalidObject + '}';
    }
}

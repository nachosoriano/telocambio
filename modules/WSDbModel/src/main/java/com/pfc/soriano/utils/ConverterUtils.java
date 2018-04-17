/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author nacho
 */
public class ConverterUtils {

    public static <S, T> List<T> convertAll(Collection<S> src, Converter<S, T> convert) {
        List<T> tgt = null;

        if (src != null) {
            tgt = new ArrayList<>();
            for (S item : src) {
                tgt.add(convert.convert(item));
            }
        }
        return tgt;
    }
}

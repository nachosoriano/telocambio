/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.ws.test.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author NACHO
 */
public class TestUtils {

    private static TestUtils instance = null;
    private final JsonParser parser;

    private TestUtils() {
        parser = new JsonParser();
    }

    public static TestUtils getInstance() {
        if (instance == null) {
            instance = new TestUtils();
        }
        return instance;
    }

    public JsonObject generateUser(String nombre) {
        JsonObject result = new JsonObject();
        result.addProperty("nombre", nombre);
        result.addProperty("email", nombre + "@gmail.com");
        result.addProperty("clave", nombre);
        JsonObject municipio = new JsonObject();
        municipio.addProperty("id", 156);
        result.add("municipio", municipio);
        return result;
    }

    public JsonObject generateTrueque(String titulo, Long subCatOferta, Long subCatDemanda) {
        JsonObject result = new JsonObject();
        result.addProperty("titulo", titulo);
        JsonObject subcat = new JsonObject();
        subcat.addProperty("id", 1L);
        result.add("subcatOferta", subcat);
        result.addProperty("descripcionOferta", "DESCRIPCION OFERTA " + titulo);
        result.add("subcatDemanda", subcat);
        result.addProperty("descripcionDemanda", "DESCRIPCION DEMANDA " + titulo);
        JsonObject usuario = new JsonObject();
        usuario.addProperty("id", 1);
        result.add("usuario", usuario);
        return result;
    }

    public JsonObject getObject(String json) {
        return parser.parse(json).getAsJsonObject();
    }

    public JsonArray getArray(String json) {
        return parser.parse(json).getAsJsonArray();
    }

    public JsonObject generateValoracion(Long userFrom, Long userTo, int puntos) {
        JsonObject result = new JsonObject();
        JsonObject usuarioOrigen = new JsonObject();
        usuarioOrigen.addProperty("id", userFrom);
        result.add("usuarioOrigen", usuarioOrigen);
        JsonObject usuarioDestino = new JsonObject();
        usuarioDestino.addProperty("id", userTo);
        result.add("usuarioDestino", usuarioDestino);
        result.addProperty("puntuacion", puntos);
        result.addProperty("comentario", "COMENTARIO VALORACION " + puntos);
        return result;
    }
}

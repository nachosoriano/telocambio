/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.ws.test.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pfc.soriano.ws.test.util.enums.EMethod;
import com.pfc.soriano.ws.test.util.enums.EModelAPI;
import com.pfc.soriano.ws.test.util.enums.ERequestMethod;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author NACHO
 */
public class Proxy {

    // <editor-fold desc="USUARIO" defaultstate="collapsed">
    public static ModelResponse getUsuarioById(Long id) throws IOException {
        Servicio servicio = new Servicio(EModelAPI.USUARIOS, "" + id, ERequestMethod.GET);
        return servicio.execute();
    }

    public static ModelResponse login(String email, String clave) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("clave", clave);
        Servicio servicio = new Servicio(EModelAPI.USUARIO, EMethod.LOGIN, params, null, ERequestMethod.POST,
                MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        return servicio.execute();
    }

    public static ModelResponse register(String jsonUsuario) throws IOException {
        Servicio servicio = new Servicio(EModelAPI.USUARIO, EMethod.REGISTER, null, jsonUsuario, ERequestMethod.POST);
        return servicio.execute();
    }

    public static ModelResponse deleteUsuario(Long id) throws IOException {
        Servicio servicio = new Servicio(EModelAPI.USUARIOS, "" + id, ERequestMethod.DELETE);
        return servicio.execute();
    }

    public static ModelResponse changeClave(String email, String clave) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("clave", clave);
        Servicio servicio = new Servicio(EModelAPI.USUARIO, EMethod.CHANGE_CLAVE, params, null, ERequestMethod.POST, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        return servicio.execute();
    }

    // </editor-fold>
    // <editor-fold desc="CATEGORIA" defaultstate="collapsed">
    public static ModelResponse getCategoriaById(Long id) throws IOException {
        Servicio servicio = new Servicio(EModelAPI.CATEGORIAS, "" + id, null, ERequestMethod.GET);
        return servicio.execute();
    }

    public static ModelResponse createCategoria(String jsonCategoria) throws IOException {
        Servicio servicio = new Servicio(EModelAPI.CATEGORIAS, null, jsonCategoria, ERequestMethod.POST);
        return servicio.execute();
    }

    public static ModelResponse getCategoriaByNombre(String nombre) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("nombre", nombre);
        Servicio servicio = new Servicio(EModelAPI.CATEGORIA, EMethod.FIND_BY_NOMBRE, params, null, ERequestMethod.POST, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        return servicio.execute();
    }

    public static ModelResponse deleteCategoria(Long id) throws IOException {
        Servicio servicio = new Servicio(EModelAPI.CATEGORIAS, "" + id, null, ERequestMethod.DELETE);
        return servicio.execute();
    }

    public static ModelResponse updateCategoria(String stringCategoria) throws IOException {
        JsonParser parser = new JsonParser();
        JsonObject jsonCategoria = parser.parse(stringCategoria).getAsJsonObject();
        Servicio servicio = new Servicio(EModelAPI.CATEGORIAS, "" + jsonCategoria.get("id"), stringCategoria,
                ERequestMethod.PUT);
        return servicio.execute();
    }

    // </editor-fold>
    // <editor-fold desc="SUBCATEGORIA" defaultstate="collapsed">
    public static ModelResponse getSubcategoriaById(Long id) throws IOException {
        Servicio servicio = new Servicio(EModelAPI.SUBCATEGORIAS, "" + id, null, ERequestMethod.GET);
        return servicio.execute();
    }

    public static ModelResponse getSubcategoriaByCategoriaIdAndByNombre(Long categoriaId, String nombre) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("categoriaId", categoriaId.toString());
        params.put("nombre", nombre);
        Servicio servicio = new Servicio(EModelAPI.SUBCATEGORIA, EMethod.FIND_BY_CATEGORIAID_AND_NOMBRE, params, null,
                ERequestMethod.POST, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        return servicio.execute();
    }

    public static ModelResponse createSubcategoria(String jsonSubcategoria) throws IOException {
        Servicio servicio = new Servicio(EModelAPI.SUBCATEGORIAS, null, jsonSubcategoria, ERequestMethod.POST);
        return servicio.execute();
    }

    public static ModelResponse deleteSubcategoria(Long id) throws IOException {
        Servicio servicio = new Servicio(EModelAPI.SUBCATEGORIAS, "" + id, ERequestMethod.DELETE);
        return servicio.execute();
    }

    public static ModelResponse updateSubcategoria(String stringSubcategoria) throws IOException {
        JsonParser parser = new JsonParser();
        JsonObject jsonSubcategoria = parser.parse(stringSubcategoria).getAsJsonObject();
        Servicio servicio = new Servicio(EModelAPI.SUBCATEGORIA, "" + jsonSubcategoria.get("id"), stringSubcategoria,
                ERequestMethod.PUT);
        return servicio.execute();
    }

    // </editor-fold>
    // <editor-fold desc="TRUEQUE" defaultstate="collapsed">
    public static ModelResponse filterTrueque() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("id", null);
        params.put("titulo", null);
        params.put("categoriaOferta", null);
        params.put("descripcionOferta", null);
        params.put("subcategoriaOferta", null);
        params.put("categoriaDemanda", null);
        params.put("descripcionDemanda", null);
        params.put("subcategoriaDemanda", null);
        params.put("usuario", null);
        params.put("pageNumber", "0");
        params.put("pageSize", "5");
        Servicio servicio = new Servicio(EModelAPI.TRUEQUE, EMethod.FILTER, params, null, ERequestMethod.POST);
        return servicio.execute();
    }

    public static ModelResponse countFilteredTrueque() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("id", null);
        params.put("titulo", null);
        params.put("categoriaOferta", null);
        params.put("descripcionOferta", null);
        params.put("subcategoriaOferta", null);
        params.put("categoriaDemanda", null);
        params.put("descripcionDemanda", null);
        params.put("subcategoriaDemanda", null);
        params.put("usuario", null);
        Servicio servicio = new Servicio(EModelAPI.TRUEQUE, EMethod.FILTER, params, null, ERequestMethod.POST);
        return servicio.execute();
    }

    public static ModelResponse getTruequeById(Long id) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("id", "" + id);
        Servicio servicio = new Servicio(EModelAPI.TRUEQUE, EMethod.FIND_BY_ID, params, null, ERequestMethod.POST, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        return servicio.execute();
    }

    public static ModelResponse findTruequeByTitulo(String titulo) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("titulo", titulo);
        Servicio servicio = new Servicio(EModelAPI.TRUEQUE, EMethod.FIND_BY_TITULO, params, null,
                ERequestMethod.POST, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        return servicio.execute();
    }

    public static ModelResponse createTrueque(String jsonTrueque) throws IOException {
        Servicio servicio = new Servicio(EModelAPI.TRUEQUE, EMethod.CREATE_TRUEQUE, null, jsonTrueque, ERequestMethod.POST);
        return servicio.execute();
    }

    public static ModelResponse deleteTrueque(Long id) throws IOException {
        Servicio servicio = new Servicio(EModelAPI.TRUEQUES, "" + id, null, ERequestMethod.DELETE);
        return servicio.execute();
    }

    public static ModelResponse updateTrueque(String stringTrueque) throws IOException {
        JsonParser parser = new JsonParser();
        JsonObject jsonTrueque = parser.parse(stringTrueque).getAsJsonObject();
        Servicio servicio = new Servicio(EModelAPI.TRUEQUES, "" + jsonTrueque.get("id"), stringTrueque,
                ERequestMethod.PUT);
        return servicio.execute();
    }

    public static ModelResponse proponerTrueque(Long truequeId, String emailFrom, String comentario) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("truequeId", "" + truequeId);
        params.put("emailFrom", emailFrom);
        params.put("texto", comentario);
        Servicio servicio = new Servicio(EModelAPI.TRUEQUE, EMethod.PROPONER_TRUEQUE, params, null, ERequestMethod.POST, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        return servicio.execute();
    }

    // </editor-fold>
    //<editor-fold desc="VALORACION" defaultstate="collapsed">
    public static ModelResponse findValoracionesForUser(Long id) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("usuarioDestinoId", "" + id);
        Servicio servicio = new Servicio(EModelAPI.VALORACION, EMethod.FIND_BY_USUARIO_DESTINO, params, null, ERequestMethod.POST, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        return servicio.execute();
    }

    public static ModelResponse createValoracion(String jsonValoracion) throws IOException {
        Servicio servicio = new Servicio(EModelAPI.VALORACION, EMethod.CREATE_VALORACION, null, jsonValoracion, ERequestMethod.POST);
        return servicio.execute();
    }

    public static ModelResponse findValoracionByUsuarioOrigenIdAndUsuarioDestinoId(Long usuarioOrigen, Long usuarioDestino) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("usuarioOrigenId", "" + usuarioOrigen);
        params.put("usuarioDestinoId", "" + usuarioDestino);
        Servicio servicio = new Servicio(EModelAPI.VALORACION, EMethod.FIND_BY_USUARIO_ORIGEN_AND_DESTINO, params, null, ERequestMethod.POST, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        return servicio.execute();
    }
    //</editor-fold>
}

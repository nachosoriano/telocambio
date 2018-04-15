/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.ws.test.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author NACHO
 */
public class SubcategoriaTest {

    private static Long subCategoriaId;
    private static Long categoriaId;

    private static final String CATEGORIA_NAME = "CATEGORIA_TEST";
    private static final String SUBCATEGORIA_NAME = "SUBCATEGORIA_TEST";

    private static final String jsonCategoria = "{\"nombre\":\"" + CATEGORIA_NAME
            + "\",\"descripcion\":\"DESCRIPCION CATEGORIA TEST\"}";

    public SubcategoriaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        Proxy.createCategoria(jsonCategoria);
        JsonParser p = new JsonParser();
        JsonObject elem = p.parse(Proxy.getCategoriaByNombre(CATEGORIA_NAME).getResult()).getAsJsonObject();
        categoriaId = elem.get("id").getAsLong();
        String jsonSubcategoria = "{\"nombre\":\"" + SUBCATEGORIA_NAME
                + "\",\"descripcion\":\"DESCRIPCION SUBCATEGORIA TEST\",\"categoria\":{\"id\":\"" + categoriaId
                + "\"}}";
        Proxy.createSubcategoria(jsonSubcategoria);
        elem = p.parse(Proxy.getSubcategoriaByCategoriaIdAndByNombre(categoriaId, SUBCATEGORIA_NAME).getResult()).getAsJsonObject();
        subCategoriaId = elem.get("id").getAsLong();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        Proxy.deleteCategoria(categoriaId);
    }

    @Before
    public void setUp() throws IOException {
    }

    @After
    public void tearDown() throws IOException {
    }

    /**
     * Test of getSubcategoriaById method, of class Proxy.
     */
    @Test
    public void testGetSubcategoriaById() throws Exception {
        System.out.println("getSubcategoriaById");
        ModelResponse result = Proxy.getSubcategoriaById(subCategoriaId);
        JsonParser p = new JsonParser();
        JsonObject elem = p.parse(result.getResult()).getAsJsonObject();
        assertEquals(elem.get("nombre").getAsString(), SUBCATEGORIA_NAME);
    }

}

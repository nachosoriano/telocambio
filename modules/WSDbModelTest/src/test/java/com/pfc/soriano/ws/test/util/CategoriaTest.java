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
public class CategoriaTest {

    private static final String CATEGORIA_NAME = "CATEGORIA_TEST";
    private static long categoriaId;

    public CategoriaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        String jsonCategoria = "{\"nombre\":\"" + CATEGORIA_NAME + "\",\"descripcion\":\"DESCRIPCION " + CATEGORIA_NAME
                + "\"}";
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(Proxy.createCategoria(jsonCategoria).getResult())
                .getAsJsonObject();
        jsonObject = parser.parse(Proxy.getCategoriaByNombre(jsonObject.get("nombre").getAsString()).getResult()).getAsJsonObject();
        categoriaId = jsonObject.get("id").getAsLong();
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
     * Test of getCategoriaById method, of class Proxy.
     */
    @Test
    public void testGetCategoriaById() throws Exception {
        System.out.println("getCategoriaById");
        ModelResponse result = Proxy.getCategoriaById(categoriaId);
        JsonParser p = new JsonParser();
        JsonObject elem = p.parse(result.getResult()).getAsJsonObject();
        assertEquals(elem.get("nombre").getAsString(), CATEGORIA_NAME);
    }

}

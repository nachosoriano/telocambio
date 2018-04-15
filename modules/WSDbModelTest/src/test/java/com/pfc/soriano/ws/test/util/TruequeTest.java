/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.ws.test.util;

import com.google.gson.JsonObject;
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
public class TruequeTest {

    private static final String TRUEQUE_TITULO = "TRUEQUE_TEST";
    private static long truequeId;
    private static final TestUtils testUtils = TestUtils.getInstance();

    public TruequeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        JsonObject newTrueque = testUtils.generateTrueque(TRUEQUE_TITULO, 1L, 1L);
        Proxy.createTrueque(newTrueque + "");
        ModelResponse resp = Proxy.findTruequeByTitulo(TRUEQUE_TITULO);
        JsonObject jsonObject = testUtils.getArray(resp.getResult()).get(0).getAsJsonObject();
        truequeId = jsonObject.get("id").getAsLong();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        Proxy.deleteTrueque(truequeId);
    }

    @Before
    public void setUp() throws IOException {
    }

    @After
    public void tearDown() throws IOException {
    }

    /**
     * Test of getOfertaById method, of class Proxy.
     */
    @Test
    public void testGetTruequeById() throws Exception {
        System.out.println("getTruequeById");
        ModelResponse result = Proxy.getTruequeById(truequeId);
        JsonObject elem = testUtils.getObject(result.getResult());
        assertEquals(elem.get("titulo").getAsString(), TRUEQUE_TITULO);
    }

    /**
     * Test of getTruequeByNombre method, of class Proxy.
     */
    @Test
    public void testGetTruequeByTitulo() throws Exception {
        System.out.println("testGetTruequeByTitulo");
        ModelResponse result = Proxy.findTruequeByTitulo(TRUEQUE_TITULO);
        JsonObject elem = testUtils.getArray(result.getResult()).get(0).getAsJsonObject();
        assertEquals(elem.get("titulo").getAsString(), TRUEQUE_TITULO);
    }

    /**
     * Test of testProponer method, of class Proxy.
     */
    @Test
    public void testProponer() throws Exception {
        System.out.println("testProponer");
        ModelResponse result = Proxy.proponerTrueque(truequeId, "nachosoriano@gmail.com", "Comentario de propuesta");
        assertEquals(result.getCode(), 200);
    }
}

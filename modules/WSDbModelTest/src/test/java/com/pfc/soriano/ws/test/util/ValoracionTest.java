/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.ws.test.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author NACHO
 */
public class ValoracionTest {

    private static final TestUtils testUtils = TestUtils.getInstance();
    private static Long usuarioId;
    private static final String USUARIO_NOMBRE = "test";

    public ValoracionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        JsonObject usuario = testUtils.generateUser(USUARIO_NOMBRE);
        Proxy.register("" + usuario);
        usuario = testUtils.getObject(Proxy.login(usuario.get("email").getAsString(), usuario.get("clave").getAsString()).getResult());
        usuarioId = usuario.get("id").getAsLong();
        JsonObject newValoracion = testUtils.generateValoracion(1L, usuarioId, 2);
        Proxy.createValoracion("" + newValoracion);
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        Proxy.deleteUsuario(usuarioId);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createValoracion method, of class ValoracionController.
     */
    @Test
    public void testCreateValoracion() throws IOException {
        System.out.println("createValoracion");
        JsonObject newValoracion = testUtils.generateValoracion(2L, usuarioId, 5);
        Proxy.createValoracion("" + newValoracion);
        String valoracionesPaged = Proxy.findValoracionesForUser(usuarioId).getResult();
        JsonArray valoraciones = testUtils.getObject(valoracionesPaged).get("content").getAsJsonArray();
        assertEquals(2, valoraciones.size());
    }

    /**
     * Test of findByUsuarioOrigenIdAndUsuarioDestinoId method, of class
     * ValoracionController.
     */
    @Test
    public void testFindByUsuarioOrigenIdAndUsuarioDestinoId() throws IOException {
        System.out.println("findByUsuarioOrigenIdAndUsuarioDestinoId");
        Long usuarioOrigenId = 1L;
        Long usuarioDestinoId = usuarioId;
        String valoracion = Proxy.findValoracionByUsuarioOrigenIdAndUsuarioDestinoId(usuarioOrigenId, usuarioDestinoId).getResult();
        Assert.assertNotSame("", valoracion);
    }

    /**
     * Test of findByUsuarioDestinoId method, of class ValoracionController.
     */
    @Test
    public void testFindByUsuarioDestinoId() throws IOException {
        System.out.println("findByUsuarioDestinoId");
        Long usuarioDestinoId = usuarioId;
        String valoracionesPaged = Proxy.findValoracionesForUser(usuarioDestinoId).getResult();
        JsonArray valoraciones = testUtils.getObject(valoracionesPaged).get("content").getAsJsonArray();
        assertEquals(2, valoraciones.size());
    }
}

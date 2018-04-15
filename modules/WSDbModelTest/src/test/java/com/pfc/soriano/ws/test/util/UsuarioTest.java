/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.ws.test.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author NACHO
 */
public class UsuarioTest {

    private static Long usuarioId;
    private static final TestUtils testUtils = TestUtils.getInstance();

    public UsuarioTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        Proxy.login("nacho@gmail.com", "nacho");
        Proxy.register("" + testUtils.generateUser("pepito"));
        usuarioId = testUtils.getObject(Proxy.login("pepito@gmail.com", "pepito").getResult()).get("id").getAsLong();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        Proxy.deleteUsuario(usuarioId);
    }

    @Before
    public void setUp() throws IOException {
    }

    @After
    public void tearDown() throws IOException {
    }

    /**
     * Test of login method, of class Proxy.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        ModelResponse result = Proxy.login("pepito@gmail.com", "pepito");
        Assert.assertEquals("pepito", testUtils.getObject(result.getResult()).get("nombre").getAsString());
    }

    /**
     * Test of login method, of class Proxy.
     */
    @Test
    public void testLogin_badcredential() throws Exception {
        System.out.println("Login_badcredential");
        ModelResponse result = Proxy.login("pepito@gmail.com", "nacho2");
        Assert.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, result.getCode());
    }
}

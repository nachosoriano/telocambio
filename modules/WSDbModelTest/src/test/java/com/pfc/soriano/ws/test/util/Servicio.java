/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.ws.test.util;

import com.pfc.soriano.ws.test.util.enums.EMethod;
import com.pfc.soriano.ws.test.util.enums.EModelAPI;
import com.pfc.soriano.ws.test.util.enums.ERequestMethod;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author NACHO
 */
public class Servicio {

    protected EModelAPI api;
    protected EMethod metodo;
    protected Map<String, String> parametros;
    protected String body;
    protected ERequestMethod requestMethod;
    protected MediaType mediaType;
    protected String extra;

    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final int PORT = 7777;
    private static final String BASE_PATH = "/WSDbModel/";

    public Servicio(EModelAPI api, String extra, String body, ERequestMethod requestMethod) {
        this.api = api;
        this.extra = extra;
        this.requestMethod = requestMethod;
        this.body = body;
    }

    public Servicio(EModelAPI api, String extra, ERequestMethod requestMethod) {
        this(api, extra, null, requestMethod);
    }

    public Servicio(EModelAPI api, EMethod metodo, Map<String, String> parametros, String body, ERequestMethod requestMethod) {
        this(api, metodo, parametros, body, requestMethod, MediaType.APPLICATION_JSON_TYPE);
    }

    /**
     *
     * @param api
     * @param metodo
     * @param parametros
     * @param body
     * @param requestMethod
     * @param mediaType
     */
    public Servicio(EModelAPI api, EMethod metodo, Map<String, String> parametros, String body,
            ERequestMethod requestMethod, MediaType mediaType) {
        this.api = api;
        this.metodo = metodo;
        this.parametros = parametros;
        this.body = body;
        this.requestMethod = requestMethod;
        this.mediaType = mediaType;
    }

    public ModelResponse execute() throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = getConnection(api, extra, metodo, parametros, requestMethod, mediaType);
            if (body != null) {
                OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
                output.write(body);

                output.flush();
                output.close();

                connection.connect();
            } else if (parametros != null && ERequestMethod.POST.equals(requestMethod)) {
                OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
                output.write(getAsString(parametros));

                output.flush();
                output.close();

                connection.connect();
            }

            int responseCode = connection.getResponseCode();
            String responseResult = "";

            try {
                responseResult = inputStreamToString(connection.getInputStream());
            } catch (Exception e) {
            }

            if (responseCode < HttpURLConnection.HTTP_OK || responseCode > 299) // 2xx
            // ->
            // Success
            {
                Logger.getLogger(Servicio.class.getName()).warning(
                        inputStreamToString(connection.getErrorStream()));
                //throw new HTTPException(responseCode);
            }

            return new ModelResponse(responseCode, responseResult);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String getAsString(Map<String, String> params) throws UnsupportedEncodingException {
        String result = "";
        for (Map.Entry<String, String> elem : params.entrySet()) {
            if (!result.equals("")) {
                result += "&";
            }
            result += elem.getKey() + "=" + elem.getValue();
        }
        return result;
    }

    private synchronized HttpURLConnection getConnection(EModelAPI api, String extra, EMethod method,
            Map<String, String> params, ERequestMethod requestMethod, MediaType format) {
        try {
            URL url;
            if (params != null && !params.isEmpty() && !ERequestMethod.POST.equals(requestMethod)) {
                url = new URL(PROTOCOL, HOST, PORT, BASE_PATH + api.getPath() + (extra != null ? "/" + extra : "")
                        + "/" + (method != null ? method.getValue() : "") + "?" + getAsString(params));
            } else {
                url = new URL(PROTOCOL, HOST, PORT, BASE_PATH + api.getPath() + (extra != null ? "/" + extra : "")
                        + "/" + (method != null ? method.getValue() : ""));
            }

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(5000);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod(requestMethod.name());
            if (format != null) {
                connection.addRequestProperty("Content-Type", format + "");
            } else {
                connection.addRequestProperty("Content-Type", "application/json");
            }

            return connection;
        } catch (MalformedURLException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    private String inputStreamToString(InputStream is) {
        if (is != null) {
            try (Scanner s = new Scanner(is).useDelimiter("\\A")) {
                return s.hasNext() ? s.next() : "";
            }
        }
        return null;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.wsdbmodel.controller.errorhandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author nacho
 */
public class BaseRestErrorHandler implements ErrorController {

    public static final String ERROR_MESSAGE_HEADER = "X-Exception-msg";
    private static final String ERROR_PATH = "/error";
    private static final String CLASS_NAME = BaseRestErrorHandler.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestInvalidParameter> processValidationError(MethodArgumentNotValidException ex) {
        RestInvalidParameter error = new RestInvalidParameter(ex);
        LOGGER.log(Level.WARNING, "Error: {0}", error);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<RestError> handleDBException(DataAccessException ex) {
        RestError error = new RestError(ex);
        LOGGER.log(Level.WARNING, "Error: {0}", error);
        error.errorMsg = "Database error";
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<RestError> handleAuthenticationException(AuthenticationException ex) {
        return generateResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<RestError> handleException(Exception ex) {
        return generateResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(path = ERROR_PATH)
    public void noHandlerFoundException(Exception ex, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (resp.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
            /**
             * Esta forma de pasar el mensaje de error vía el header de la request no es lo más elegante pero las excepciones lanzadas
             * durante la autenticación no llegan correctamente a este handler de excepciones. Llega una excepción nula. Posiblemente sea
             * necesario escribir un ExceptionTranslation propio para que lleguen correctamente.
             */
            // No autorizado a nivel del filtro.
            String msg = resp.getHeader(ERROR_MESSAGE_HEADER);
            resp.setHeader(ERROR_MESSAGE_HEADER, null);
            objectMapper.writeValue(resp.getOutputStream(), new RestError(new BadCredentialsException(msg)));

        } else {
            objectMapper.writeValue(resp.getOutputStream(), new RestError(ex));
        }
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RestError> methodNotSupportedException(Exception ex) {
        return generateResponse(ex, HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ResponseEntity<RestError> generateResponse(Exception ex, HttpStatus status) {
        RestError error = new RestError(ex);
        LOGGER.log(Level.WARNING, "Error: {0}", error);
        return new ResponseEntity<>(error, status);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}

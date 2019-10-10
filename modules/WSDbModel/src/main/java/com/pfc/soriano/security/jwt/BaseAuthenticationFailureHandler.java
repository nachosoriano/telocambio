package com.pfc.soriano.security.jwt;

import com.pfc.soriano.wsdbmodel.controller.errorhandling.BaseRestErrorHandler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * Defines where to go after successful login. In this implementation just make sure nothing is done (REST API constains no pages)
 *
 * @author nacho
 */
class BaseAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setHeader(BaseRestErrorHandler.ERROR_MESSAGE_HEADER, exception.getLocalizedMessage());
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED");
    }

}

package com.pfc.soriano.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.impl.DefaultClock;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Defines where to go after successful login. In this implementation just make sure nothing is done (REST API constains no pages)
 *
 * @author nacho
 */
class BaseAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final String AUTH_REMAINING_TIME = "x-auth-remaining";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Claims jwtClaims = (Claims) authentication.getDetails();

        Clock clock = DefaultClock.INSTANCE;
        long currentTime = clock.now().getTime();
        long expiration = jwtClaims.getExpiration().getTime();
        long remaining = (expiration - currentTime);

        response.setHeader(AUTH_REMAINING_TIME, Long.toString(remaining));

    }

}

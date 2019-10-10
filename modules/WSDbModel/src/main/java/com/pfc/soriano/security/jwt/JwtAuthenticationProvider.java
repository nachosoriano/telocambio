package com.pfc.soriano.security.jwt;

import io.jsonwebtoken.JwtException;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Used for checking the token from the request and supply the UserDetails if the token is valid
 *
 * @author nacho
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = Logger.getLogger(JwtAuthenticationProvider.class.getName());

    @Autowired
    private IJwtManager jwtManager;

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        Authentication auth = authentication;
        if (!authentication.isAuthenticated()) {
            try {
                auth = jwtManager.generateAuthentication(token);
                if (auth.getName() == null || auth.getName().isEmpty()) {
                    throw new BadCredentialsException("User not found");
                }
            } catch (JwtException ex) {
                throw new BadCredentialsException("JWT Authentication error", ex);
            } catch (Exception ex) {
                LOGGER.info(("JWT Authentication error: " + ex.getMessage()));
                throw ex;
            }
        }
        return auth;
    }

}

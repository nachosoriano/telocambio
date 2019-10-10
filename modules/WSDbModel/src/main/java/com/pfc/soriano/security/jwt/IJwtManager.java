/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.security.jwt;

import java.util.Map;
import org.springframework.security.core.Authentication;

/**
 *
 * @author scastaneda
 */
public interface IJwtManager {

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted
     * from token). If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    JwtUserDetails parseToken(String token);

    /**
     * Refreshes a JWT token.
     *
     * @param token
     * @return
     */
    Authentication refreshAuthentication(String token);

    Authentication generateAuthentication(JwtUserDetails userDetails);

    Authentication generateAuthentication(String token);

    Map<String, Object> getClaims(String token);

}

package com.pfc.soriano.security.jwt;

import io.jsonwebtoken.Claims;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Holder for JWT token from the request.
 * <p/>
 * Other fields aren't used but necessary to comply to the contracts of AbstractUserDetailsAuthenticationProvider
 *
 * @author nacho
 */
class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String token;
    private JwtUserDetails userDetails;

    public JwtAuthenticationToken(JwtUserDetails userDetails, String token, Collection<? extends GrantedAuthority> authorities, Claims claims) {
        super(authorities);
        this.userDetails = userDetails;
        this.token = token;
        setAuthenticated(true);
        setDetails(claims);
    }

    public JwtAuthenticationToken(String token) {
        super(null);
        this.userDetails = null;
        this.token = token;
        setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
//        super.eraseCredentials();
//        token = null;        
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    public String getToken() {
        return token;
    }

    protected JwtUserDetails getJwtUserDetails() {
        return userDetails;
    }

}

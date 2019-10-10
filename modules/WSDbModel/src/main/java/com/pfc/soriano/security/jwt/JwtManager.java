package com.pfc.soriano.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

/**
 * Class validates a given token by using the secret configured in the application
 *
 * @author nacho
 */
@Component
class JwtManager implements IJwtManager {

    @Value("${spring.application.name:jwt-token-validator}")
    private String appName;

    @Value("${platform.security.jwt.secret:g00db1e.2017}")
    private String secret;

    @Value("${platform.security.jwt.expiration:10}")
    private int tokenExpiration;

    @Value("${platform.security.jwt.skew.seconds:300}")
    private long skewSeconds;

    MapToJWTUserDetailsConverter mapConverter;

    @PostConstruct
    void configure() {
        mapConverter = new MapToJWTUserDetailsConverter();
    }

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted
     * from token). If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    @Override
    public JwtUserDetails parseToken(String token) {
        return parseToken(token, 0).getJwtUserDetails();
    }

    protected JwtAuthenticationToken parseToken(String token, long skew) {
        String parsedToken = cleanToken(token);
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .setAllowedClockSkewSeconds(skew)
                .parseClaimsJws(parsedToken)
                .getBody();

        JwtUserDetails userDetails = mapConverter.convert((Map) claims.get("user"));
        // generar Authorities
        List<GrantedAuthority> authorities;
        if (!userDetails.getRoles().isEmpty()) {
            authorities = AuthorityUtils.createAuthorityList(userDetails.getRoles().toArray(new String[]{}));
        } else {
            authorities = new ArrayList<>();
        }

        return new JwtAuthenticationToken(userDetails, parsedToken, authorities, claims);

    }

    protected String cleanToken(String token) {
        return (token.toLowerCase().startsWith("bearer")) ? (token.substring("bearer".length()).trim()) : (token);
    }

    @Override
    public Authentication refreshAuthentication(String token) {
        JwtUserDetails oldDetails = parseToken(token, skewSeconds).getJwtUserDetails();
        return generateAuthentication(oldDetails);
    }

    @Override
    public Authentication generateAuthentication(JwtUserDetails userDetails) {
        // generar token
        Claims claims = Jwts.claims().setSubject(userDetails.getName());
        claims.put("user", userDetails);
        claims.setIssuedAt(new Date());
        claims.setIssuer(appName);
        claims.setExpiration(DateTime.now().plusMinutes(tokenExpiration).toDate());
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        // generar Authorities
        List<GrantedAuthority> authorities;
        if (!userDetails.getRoles().isEmpty()) {
            authorities = AuthorityUtils.createAuthorityList(userDetails.getRoles().toArray(new String[]{}));
        } else {
            authorities = new ArrayList<>();
        }

        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(userDetails, token, authorities, claims);
        return jwtAuthenticationToken;
    }

    public Map<String, Object> getClaims(String token) {
        return (Map<String, Object>) parseToken(token, skewSeconds).getDetails();
    }

    @Override
    public Authentication generateAuthentication(String token) {
        return parseToken(token, 0);
    }

}

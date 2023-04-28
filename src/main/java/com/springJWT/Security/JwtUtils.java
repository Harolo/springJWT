package com.springJWT.Security;

import com.springJWT.Service.implement.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.Date;

@Service
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Firma del token invalida: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("JWT token invalido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token no es compatible: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT token esta vacío: {}", e.getMessage());
        }

        return false;
    }
}


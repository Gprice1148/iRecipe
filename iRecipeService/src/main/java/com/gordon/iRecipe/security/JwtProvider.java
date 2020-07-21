package com.gordon.iRecipe.security;

import static io.jsonwebtoken.Jwts.parser;
import static java.util.Date.from;

import com.gordon.iRecipe.exception.IRecipeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


@Service
public class JwtProvider {

    private KeyStore keyStore;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            InputStream resourceAsStream = getClass().getResourceAsStream("/irecipe.jks");
            keyStore.load(resourceAsStream, "keystore".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new IRecipeException("Exception occurred while loading keystore", e);
        }
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }

    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User principal = (User) authentication
            .getPrincipal();
        return Jwts.builder()
            .setSubject(principal.getUsername())
            .setIssuedAt(from(Instant.now()))
            .signWith(getPrivateKey())
            .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
            .compact();
    }

    public String generateTokenWithUsername(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(from(Instant.now()))
            .signWith(getPrivateKey())
            .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
            .compact();
    }


    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("irecipe", "keystore".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            throw new IRecipeException(
                "Exception occurred while retrieving public key from keystore ", e);
        }
    }

    public boolean validateToken(String jwt) {
        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("irecipe").getPublicKey();
        } catch (KeyStoreException e) {
            throw new IRecipeException("Exception occurred while retrieving the public key");
        }
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = parser()
            .setSigningKey(getPublicKey())
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject();
    }

}

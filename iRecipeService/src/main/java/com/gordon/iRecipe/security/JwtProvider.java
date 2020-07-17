package com.gordon.iRecipe.security;

import com.gordon.iRecipe.exception.IRecipeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parser;


@Service
public class JwtProvider {

    private KeyStore keyStore;

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

        public String generateToken(Authentication authentication) {
            org.springframework.security.core.userdetails.User principal = (User) authentication.getPrincipal();
            return Jwts.builder()
                    .setSubject(principal.getUsername())
                    .signWith(getPrivateKey())
                    .compact();
        }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("irecipe", "keystore".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            throw new IRecipeException("Exception occurred while retrieving public key from keystore ", e);
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

    public String getUsernameFromJwt(String token){
        Claims claims = parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}

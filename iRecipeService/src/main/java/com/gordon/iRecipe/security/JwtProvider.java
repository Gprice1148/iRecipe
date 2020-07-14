package com.gordon.iRecipe.security;

import com.gordon.iRecipe.exception.iRecipeException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import static java.util.Date.from;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/irecipeKeyStore.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new iRecipeException("Exception occurred while loading keystore", e);
        }
    }

        public String generateToken(Authentication authentication) {
            User principal = (User) authentication.getPrincipal();
            return Jwts.builder()
                    .setSubject(principal.getUsername())
                    .signWith(getPrivateKey())
                    .compact();
        }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("irecipeKeyStore", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            throw new iRecipeException("Exception occurred while retrieving public key from keystore ", e);
        }
    }

}

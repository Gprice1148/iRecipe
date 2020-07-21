package com.gordon.iRecipe.service;

import com.gordon.iRecipe.exception.IRecipeException;
import com.gordon.iRecipe.model.RefreshToken;
import com.gordon.iRecipe.repository.RefreshTokenRepository;
import java.time.Instant;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreateDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    void validateRefreshToken(String token) {
        if (refreshTokenRepository.findByToken(token).isEmpty()) {
            throw new IRecipeException("Could not validate token: " + token);
        }
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}

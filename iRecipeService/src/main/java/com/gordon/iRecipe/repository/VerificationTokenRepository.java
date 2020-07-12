package com.gordon.iRecipe.repository;

import com.gordon.iRecipe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<User, Long> {
}

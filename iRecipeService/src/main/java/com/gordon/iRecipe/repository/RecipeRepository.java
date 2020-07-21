package com.gordon.iRecipe.repository;

import com.gordon.iRecipe.model.Recipe;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    //Optional<Recipe> findById(Long id);

}

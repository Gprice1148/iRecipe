package com.gordon.iRecipe.service;

import static java.util.stream.Collectors.toList;

import com.gordon.iRecipe.dto.RecipeDto;
import com.gordon.iRecipe.exception.IRecipeException;
import com.gordon.iRecipe.mapper.RecipeMapper;
import com.gordon.iRecipe.model.Recipe;
import com.gordon.iRecipe.repository.RecipeRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final RecipeMapper recipeMapper;

    @Transactional
    public RecipeDto save(RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.save((recipeMapper.dtoToRecipe(recipeDto)));
        recipeDto.setId(recipe.getId());
        return recipeDto;
    }

    @Transactional
    public List<RecipeDto> getAll() {
        return recipeRepository.findAll().stream().map(recipeMapper::recipeToDto).collect(toList());
    }

    @Transactional
    public RecipeDto getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new IRecipeException("No recipe found with id: " + id));
        return recipeMapper.recipeToDto(recipe);
    }

}

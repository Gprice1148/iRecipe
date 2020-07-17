package com.gordon.iRecipe.service;

import com.gordon.iRecipe.dto.RecipeDto;
import com.gordon.iRecipe.model.Recipe;
import com.gordon.iRecipe.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Transactional
    public RecipeDto save(RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.save(mapFromDtoToModel(recipeDto));
        recipeDto.setId(recipe.getId());
        return recipeDto;
    }

    @Transactional
    public List<RecipeDto> getAll() {
        return recipeRepository.findAll()
                .stream()
                .map(this::mapFromModelToDto)
                .collect(toList());
    }

    private RecipeDto mapFromModelToDto(Recipe recipe) {
        return RecipeDto.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .type(recipe.getType())
                .build();
    }

    private Recipe mapFromDtoToModel(RecipeDto recipeDto) {
        return Recipe.builder()
                .name(recipeDto.getName())
                .type(recipeDto.getType())
                .build();
    }

}

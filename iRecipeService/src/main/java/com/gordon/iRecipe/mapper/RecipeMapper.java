package com.gordon.iRecipe.mapper;

import com.gordon.iRecipe.dto.RecipeDto;
import com.gordon.iRecipe.model.Recipe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeDto recipeToDto(Recipe recipe);

    Recipe dtoToRecipe(RecipeDto recipeDto);

}

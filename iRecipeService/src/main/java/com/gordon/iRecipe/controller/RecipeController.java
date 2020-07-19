package com.gordon.iRecipe.controller;

import com.gordon.iRecipe.dto.RecipeDto;
import com.gordon.iRecipe.service.RecipeService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipe")
@AllArgsConstructor
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipeDto) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(recipeService.save(recipeDto));
    }

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(recipeService.getAll());
    }
}

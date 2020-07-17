package com.gordon.iRecipe.controller;

import com.gordon.iRecipe.dto.RecipeDto;
import com.gordon.iRecipe.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@AllArgsConstructor
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipeDto){
        return  ResponseEntity
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

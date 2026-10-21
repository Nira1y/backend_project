package backend_project.demo.controllers;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend_project.demo.dto.RecipeRequest;
import backend_project.demo.model.Recipe;
import backend_project.demo.service.RecipeBookService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/recipes")
public class RecipeBookController {

    private final RecipeBookService recipeBookService;

    public RecipeBookController(RecipeBookService recipeBookService) {
        this.recipeBookService = recipeBookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeBookService.getRecipeById(id);
        if (recipe == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(recipe);
    }

    @PostMapping()
    public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody RecipeRequest recipeRequest) {
        Recipe recipe = new Recipe(
            null, 
            recipeRequest.title(), 
            recipeRequest.author(),
            recipeRequest.cookingTimeMinutes(),
            recipeRequest.servings()
        );
        Recipe newRecipe = recipeBookService.createRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRecipe);
    }
}


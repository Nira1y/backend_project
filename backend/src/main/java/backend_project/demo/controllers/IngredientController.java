package backend_project.demo.controllers;
import backend_project.demo.dto.IngredientRequest;
import backend_project.demo.model.Ingredient;
import backend_project.demo.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("api/recipes/{recipeId}/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<Ingredient> create(@PathVariable Long recipeId, @Valid @RequestBody IngredientRequest request){
        Ingredient created = ingredientService.addIngredient(recipeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<Ingredient> list(@PathVariable Long recipeId){
        return ingredientService.getIngredientsByRecipeId(recipeId);
    }
}

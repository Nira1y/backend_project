package backend_project.demo.mapper;

import backend_project.demo.dto.RecipeRequest;
import backend_project.demo.model.Ingredient;
import backend_project.demo.model.Recipe;
import backend_project.demo.model.RecipeEntity;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {
    private final IngredientMapper ingredientMapper;
    public RecipeMapper(IngredientMapper ingredientMapper){
        this.ingredientMapper = ingredientMapper;
    }
    public Recipe toDTO(RecipeEntity recipeEntity) {
        List<Ingredient> ingredient = null;
        if(recipeEntity.getIngredients() != null){
            ingredient = recipeEntity.getIngredients().stream()
                .map(ingredientMapper::toDTO)
                .toList();
        }
        return new Recipe(
                recipeEntity.getId(),
                recipeEntity.getTitle(),
                recipeEntity.getAuthor(),
                recipeEntity.getCookingTimeMinutes(),
                recipeEntity.getServings(),
                ingredient
        );
    }

    public RecipeEntity toEntity(RecipeRequest recipeRequest) {
        return new RecipeEntity(
                null,
                recipeRequest.title(),
                recipeRequest.author(),
                recipeRequest.cookingTimeMinutes(),
                recipeRequest.servings()
        );
    }

}

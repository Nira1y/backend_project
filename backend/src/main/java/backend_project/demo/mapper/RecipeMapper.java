package backend_project.demo.mapper;

import backend_project.demo.dto.RecipeRequest;
import backend_project.demo.model.Recipe;
import backend_project.demo.model.RecipeEntity;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {
    public Recipe toDTO(RecipeEntity recipeEntity) {
        return new Recipe(
                recipeEntity.getId(),
                recipeEntity.getTitle(),
                recipeEntity.getAuthor(),
                recipeEntity.getCookingTimeMinutes(),
                recipeEntity.getServings()
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

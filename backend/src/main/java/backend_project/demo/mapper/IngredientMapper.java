package backend_project.demo.mapper;

import org.springframework.stereotype.Component;

import backend_project.demo.dto.IngredientRequest;
import backend_project.demo.model.Ingredient;
import backend_project.demo.model.IngredientEntity;

@Component
public class IngredientMapper {
    public Ingredient toDTO(IngredientEntity ingredientEntity) {
        return new Ingredient(
            ingredientEntity.getId(),
            ingredientEntity.getName(),
            ingredientEntity.getQuantity(),
            ingredientEntity.getCalories(),
            ingredientEntity.getRecipe().getId()
        );
    }

    public IngredientEntity toEntity(IngredientRequest request) {
        IngredientEntity entity = new IngredientEntity();
        entity.setName(request.name());
        entity.setQuantity(request.quantity());
        entity.setCalories(request.calories());
        return entity;
    }
}

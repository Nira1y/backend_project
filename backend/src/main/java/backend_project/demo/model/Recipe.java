package backend_project.demo.model;

import java.util.List;

public record Recipe(
        Long id,
        String title,
        String author,
        Long ownerId,
        Integer cookingTimeMinutes,
        Integer servings,
        List<Ingredient> ingredients
) {}
package backend_project.demo.model;

public record Recipe(
        Long id,
        String title,
        String author,
        Integer cookingTimeMinutes,
        Integer servings
) {}
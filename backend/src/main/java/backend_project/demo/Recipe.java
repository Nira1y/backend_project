package backend_project.demo;

public record Recipe(
        Long id,
        String title,
        String author,
        Integer cookingTimeMinutes,
        Integer servings
) {}
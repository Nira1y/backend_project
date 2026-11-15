package backend_project.demo.model;

public record Ingredient (
    Long id,
    String name,
    Double quantity,
    Integer calories,
    Long recipe_id
){}

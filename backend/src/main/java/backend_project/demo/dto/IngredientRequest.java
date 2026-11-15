package backend_project.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IngredientRequest (
     @NotBlank(message="name can't be empty")
    String name,

    @NotNull(message="quantite can't be null")
    @Min(value = 1, message = "quantity must be more than 0")
    Double quantity,

    Integer calories
){}

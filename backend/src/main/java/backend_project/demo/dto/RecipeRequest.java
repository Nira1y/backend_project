package backend_project.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecipeRequest (
    @NotBlank(message = "title can't be empty")
    String title,

    @NotBlank(message = "author can't be empty")
    String author,

    @NotNull(message = "cookingTimeMinutes can't be null")
    @Min(value = 1, message = "cookingTimeMinutes need to be more than 0")
    Integer cookingTimeMinutes,

    @NotNull(message = "serving can't be null")
    @Min(value = 1, message = "serving need to be more than 0")
    Integer servings
) {}

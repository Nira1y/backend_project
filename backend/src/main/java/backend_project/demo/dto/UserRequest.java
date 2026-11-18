package backend_project.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
    @NotBlank(message = "username can't be empty")
    @Size(min = 3, max = 50)
    String username,

    @NotBlank(message = "password can't be empty")
    @Size(min = 8, max = 100)
    String password
) {}

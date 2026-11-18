package backend_project.demo.model;

public record User(
    Long id,
    String username,
    Role role
) {}

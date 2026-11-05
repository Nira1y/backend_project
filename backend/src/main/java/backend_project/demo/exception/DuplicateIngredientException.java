package backend_project.demo.exception;

public class DuplicateIngredientException extends RuntimeException {
    public DuplicateIngredientException(String message) {
        super(message);
    }
}
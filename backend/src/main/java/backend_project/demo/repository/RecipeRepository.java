package backend_project.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import backend_project.demo.model.RecipeEntity;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    List<RecipeEntity> findByAuthor(String author);
    List<RecipeEntity> findByOwner_Username(String username);
    List<RecipeEntity> findByTitleContainingIgnoreCase(String title);
    @EntityGraph(attributePaths = {"ingredients", "owner"})
    List<RecipeEntity> findAll();
    @EntityGraph(attributePaths = {"ingredients", "owner"})
    Optional<RecipeEntity> findById(Long id);
}

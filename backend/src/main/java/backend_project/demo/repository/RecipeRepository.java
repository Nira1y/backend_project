package backend_project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend_project.demo.model.RecipeEntity;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    
}

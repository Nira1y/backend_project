package backend_project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import backend_project.demo.model.IngredientEntity;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
}
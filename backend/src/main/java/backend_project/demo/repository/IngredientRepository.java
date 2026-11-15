package backend_project.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import backend_project.demo.model.IngredientEntity;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
    List<IngredientEntity> findByRecipeId(Long recipeId);
    boolean existsByRecipeIdAndNameIgnoreCase(Long recipeId, String name);

    @Query("SELECT i.recipe.title, COUNT(i), SUM(i.calories), AVG(calories) " + "FROM IngredientEntity i " + "GROUP BY i.recipe.id, i.recipe.title")
    List<Object[]> getRecipeStats();
}
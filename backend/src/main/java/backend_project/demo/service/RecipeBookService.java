package backend_project.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import backend_project.demo.dto.RecipeRequest;
import backend_project.demo.mapper.RecipeMapper;
import backend_project.demo.model.Recipe;
import backend_project.demo.model.RecipeEntity;
import backend_project.demo.repository.IngredientRepository;
import backend_project.demo.repository.RecipeRepository;

@Service
public class RecipeBookService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final IngredientRepository ingredientRepository;

    public RecipeBookService(RecipeRepository recipeRepository, RecipeMapper recipeMapper, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.ingredientRepository = ingredientRepository;
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .map(recipeMapper::toDTO)
                .orElse(null);
    }

    public List<Recipe> getAllRecipes(Integer limit, Integer offset, String author, String q){
        List<RecipeEntity> entities;

        if (q != null && !q.isBlank()){
        entities = recipeRepository.findByTitleContainingIgnoreCase(q);
        } 

        else if(author != null && !author.isBlank()){
            entities = recipeRepository.findByAuthor(author);
        }
        else{
            entities = recipeRepository.findAll();
        }
        
        List<Recipe> recipes = entities.stream()
                .map(recipeMapper::toDTO)
                .toList();
        
        int start = offset != null ? offset : 0;
        if(start >= recipes.size()) return new ArrayList<>();
        int end = limit != null ? start + limit : recipes.size();
        if(end > recipes.size()) end = recipes.size();
        return recipes.subList(start, end);
    } 

    public Recipe createRecipe(RecipeRequest request){
        RecipeEntity entity = recipeMapper.toEntity(request);
        RecipeEntity savedEntity = recipeRepository.save(entity);
        return recipeMapper.toDTO(savedEntity);
    }

    public Recipe updateRecipe (Long id, RecipeRequest request){
        return recipeRepository.findById(id)
            .map(existingEntity -> {
                existingEntity.setTitle(request.title());
                existingEntity.setAuthor(request.author());
                existingEntity.setCookingTimeMinutes(request.cookingTimeMinutes());
                existingEntity.setServings(request.servings());

                RecipeEntity saved = recipeRepository.save(existingEntity);
                return recipeMapper.toDTO(saved);
            })
            .orElse(null);
    }
    
    public boolean deleteRecipe(Long id){
        if (!recipeRepository.existsById(id)){
            return false;
        }
        else{
            recipeRepository.deleteById(id);
            return true;
        }
    }

    public List<Map<String, Object>> getStats(){
        List<Object[]> results = ingredientRepository.getRecipeStats();
        return results.stream().map(row -> Map.of(
            "title", row[0],
            "ingredientsCount", row[1],
            "totalCalories", row[2],
            "averageCalories", row[3]
        )).toList();
    }
}

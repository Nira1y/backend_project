package backend_project.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import backend_project.demo.dto.RecipeRequest;
import backend_project.demo.mapper.RecipeMapper;
import backend_project.demo.model.Recipe;
import backend_project.demo.model.RecipeEntity;
import backend_project.demo.model.UserEntity;
import backend_project.demo.repository.IngredientRepository;
import backend_project.demo.repository.RecipeRepository;
import backend_project.demo.repository.UserRepository;

@Service
public class RecipeBookService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;

    public RecipeBookService(RecipeRepository recipeRepository, RecipeMapper recipeMapper, IngredientRepository ingredientRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
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
        // TODO: заменить на auth.getName(), пока заглушка
        UserEntity owner = userRepository.findAll().stream().findFirst()
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "no users yet"));

        RecipeEntity entity = recipeMapper.toEntity(request, owner);
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

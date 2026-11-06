package backend_project.demo.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import backend_project.demo.dto.IngredientRequest;
import backend_project.demo.exception.DuplicateIngredientException;
import backend_project.demo.mapper.IngredientMapper;
import backend_project.demo.model.Ingredient;
import backend_project.demo.model.IngredientEntity;
import backend_project.demo.model.RecipeEntity;
import backend_project.demo.repository.IngredientRepository;
import backend_project.demo.repository.RecipeRepository;
import jakarta.transaction.Transactional;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientMapper ingredientMapper;

    public IngredientService(IngredientRepository ingredientRepository, RecipeRepository recipeRepository, IngredientMapper ingredientMapper){
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientMapper = ingredientMapper;
    }

    @Transactional 
    public Ingredient addIngredient(Long recipeId, IngredientRequest request){
        RecipeEntity recipe = recipeRepository.findById(recipeId)
            .orElseThrow(() -> new NoSuchElementException("Recipe with id " + recipeId + " not found"));;
        if(ingredientRepository.existsByRecipeIdAndNameIgnoreCase(recipeId, request.name())){
            throw new DuplicateIngredientException("Ingredient with name '" + request.name() + "' already exists in this recipe");
        }
        IngredientEntity entity = ingredientMapper.toEntity(request);
        entity.setRecipe(recipe);
        IngredientEntity saved = ingredientRepository.save(entity);
        return ingredientMapper.toDTO(saved);
    }

    public List<Ingredient> getIngredientsByRecipeId(Long recipeId) {
        if (!recipeRepository.existsById(recipeId)) {
            throw new NoSuchElementException("Recipe with id " + recipeId + " not found");
        }
        
        return ingredientRepository.findByRecipeId(recipeId).stream()
                .map(ingredientMapper::toDTO)
                .toList();
    }

}

package backend_project.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import backend_project.demo.model.Recipe;

@Service
public class RecipeBookService {
    private final Map<Long, Recipe> recipeBook;
    private final AtomicLong idCounter;

    public RecipeBookService(){
        recipeBook = new HashMap<>();
        idCounter = new AtomicLong(1);
    }

    public Recipe getRecipeById(Long id) {
        return recipeBook.get(id);
    }

    public Recipe createRecipe(Recipe recipe){
        var newRecipe = new Recipe(
            idCounter.getAndIncrement(),
            recipe.title(),
            recipe.author(),
            recipe.cookingTimeMinutes(),
            recipe.servings()
        );
        recipeBook.put(newRecipe.id(), newRecipe);
        return newRecipe;
    }
}

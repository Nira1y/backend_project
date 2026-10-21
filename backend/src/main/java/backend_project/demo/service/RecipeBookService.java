package backend_project.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public List<Recipe> getAllRecipes(Integer limit, Integer offset, String author){
        List<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : recipeBook.values()){
            if (author == null || author.isBlank() || author.equals(recipe.author())){
                recipes.add(recipe);
            }
        }
        int start = offset != null ? offset : 0;
        if(start >= recipes.size()) return new ArrayList<>();
        int end = limit != null ? start + limit : recipes.size();
        if(end > recipes.size()) end = recipes.size();
        return new ArrayList<>(recipes.subList(start, end));

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

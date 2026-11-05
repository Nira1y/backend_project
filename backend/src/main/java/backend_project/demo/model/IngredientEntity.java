package backend_project.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredients")
public class IngredientEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "calories")
    private Integer calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private RecipeEntity recipe;

    public IngredientEntity() {}

    public IngredientEntity(Long id, String name, Double quantity, Integer calories, RecipeEntity recipe){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.calories = calories;
        this.recipe = recipe;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Double getQuantity(){
        return quantity;
    }
    public void setQuantity(Double quantity){
        this.quantity = quantity;
    }
    public Integer getCalories(){
        return calories;
    }
    public void setCalories(Integer calories){
        this.calories = calories;
    }
    public RecipeEntity getRecipe(){
        return recipe;
    }
    public void setRecipe(RecipeEntity recipe){
        this.recipe = recipe;
    }
    

}
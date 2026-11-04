package backend_project.demo.model;
import jakarta.persistence.*;

@Table(name = "recipes")
@Entity
public class RecipeEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "cooking_time_minutes")
    private Integer cookingTimeMinutes;

    @Column(name = "servings")
    private Integer servings;
    
    public RecipeEntity() {
    }

    public RecipeEntity(Long id, String title, String author, Integer cookingTimeMinutes, Integer servings) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.cookingTimeMinutes = cookingTimeMinutes;
        this.servings = servings;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Integer getCookingTimeMinutes() {
        return cookingTimeMinutes;
    }
    public void setCookingTimeMinutes(Integer cookingTimeMinutes) {
        this.cookingTimeMinutes = cookingTimeMinutes;
    }
    public Integer getServings() {
        return servings;
    }
    public void setServings(Integer servings) {
        this.servings = servings;
    }
}

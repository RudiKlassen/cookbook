package de.david.cookbook.persistence;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "CB_RECIPE")
public class Recipe {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User author; //TODO

    @Column(name = "IMAGE_URL")
    private String imageURL;

    @Column(name = "SERVINGS")
    private int servings;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToMany
    @JoinColumn(name = "FK_RECIPE_ID")
    private List<Ingredient> ingredients;

    public Recipe() {
        this.ingredients = new ArrayList<>();
    }

    public Recipe(String name, User author, String imageURL,
                  int servings, String description,
                  Category category, List<Ingredient> ingredients) {
        this.name = name;
        this.author = author;
        this.imageURL = imageURL;
        this.servings = servings;
        this.description = description;
        this.category = category;
        this.ingredients = ingredients;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author Halil
 */
public class RecipeWithoutAuthor {
    private String foodName;
    private String recipe;
    private int recipeId;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
    

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
    
    public RecipeWithoutAuthor(int recipeId, String foodName, String recipe){
        this.recipeId = recipeId;
        this.foodName = foodName;
        this.recipe = recipe;
    }
}

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
public class Recipe  extends RecipeWithoutAuthor{

    private String author;
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public Recipe(int recipeId ,String author, String foodName, String recipe){
        super(recipeId ,foodName, recipe);
        this.author = author;
    }
}

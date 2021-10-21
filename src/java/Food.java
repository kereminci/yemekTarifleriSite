/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kerem
 */
import classes.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import classes.Recipe;
import classes.RecipeWithoutAuthor;

@ManagedBean(name = "food")
@RequestScoped

public class Food {

    @ManagedProperty(value = "#{user}")
    private User user;
    private String ingredients;
    private String title;
    private ArrayList<Recipe> recipes;
    private ArrayList<RecipeWithoutAuthor> recipesWithoutAuthor;

    public ArrayList<RecipeWithoutAuthor> getRecipesWithoutAuthor() {

        this.recipesWithoutAuthor = new ArrayList<>();
        String query = "SELECT recipeId ,foodName, recipe FROM recipes WHERE id = ?";

        try (PreparedStatement stat = Database.getPreparedStatement(query)) {
            stat.setInt(1, this.user.getId());
            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                this.recipesWithoutAuthor.add(new RecipeWithoutAuthor(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException exp) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                            exp.toString()));
        }
        return recipesWithoutAuthor;
    }

    public void setRecipesWithoutAuthor(ArrayList<RecipeWithoutAuthor> recipesWithoutAuthor) {
        this.recipesWithoutAuthor = recipesWithoutAuthor;
    }

    public ArrayList<Recipe> getRecipes() {
        this.recipes = new ArrayList<>();
        String query = "SELECT users.username AS author, recipes.recipeId AS recipeId, recipes.foodname AS foodname, recipes.recipe AS recipe"
                + " FROM recipes INNER JOIN users ON recipes.id = users.id";

        try (Statement stat = Database.getCreatedStatement()) {
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                this.recipes.add(new Recipe(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException exp) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                            exp.toString()));
        }
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void sendFoodRecipe() {
        String query = "INSERT INTO recipes VALUES(?,DEFAULT , ?, ?)";

        try (PreparedStatement stat = Database.getPreparedStatement(query)) {
            stat.setInt(1, this.user.getId());
            stat.setString(2, this.title);
            stat.setString(3, this.ingredients);
            stat.executeUpdate();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Tarif başarıyla eklendi",
                            "Tarif başarıyla eklendi"));

        } catch (SQLException exp) {
            System.out.println(exp.toString());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                            exp.toString()));
        }
    }

    public void deleteFood() {
        String query = "DELETE FROM recipes WHERE recipeId = ?";

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();
        int recipeId = Integer.valueOf(params.get("recipeId"));

        try (PreparedStatement stat = Database.getPreparedStatement(query)) {
            stat.setInt(1, recipeId);
            stat.executeUpdate();
        } catch (SQLException exp) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                            exp.toString()));
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Halil
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kerem
 */
import classes.BlogWithoutAuthor;
import classes.Database;
import classes.BlogWithAuthor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "blog")
@RequestScoped

public class Blog {

    @ManagedProperty(value = "#{user}")
    private User user;
    private String title;
    private String info;
    private ArrayList<BlogWithoutAuthor> blogsWithoutAuthors;
    private ArrayList<BlogWithAuthor> blogsWithAuthors;

    public ArrayList<BlogWithAuthor> getBlogsWithAuthors() {
        this.blogsWithAuthors = new ArrayList<>();
        String query = "SELECT users.username, blogs.title, "
                + "blogs.description FROM blogs INNER JOIN users ON blogs.id = users.id";
        
        try(Statement stat = Database.getCreatedStatement()){
            ResultSet rs = stat.executeQuery(query);
            
            while(rs.next()){
                this.blogsWithAuthors.add(new BlogWithAuthor(0, rs.getString(1), rs.getString(2), rs.getString(3)));
            }
            
        }
        catch(SQLException exp){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                            exp.toString()));
        }
        
        return this.blogsWithAuthors;
        
    }

    public void setBlogsWithAuthors(ArrayList<BlogWithAuthor> blogsWithtAuthors) {
        this.blogsWithAuthors = blogsWithtAuthors;
    }

    
    
    
    
    public ArrayList<BlogWithoutAuthor> getBlogsWithoutAuthors() {
        this.blogsWithoutAuthors = new ArrayList<>();
        String query = "SELECT blogId, title, description FROM blogs WHERE id = ?";
        try (PreparedStatement stat = Database.getPreparedStatement(query)) {
            stat.setInt(1, this.user.getId());
            ResultSet rs = stat.executeQuery();
            
            while(rs.next()){
                this.blogsWithoutAuthors.add(new BlogWithoutAuthor(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            
        } catch (SQLException exp) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                            exp.toString()));
        }
        
        return this.blogsWithoutAuthors;
    }

    public void setBlogsWithoutAuthors(ArrayList<BlogWithoutAuthor> blogsWithoutAuthors) {
        this.blogsWithoutAuthors = blogsWithoutAuthors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void sendBlog() {
        String query = "INSERT INTO blogs VALUES( ?, DEFAULT, ?, ?)";

        try (PreparedStatement stat = Database.getPreparedStatement(query)) {

            stat.setInt(1, this.user.getId());
            stat.setString(2, this.title);
            stat.setString(3, this.info);
            stat.executeUpdate();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Blog başarıyla eklendi",
                            "Blog başarıyla eklendi"));

        } catch (SQLException exp) {
            System.out.println(exp.toString());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                            exp.toString()));
        }

    }
    
    public void deleteBlog(){
        String query = "DELETE FROM blogs WHERE blogId = ?";
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params
                = fc.getExternalContext().getRequestParameterMap();
        
        try(PreparedStatement stat = Database.getPreparedStatement(query)){
            stat.setInt(1, Integer.valueOf(params.get("blogId")));
            stat.executeUpdate();
        }
        
        catch(SQLException exp){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                            exp.toString()));
        }
    }
}

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
public class BlogWithAuthor  extends BlogWithoutAuthor{
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BlogWithAuthor(int blogId, String author, String title, String description){
        super(blogId, title, description);
        this.author = author;
    }
    
}

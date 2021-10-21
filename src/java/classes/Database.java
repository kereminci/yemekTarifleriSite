package classes;

import java.sql.*;

/**
 *
 * @author Halil
 */
public class Database {
    
    private static Stat statClass = new Stat();

    public static final String URL = "jdbc:derby://localhost:1527/addressbook;user=APP;password=APP";
    public static final String USER = "APP";
    public static final String PASS = "APP";

    
    
    public static Statement getCreatedStatement() throws SQLException{
        return statClass.getCreatedStatement();
    }
    
    public static PreparedStatement getPreparedStatement(String query) throws SQLException{
        return statClass.getPreparedStatement(query);
    }
    
    public static void main(String[] args) {
        String query = "SELECT * FROM users";
        
            try(Statement stat = Database.getCreatedStatement()){
                stat.executeQuery(query);
            }
            catch(SQLException exp){
                System.out.println(exp.toString());
            }
    }
    
    /*CREATE TABLE genders (id INTEGER, gender VARCHAR(1) FOREIGN KEY(id) REFERENCES users(id))*/
    
    /*CREATE TABLE emails (id INTEGER, email VARCHAR(40), FOREIGN KEY(id) REFERENCES users(id))*/
    
    
    
    /*CREATE TABLE passwords (id INTEGER, pass VARCHAR(40), FOREIGN KEY(id) REFERENCES users(id))*/
    
    
    /*
    CREATE TABLE recipes (id INTEGER , recipeId INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ,foodName VARCHAR(40), recipe VARCHAR(500), FOREIGN KEY(id) REFERENCES users(id))
    */
    
    
    /*
    CREATE TABLE blogs (id INTEGER, ,blogId recipeId INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ,title VARCHAR(40), description VARCHAR(500), FOREIGN KEY(id) REFERENCES users(id))
    */
    


}
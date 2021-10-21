/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.*;

/**
 *
 * @author Halil
 */
public class Stat implements AutoCloseable{

    private Connection conn;
    private Statement createdStatement;
    private PreparedStatement preparedStatement;


    public Statement getCreatedStatement() throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException exp) {
            System.out.println(exp.toString());
        }
        this.conn = DriverManager.getConnection(Database.URL);
        this.createdStatement = conn.createStatement();
        return this.createdStatement;
    }
    
    public PreparedStatement getPreparedStatement(String query) throws SQLException{
          try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException exp) {
            System.out.println(exp.toString());
        }
          
          this.conn = DriverManager.getConnection(Database.URL);
          this.preparedStatement = conn.prepareStatement(query);
          return this.preparedStatement;
    }
          
    
    @Override
     public void close() throws SQLException {
        if(this.createdStatement != null){
        this.createdStatement.close();
        }
        
        if(this.preparedStatement != null){
            this.preparedStatement.close();
        }
        
        if(this.conn != null){
            this.conn.close(); 
        } 
    }
}

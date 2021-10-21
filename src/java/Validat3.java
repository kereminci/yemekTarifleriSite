/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Halil
 */

import classes.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import javax.faces.validator.Validator;

@FacesValidator("registerUsernameValidator")
public class Validat3 implements Validator{
    private static String username;
    @Override
    public void validate(FacesContext facesContext,
            UIComponent component, Object value)
            throws ValidatorException {
        username = value.toString();
        String query = "SELECT COUNT(*) as count FROM users WHERE username = ? fetch first 1 rows only";
        
        try(PreparedStatement stat = Database.getPreparedStatement(query)){
            stat.setString(1, username);
            ResultSet rs = stat.executeQuery();
            rs.next();
            if(rs.getInt("count") == 1){
                FacesMessage msg = new FacesMessage("Bu kullanıcı adı alınmış");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
                
            }
            
                
        }
        catch (SQLException exp){
            FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                                    exp.toString()));
        }
        
        
        
    }
    
}

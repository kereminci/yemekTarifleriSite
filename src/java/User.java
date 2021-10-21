
import javax.faces.bean.ManagedBean;
import classes.Database;
import java.sql.*;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;

@FacesValidator("passwordCheckValidator")
@ManagedBean(name = "user")    // Using ManagedBean annotation  
@SessionScoped // Using Scope annotation  

public class User implements Validator {

    private String username;
    private String password;
    private String gender;
    private String mail;
    private String passwordAgain;
    private String error;
    private String newUsername;
    private String newMail;
    private String newPassword;
    private String newGender;
    private int id;
    private boolean isLoggedIn;

    public String getNewGender() {
        return newGender;
    }

    public void setNewGender(String newGender) {
        if(!newGender.equals(""))
        this.newGender = newGender;
    }
    

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

   

   
    public String getNewMail() {
        return newMail;
    }

    public void setNewMail(String newMail) {
        if(!newMail.equals("")){
          this.newMail = newMail;  
        }  
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        if(!newPassword.equals(""))
        this.newPassword = newPassword;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        if(!newUsername.equals(""))
        this.newUsername = newUsername;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    @Override
    public void validate(FacesContext facesContext,
            UIComponent component, Object value)
            throws ValidatorException {
        String passwordL = value.toString();

        if (!passwordL.equals(Validat.password)) {
            FacesMessage msg = new FacesMessage("Şifreler uyuşmuyor");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public User() {
        this.isLoggedIn = false;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
   

    public void register() {
        String insertIntoUsers = "INSERT INTO users VALUES(DEFAULT, ?)";
        String insertIntoEmails = "INSERT INTO emails VALUES((SELECT id FROM users WHERE username = ?), ?)";
        String insertIntoGender = "INSERT INTO genders VALUES((SELECT id FROM users WHERE username = ?), ?)";
        String insertIntoPasswords = "INSERT INTO passwords VALUES((SELECT id FROM users WHERE username = ?), ?)";
        
        try (PreparedStatement stmt = Database.getPreparedStatement(insertIntoUsers)) {
            stmt.setString(1, this.username);
            stmt.executeUpdate();
        } catch (SQLException exp) {
            this.error = exp.toString();
        }
        
        
        
        
        try (PreparedStatement stmt = Database.getPreparedStatement(insertIntoEmails)) {
            stmt.setString(1, this.username);
            stmt.setString(2, this.mail);
            stmt.executeUpdate();
        } catch (SQLException exp) {
            this.error = exp.toString();
        }
        
        
        try (PreparedStatement stmt = Database.getPreparedStatement(insertIntoGender)) {
            stmt.setString(1, this.username);
            stmt.setString(2, this.gender.equals("male") ? "M" : "F");
            stmt.executeUpdate();
        } catch (SQLException exp) {
            this.error = exp.toString();
        }
        
        
        try (PreparedStatement stmt = Database.getPreparedStatement(insertIntoPasswords)) {
            stmt.setString(1, this.username);
            stmt.setString(2, this.password);
            stmt.executeUpdate();
        } catch (SQLException exp) {
            this.error = exp.toString();
        }
        
        
    }

    public String login() {
        String query = "SELECT users.id , genders.gender , emails.email FROM users INNER JOIN passwords ON "
                + "users.id = passwords.id INNER JOIN genders ON  genders.id = users.id INNER JOIN emails ON  emails.id = users.id"
                + " WHERE username = ? AND passwords.pass = ? fetch first 1 rows only";
        
        try (PreparedStatement stat = Database.getPreparedStatement(query)) {
            stat.setString(1, this.username);
            stat.setString(2, this.password);

            try (ResultSet rs = stat.executeQuery()) {

                if (rs.next()) {
                    this.id = rs.getInt(1);
                    this.gender = rs.getString(2);
                    this.mail = rs.getString(3);
                    this.isLoggedIn= true;
                    return "index.xhtml";
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kullanıcı adı veya şifre yanlış",
                            "Kullanıcı adı veya şifre yanlış"));
                }

            }

        } catch (SQLException exp) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                            exp.toString()));
           
        }
        return null;
    }

    public void changeUsername() {   //validationunı koymayı unutma

        String query = "UPDATE users SET username = ? WHERE id = ?";

        try (PreparedStatement stat = Database.getPreparedStatement(query)) {
            stat.setString(1, this.newUsername);
            stat.setInt(2, this.id);
            stat.executeUpdate();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Kullanıcı adı başarıyla değiştirildi",
                            "Kullanıcı adı başarıyla değiştirildi"));
        } catch (SQLException exp) {          
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                            exp.toString()));
        }
    }
    
    
    public void changeGender(){
        String query = "UPDATE genders SET gender = ?  WHERE id = ?";
        try(PreparedStatement stat = Database.getPreparedStatement(query)){
            stat.setString(1, this.newGender.equals("male") ? "M" : "F");
            stat.setInt(2, this.id);
            stat.executeUpdate();
        }
        catch(SQLException exp){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                            exp.toString()));
        }
    }

    public void changeEmail() {
        String query = "UPDATE emails SET email = ?  WHERE id = ?";

        try (PreparedStatement stat = Database.getPreparedStatement(query)) {
            stat.setString(1, this.newMail);
            stat.setInt(2, this.id);
            stat.executeUpdate();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Email başarıyla değiştirildi",
                            "Email başarıyla değiştirildi"));
        } catch (SQLException exp) {
            System.out.println(exp.toString());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                            exp.toString()));
        }

    }

    public void changePassword() {
        String query = "UPDATE passwords SET pass = ? WHERE id = ?";

        try (PreparedStatement stat = Database.getPreparedStatement(query)) {
            stat.setString(1, this.newPassword);
            stat.setInt(2, this.id);
            stat.executeUpdate();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Şifre başarıyla değiştirildi",
                            "Şifre başarıyla değiştirildi"));
        } catch (SQLException exp) {
            System.out.println(exp.toString());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                            exp.toString()));
        }
    }
    

    /* <h:inputHidden id="confirmation" value="#{user.confirmation}" />
    function confirmDelete() {
   
    let confirmation = false;
    
    if (confirm("Hesabınızı silmek istediğinizden emin misiniz?")) {
        confirmation = true;
    } else {
        confirmaiton = false;
    }

    document.getElementById("formId:confirmation").value = confirmation;
}   */
    
    //aynı isim girildiğinde hata mesajı gelmiyor onu düzelt
    public void deleteUser() {
        String deleteUsers = "DELETE FROM users WHERE id = ?";
        String deletePasswords = "DELETE FROM passwords WHERE id = ?";
        String deleteEmail = "DELETE FROM emails WHERE id = ?";
        String deleteGender = "DELETE FROM genders WHERE id = ?";

        this.executeUpdate(deleteUsers);
        this.executeUpdate(deletePasswords);
        this.executeUpdate(deleteEmail);
        this.executeUpdate(deleteGender);
        
        this.logout();
    }
    
    
    public void executeUpdate(String query){
        try (PreparedStatement stat = Database.getPreparedStatement(query)) {
            stat.setInt(1, this.id);
            stat.executeUpdate();
            
        } catch (SQLException exp) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, exp.toString(),
                            exp.toString()));
        }
    }

    public String logout() {
        this.isLoggedIn = false;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

}

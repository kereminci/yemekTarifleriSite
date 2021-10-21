import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import javax.faces.validator.Validator;


@FacesValidator("loginUsernameValidation")
public class Validat2 implements Validator{
    public static String password;
    @Override
    public void validate(FacesContext facesContext,
            UIComponent component, Object value)
            throws ValidatorException {
            password = value.toString();
        }
    }

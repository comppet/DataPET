/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Validators;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Tiago Peres
 */
@ManagedBean(name="EmailValidator")
@RequestScoped
public class EmailValidator {

    public void validarEmail(FacesContext context, UIComponent component, Object value){
        String email = (String) value;
        if (email.isEmpty()) return;
        int tam = email.length();
        // verificacao adicional, pois a linguagem que define os emails válidos não é regular.
        if (tam <= 254){
            int at = email.indexOf("@");
            int tamDominio = tam - at - 1;
            // verificacao adicional, pois a linguagem que define os emails válidos não é regular.
            if (at != -1 && tamDominio <= 252){
                // expressão regular que reconhece um e-mail válido (sem verificação sobre o nro de caracteres do endereço como um todo e do domínio)
                String regex = "[a-zA-Z0-9][\\w-.]{0,63}@[a-zA-Z0-9][a-zA-Z0-9-]{0,62}(\\.[a-zA-Z0-9][a-zA-Z0-9-]{0,62}){1,}";
                if (email.matches(regex)) return;
            }
        }
        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro. O endereço de e-mail não é válido.", null));
    }
}

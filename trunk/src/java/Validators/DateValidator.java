/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Validators;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
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
@ManagedBean(name="DateValidator")
@RequestScoped
public class DateValidator {
    private ArrayList<Date> datas;

    public DateValidator(){
        datas = new ArrayList<Date>();
    }

    public void validarData(FacesContext context, UIComponent component, Object value){
        if (value != null) datas.add((Date) value);
        int n = datas.size();
        if (n > 1)
            if (datas.get(n - 1).before(datas.get(n - 2)))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro. A data final deve ser maior que a data de início.", null));
    }
    
    public void validarUmaData(FacesContext context, UIComponent componen, Object value)
    {
        Calendar hoje = Calendar.getInstance();
        if(value != null) datas.add((Date) value);
        if(value != null) datas.add(hoje.getTime());
        int n = datas.size();
        if(n > 1)
            if (datas.get(n - 1).before(datas.get(n - 2)))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro. A data escolhida é maior do que a data de hoje. ", null));
    }
}

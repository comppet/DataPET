/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package converters;

import Model.Aluno;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author tiago
 */
/* Classe usada exclusivamente pelo picklist do formulário de atividades */
@FacesConverter( value="AlunoConverter" )
public class AlunoConverter implements javax.faces.convert.Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        } else {
            try {
                String[] partes = value.split(":");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                
                 
                Aluno a = new Aluno();
                a.setId(id);
                a.setNome(nome);
                return a;

            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lista de petianos inválida", "Por favor reporte o problema ao administrador do DataPET."));
            }
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // value é um inteiro correspondente ao id do Aluno. Deve ser retornado como String.
        return String.valueOf(value);
    }

}

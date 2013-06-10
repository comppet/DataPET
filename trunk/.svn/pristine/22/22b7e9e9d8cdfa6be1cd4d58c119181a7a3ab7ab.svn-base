/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.DateSelectEvent;

/**
 *
 * @author Tchesco
 */
@ManagedBean(name = "AtividadesController")
@ViewScoped
public class AtividadesController {

    private Date inicio;
    private Date fim;
    private static boolean trocarData = false;
    
    public AtividadesController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        
                
        if(trocarData){
            //System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$ ENTREI AKI NAO PODE SER PRIMEIRA VEZ");
            
            inicio = (Date) session.getAttribute("dataInicioAtividades");
            fim = (Date) session.getAttribute("dataFimAtividades");
        }
        else{
            inicio = new Date();
            fim = new Date();
            System.out.println("########################## AKI EH A PRIMEIRA VEZ");
            GregorianCalendar dataInicial = new GregorianCalendar();
            GregorianCalendar dataFinal = new GregorianCalendar();
            SimpleDateFormat formatador = new SimpleDateFormat("yyyy");
            int ano = dataInicial.get(GregorianCalendar.YEAR);
            dataInicial.set(ano, GregorianCalendar.JANUARY, 1);
            dataFinal.set(ano, GregorianCalendar.DECEMBER, 31);
            //System.out.println("$$$$$$$$$$$$$$$$$$ DATA: " + dataInicial.getTime());
            //System.out.println("$$$$$$$$$$$$$$$$$$ DATAF: " + dataFinal.getTime());
            inicio = dataInicial.getTime();
            fim = dataFinal.getTime();
            session.setAttribute("dataInicioAtividades", dataInicial.getTime());
       session.setAttribute("dataFimAtividades", dataFinal.getTime());
            //System.out.println("$$$$$$$$$$$$$$$$$$### DATA: " + inicio.getDate() + inicio.getMonth() + inicio.getYear());
            //System.out.println("$$$$$$$$$$$$$$$$$$### DATAF: " + fim.getDate() + fim.getMonth() + fim.getYear());
            trocarData = true;
        }
    }
    
     public void alterarData(DateSelectEvent event){
       
       
       FacesContext facesContext = FacesContext.getCurrentInstance();
       HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
       HttpSession session = request.getSession();
       System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%% DATA INICIO: " + inicio.getTime());
       System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%% DATA FIM: " + fim.getTime());
       session.setAttribute("dataInicioAtividades", inicio);
       session.setAttribute("dataFimAtividades", fim);
    }

    public Date getFim() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        inicio = (Date) session.getAttribute("dataFimAtividades");
        return fim;
    }


    public Date getInicio() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        inicio = (Date) session.getAttribute("dataInicioAtividades");
        return inicio;
    }

    public void setFim(Date fim) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("dataFimAtividades", fim);
        this.fim = fim;
    }

    public void setInicio(Date inicio) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("dataInicioAtividades", inicio);
        this.inicio = inicio;
    }

    
    
   
    
}

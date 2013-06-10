/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tiago Peres
 */
@ManagedBean(name = "SessionController")
@SessionScoped
public class SessionController {

    private static Date inicioAno;
    private static Date fimAno;

    public SessionController(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        if (LoginController.getNivelUsuario() == null) {
            session.setAttribute("nivelUsuario", LoginController.VISITANTE);
        }
    }

    private static void preencherDatas() {
        SimpleDateFormat formatoAno = new SimpleDateFormat("yyyy");
        int ano = Integer.parseInt(formatoAno.format(new Date()));
        inicioAno = (new GregorianCalendar(ano, 0, 1)).getTime();
        fimAno = (new GregorianCalendar(ano, 11, 31)).getTime();
    }

    /**
     * @return the inicioAno
     */
    public static Date getInicioAno() {
        if (inicioAno == null) {
            preencherDatas();
        }
        return inicioAno;
    }

    /**
     * @return the fimAno
     */
    public static Date getFimAno() {
        if (fimAno == null) {
            preencherDatas();
        }
        return fimAno;
    }

    /**
     * @return the painel
     */
    public String getPainel() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        System.out.println("Nivel usuario" + (Integer) session.getAttribute("nivelUsuario"));
        if (((Integer) session.getAttribute("nivelUsuario")) == LoginController.ADMIN) {
            System.out.println("Admin");
            return "administrador_painel.xhtml";
        } else if (((Integer) session.getAttribute("nivelUsuario")) == LoginController.CLA) {
            System.out.println("CLA");
            return "cla_painel.xhtml";
        }
        else if (((Integer) session.getAttribute("nivelUsuario")) == LoginController.CLA_MEMBRO) {
            System.out.println("CLA");
            return "cla_user_painel.xhtml";
        } else if (((Integer) session.getAttribute("nivelUsuario")) == LoginController.TUTOR) {
            System.out.println("Tutor");
            return "tutor_painel.xhtml";
        } else if (((Integer) session.getAttribute("nivelUsuario")) == LoginController.ALUNO) {
            System.out.println("Aluno");
            return "aluno_painel.xhtml";
        } else {
            System.out.println("Visitante");
            return "login_painel.xhtml";
        }
    }
    
}

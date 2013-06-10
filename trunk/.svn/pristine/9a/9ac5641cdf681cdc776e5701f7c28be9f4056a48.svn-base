/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.Conexao;
import Dao.JDBCAdministradorDao;
import Dao.JDBCAlunoDao;
import Dao.JDBCClaDao;
import Dao.JDBCTicketDao;
import Dao.JDBCTutorDao;
import Dao.TicketDao;
import Dao.UsuarioDao;
import Model.Aluno;
import Model.Ticket;
import Model.Tutor;
import Model.Usuario;
import converters.Criptografia;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.mail.EmailException;

/**
 * Esta é uma classe de controle responsável pela recuperacao de senha
 * de usuarios.
 *
 * @author Douglas
 * @version 3.0
 * @since 3.0
 */

@ManagedBean(name = "RecuperarSenhaController")
@ViewScoped
public class RecuperarSenhaController {

    private TicketDao dao;
    private EmailController emailController;
    private Ticket ticket;
    private String senhaConfirmada;
    private int nivelUsuario;
    private String codigo;

    /**
     * Construtor padrão da classe. Inicializa as variáveis.
     *
     * @author Douglas
     * @throws SQLException, EmailException
     * @version 3.0
     * @since 3.0
     */
    public RecuperarSenhaController() throws SQLException, EmailException {
        //reset();
        dao = new JDBCTicketDao();
        emailController = new EmailController();
        ticket = new Ticket();
    }

    /*public void reset() throws EmailException, SQLException {
        dao = new JDBCTicketDao();
        send = new EmailController();
    }*/

    /**
     * Gera codigo de caracteres utilizado na url de recuperação de senha.
     *
     * @author Douglas
     * @version 3.0
     * @since 3.0
     * @return codigo gerado
     */
    private String gerarCodigo(){
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder(32);
        for (int i = 0; i < 32; i++)
            code.append(caracteres.charAt((int) (Math.random() * (caracteres.length() - 0.0000001))));
        return code.toString();
    }

    /**
     * Salva no banco de dados argumentos necessarios para que o usuario
     * possa recuperar sua senha.
     *
     * @author Douglas
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    public void salvar() throws UnsupportedEncodingException, SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        Connection conexao = Conexao.getConnection();
        conexao.setAutoCommit(false);

        try{
            dao.setConexao(conexao);
            UsuarioDao udao;
            switch(nivelUsuario){
                case LoginController.ADMIN:
                    udao = new JDBCAdministradorDao(conexao);
                    break;
                case LoginController.ALUNO:
                    udao = new JDBCAlunoDao(conexao);
                    break;
                case LoginController.CLA:
                    udao = new JDBCClaDao(conexao);
                    break;
                default:
                    udao = new JDBCTutorDao(conexao);  
            }
            ticket.setUsuario((Usuario) udao.recuperarPorEmail(ticket.getUsuario().getEmail()));
            //System.out.println(email);
            //code = Criptografia.md5(((Integer)id).toString());
            if (ticket.getUsuario() != null) {
                ticket.setHorario(new Date());
                String code = gerarCodigo();
                ticket.setCodigo(code);
                dao.salvar(ticket);

                // envia o email com o código
                // encontra o endereço do sistema
                HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                String url = request.getRequestURL().toString();
                /* separa o endereço anterior ao nome da view do usuário do restante.
                 * A view do usuário será sempre "esqueceu_senha" ao chamar este método.
                 */
                String partes[] = url.split("/esqueceu_senha"); 
                String systemAdress = partes[0];

                // encontra o nome do usuário
                String nome;
                switch(nivelUsuario){
                    case LoginController.ADMIN:
                        nome = "administrador do DataPET";
                        break;
                    case LoginController.ALUNO:
                        nome = ((Aluno) ticket.getUsuario()).getNome();
                        break;
                    case LoginController.CLA:
                        nome = "CLA";
                        break;
                    case LoginController.TUTOR:
                        nome = ((Tutor) ticket.getUsuario()).getNome();
                        break;
                    default:
                        nome = "usuário";
                }

                emailController.setNome("DataPET");
                emailController.setAssunto("Recuperação de Senha - DataPET");
                emailController.setMensagem("Olá " + nome + ", para alterar sua senha clique no link abaixo, ou copie e cole "
                        + "a URL abaixo em seu navegador:\n" + systemAdress + "/recuperacao_senha.xhtml?code="
                        + code + " .\n\nObrigado");
                ArrayList<String> destinatarios = new ArrayList<String>();
                destinatarios.add(ticket.getUsuario().getEmail());
                emailController.setDestinatarios(destinatarios);
                emailController.setOrigem("datapetcomppet@gmail.com");
                emailController.setNotificacao("Pronto! Continue o processo de recuperação de senha "
                        + "verificando a mensagem enviada para seu e-mail.");
                try {
                    emailController.enviaEmail();
                    conexao.commit();
                } catch (EmailException ex) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao enviar o e-mail. Por favor, tente mais tarde." +
                    "Caso o problema persista cantacte o administrador do sistema.",
                    null));
                    Logger.getLogger(RecuperarSenhaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "O endereço de e-mail informado não está cadastrado no sistema.", null));
                ticket = new Ticket();
            }
        } catch(SQLException ex){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                    "Caso o problema persista cantacte o administrador do sistema.",
                    null));
            Logger.getLogger(RecuperarSenhaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            conexao.setAutoCommit(true);
            conexao.close();
        }
    }

    /**
     * Recupera o ticket necessario para proseguir com a recuperacao de senha.
     *
     * @author Douglas
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    public void carregarTicket() throws SQLException{
        dao.setConexao(Conexao.getConnection());
        ticket = dao.recuperarPorCodigo(codigo);
        dao.getConexao().close();
    }

    /**
     * Altera no banco de dados a senha do usuario.
     *
     * @author Douglas
     * @throws SQLException, UnsupportedEncodingException
     * @version 3.0
     * @since 3.0
     * @return página seguinte
     */
    public String alterarSenha() throws SQLException, UnsupportedEncodingException {
        FacesContext context = FacesContext.getCurrentInstance();
        String view = "";
        if (ticket.getUsuario().getSenha().equals(senhaConfirmada)) {
            dao.setConexao(Conexao.getConnection());
            dao.getConexao().setAutoCommit(false);
            try{
                ticket.getUsuario().setSenha(Criptografia.md5(senhaConfirmada));
                UsuarioDao udao = new JDBCAdministradorDao(dao.getConexao());
                udao.editar(ticket.getUsuario());
                dao.excluir(ticket.getId());
                dao.getConexao().commit();
                context.getExternalContext().getFlash().setKeepMessages(true);
                context.addMessage(null, new FacesMessage("Senha Alterada com sucesso. Utilize o painel de login para entrar no sistema."));
                view = "index?faces-redirect=true";
            } catch(SQLException ex){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                    "Caso o problema persista cantacte o administrador do sistema.",
                    null));
                Logger.getLogger(RecuperarSenhaController.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                dao.getConexao().setAutoCommit(true);
                dao.getConexao().close();
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Os campos de senha não possuem o mesmo valor.", null));
        }
        return view;
    }

    /**
     * @author Douglas
     * @version 3.0
     * @since 3.0
     * @return ticket ticket em questão
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Define o ticket a ser utilizado por este controller.
     *
     * @author Douglas
     * @version 3.0
     * @since 3.0
     * @param ticket ticket em questão
     */
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * @author Douglas
     * @version 3.0
     * @since 3.0
     * @return senhaConfirmada senhaConfirmada em questão
     */
    public String getSenhaConfirmada() {
        return senhaConfirmada;
    }

    /**
     * Define a senha a ser utilizado por este controller.
     *
     * @author Douglas
     * @version 3.0
     * @since 3.0
     * @param senhaConfirmada senhaConfirmada em questão
     */
    public void setSenhaConfirmada(String senhaConfirmada) {
        this.senhaConfirmada = senhaConfirmada;
    }

    /**
     * @author Douglas
     * @version 3.0
     * @since 3.0
     * @return nivelUsuario nivelUsuario em questão
     */
    public int getNivelUsuario() {
        return nivelUsuario;
    }

    /**
     * Define o nivel de usuario a ser utilizado por este controller.
     *
     * @author Douglas
     * @version 3.0
     * @since 3.0
     * @param nivelUsuario nivelUsuario em questão
     */
    public void setNivelUsuario(int nivelUsuario) {
        this.nivelUsuario = nivelUsuario;
    }

    /**
     * @author Douglas
     * @version 3.0
     * @since 3.0
     * @return codigo codigo em questão
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define o codigo a ser utilizado por este controller.
     *
     * @author Douglas
     * @version 3.0
     * @since 3.0
     * @param codigo codigo em questão
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}

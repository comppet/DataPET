package Controller;

import Dao.Conexao;
import Dao.UsuarioDao;
import Model.Usuario;
import javax.faces.context.FacesContext;
import converters.Criptografia;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import org.apache.commons.mail.EmailException;

/** 
* @author Pedro Augusto
* @version 3.0
* @since 3.0
*/
public abstract class UsuarioController<Tuser extends Usuario, Tdao extends UsuarioDao<Tuser>> {

    private Tdao dao;
    private Tuser usuario;

    
    /** 
    * Construtor da classe.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    */
    public UsuarioController() throws SQLException {
        
    }

    
    /** 
    * Salva um usuário criptografando sua senha.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    */
    public void salvar() throws SQLException {
        usuario.setSenha(Criptografia.randomMd5());
        dao.salvar(usuario);
    }

    
    /** 
    * Prepara a edição de um usuário
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    */
    public void prepararEdicao() throws SQLException {
        getDao().setConexao(Conexao.getConnection());
        setUsuario(dao.recuperarPorId(usuario.getId()));
        getDao().getConexao().close();
    }

    
    /** 
    * Editar um usuário.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return String Vazia para edição.
    */
    public String editar() throws SQLException {
        dao.editar(usuario);
        return "";
    }

    
    /** 
    * Mostra o texto de boas vindas ao usuário, depois que este faz login no sistema.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param Nome do usuário, senha e destinatários
    */
    public static void boasVindas(String nome, String senha, ArrayList<String> destinatarios) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            EmailController email = new EmailController(); //cria conexão com servidor de emails
            email.setAssunto("Seja bem vindo ao DataPET"); //assunto do email
            email.setDestinatarios(destinatarios);
            email.setMensagem("Seja bem vindo(a) ao DataPET " + nome + "!\nSua senha é: " + senha);
            email.setNome("");
            email.setOrigem("datapetcomppet@gmail.com");
            email.setNotificacao("Senha enviada com sucesso para o e-mail do usuário!");
            email.enviaEmail();
        } catch (EmailException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Não foi possível enviar a senha do usuário por e-mail.",
                    null));
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /** 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @retur o usuário no sistema.
    */
    public Tuser getUsuario() {
        return usuario;
    }

    /** 
    * Modifica o dao do usuário.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param Novo dao.
    */
    protected void setDao(Tdao dao) {
        this.dao = dao;
    }

    
    /** 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @retur O dao.
    */
    protected Tdao getDao() {
        return dao;
    }

    /** 
    * Muda o usuário.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param novo usuário.
    */
    public void setUsuario(Tuser usuario) {
        this.usuario = usuario;
    }

}

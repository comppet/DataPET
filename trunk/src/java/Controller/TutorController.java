/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Dao.Conexao;
import Dao.GrupoDao;
import Dao.JDBCGrupoDao;
import Dao.JDBCTutorDao;
import Dao.TutorDao;
import Model.Grupo;
import Model.Instituicao;
import Model.Tutor;
import Model.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * Esta é uma classe de controle que realiza a interação entre a view,
 * o javabean "Tutor" e o banco de dados.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
@ManagedBean(name="TutorController")
@RequestScoped
public class TutorController extends PetianoController<Tutor, TutorDao> {

    public TutorController() throws SQLException{
        super();
        setUsuario(new Tutor());
        setDao(new JDBCTutorDao());
    }

    /**
     * Verifica o id da instituição para certificar se o usuário tem permissão
     * para executar uma determinada ação.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return true, se é seguro. false se não é seguro
     */
    private boolean isSeguro(){
        Usuario uLogado = LoginController.getUsuario();
        Instituicao iLogada = LoginController.getInstituicao();
        try{
            GrupoDao gdao = new JDBCGrupoDao(Conexao.getConnection());
            Grupo gTutor = gdao.recuperarPorId(getUsuario().getGrupo().getId());
            gdao.getConexao().close();
            Instituicao instituicaoTutor = gTutor.getInstituicao();
            return (uLogado.equals(getUsuario()) ||
                   (LoginController.isCLA() && iLogada.equals(instituicaoTutor)));
        } catch(SQLException ex){
            Logger.getLogger(TutorController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch(NullPointerException ex){
            return false;
        }
    }

    /**
     * Insere no banco de dados um tutor com os dados no atributo "usuario".
     *
     * @author Pedro Augusto
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    @Override
    public void salvar() throws SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        getUsuario().setGrupo(LoginController.getGrupo());
        getDao().setConexao(Conexao.getConnection());
        getDao().getConexao().setAutoCommit(false);

        try{      
            super.salvar();
            getDao().getConexao().commit();
            context.addMessage(null, new FacesMessage("O tutor foi cadastrado com sucesso!"));
            
            // envia e-mail de boas vindas
            ArrayList<String> destinatarios = new ArrayList<String>(); //define destino para o email
            destinatarios.add(getUsuario().getEmail());
            boasVindas(getUsuario().getNome(), getUsuario().getSenha(), destinatarios);
        } catch(SQLException ex){
            if (ex.getErrorCode() == 1062)
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "O tutor não foi cadastrado. O endereço de e-mail informado já existe no sistema.",
                        null));
            else
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                        "Caso o problema persista cantacte o administrador do sistema.",
                        null));
            Logger.getLogger(TutorController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            getDao().getConexao().setAutoCommit(true);
            getDao().getConexao().close();
        }
    }

    /**
     * Modifica no banco de dados as informações do tutor que tem seus dados no atributo "usuario".
     *
     * @author Pedro Augusto
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return página do sistema para qual o usuário em sessão será redirecionado após a operação.
     */
    @Override
    public String editar() throws SQLException {
        if (!isSeguro()) return "403";
        
        FacesContext context = FacesContext.getCurrentInstance();
        String view;
        getDao().setConexao(Conexao.getConnection());

        try{
            view = super.editar();
            getDao().getConexao().commit();
            if (getUsuario().getDataSaidaPet() == null)
                context.addMessage(null, new FacesMessage("As informações do tutor foram alteradas com sucesso!"));
            else
                context.addMessage(null, new FacesMessage("A operação de desligamento do tutor foi realizada com sucesso!!"));
        } catch(SQLException ex){
            if (ex.getErrorCode() == 1062)
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "O endereço de e-mail informado já existe no sistema.",
                    null));
            else
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                        "Caso o problema persista cantacte o administrador do sistema.",
                        null));
            view = "";
            Logger.getLogger(TutorController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            getDao().getConexao().close();
        }
        return view;
    }

    /**
     * Recupera o tutor vigente do grupo que tem o id passado como parâmetro.
     *
     * @author Pedro Augusto
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idGrupo id do Grupo o qual terá seu tutor vigente recuperado.
     * @return tutor vigente do grupo que tem o id passado como parâmetro.
     */
    public Tutor getTutorByGrupo(int idGrupo) throws SQLException{
        getDao().setConexao(Conexao.getConnection());
        Tutor tutor = getDao().recuperarTutorAtivo(idGrupo);
        getDao().getConexao().close();
        return tutor;
    }

}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor
 */
package Controller;

import Dao.AdministradorDao;
import Dao.Conexao;
import Dao.JDBCAdministradorDao;
import Model.Administrador;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * Esta é uma classe de controle que realiza a interação entre a view,
 * o javabean "Administrador" e o banco de dados.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
@ManagedBean(name = "AdministradorController")
@RequestScoped
public class AdministradorController extends UsuarioController<Administrador, AdministradorDao> {

    /**
     * Construtor padrão da classe. Inicializa as variáveis.
     *
     * @author Pedro Augusto
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    public AdministradorController() throws SQLException {
        super();
        setDao(new JDBCAdministradorDao());
        setUsuario(new Administrador());
    }

    /**
     * Lança a exceção "UnsupportedOperationException", indicando que a operação de salvar não se aplica
     * a um administrador, sendo essa operação sendo apenas realizada diretamente no banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    @Override
    public void salvar() throws SQLException {
        throw new UnsupportedOperationException("Não é possível salvar um administrador "
                + "através deste sistema, tente manipular o banco de dados diretamente.");
    }

    /**
     * Edita o e-mail do administrador, retornando a página do sistema para qual o usuário será redirecionado.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return página do sistema para qual o usuário será redirecionado.
     */
    @Override
    public String editar() throws SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        String view = null;
        getDao().setConexao(Conexao.getConnection());
        getDao().getConexao().setAutoCommit(false);
        try {
            view = super.editar();
            /*** COMMIT ***/
            getDao().getConexao().commit();
            context.addMessage(null, new FacesMessage("Dados alterados com sucesso!"));
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "O endereço de e-mail informado já existe no sistema.",
                        null));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde."
                        + "Caso o problema persista contacte o administrador do sistema.",
                        null));
            }
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            getDao().getConexao().setAutoCommit(true);
            getDao().getConexao().close();
        }
        return view;
    }
}

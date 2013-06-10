/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Administrador;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
    /**
     * Classe que opera informaçes do administrador no banco de dados.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @return :: Identificador para o próximo cla.
     * @param :: Um usuário cla.
     */
public class JDBCAdministradorDao extends JDBCUsuarioDao<Administrador> implements AdministradorDao {

    /**
     * Construtor da classe.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @param :: Uma conexão com o banco de dados.
     */
    public JDBCAdministradorDao(Connection conn) throws SQLException{
        super(conn);
    }
    
    
    /**
     * Construtor da classe.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     */
    public JDBCAdministradorDao() throws SQLException{
        super(null);
    }

    
    /**
     * Recuperar um administrador no banco de dados.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @return :: Execeção de erro, pois não pode recuperar um administrador por Id.
     * @param :: Id de usuário.
     */
    @Override
    public Administrador recuperarPorId(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    /**
     * Recupera o administrador no banco de dados.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @return :: O administrador.
     */
    @Override
    public Administrador recuperarAdministrador() throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select u.id, email, senha "
                + "from usuario as u, administrador as a "
                + "where u.id = a.id";
        ResultSet rs = st.executeQuery(sql);
        Administrador adm = null;
        if (rs.next()) {
            adm = new Administrador();
            preencher(adm, rs);
        }
        st.close();
        return adm;
    }

    
    /**
     * Recuperar o administrador através do e-mail.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @return :: O administrador.
     * @param :: E-mail.
     */
    @Override
    public Administrador recuperarPorEmail(String email) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select u.id, email, senha "
                + "from usuario as u, administrador as a "
                + "where u.id = a.id and email = '" + email + "'";
        ResultSet rs = st.executeQuery(sql);
        Administrador adm = null;
        if (rs.next()) {
            adm = new Administrador();
            preencher(adm, rs);
        }
        st.close();
        return adm;
    }
    
    
    /**
     * Salva um administrador no banco de dados.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @return :: Mensagem de erro.
     * @param :: Um administrador.
     */
    @Override
    public int salvar(Administrador a){
        throw new UnsupportedOperationException("Nao é possível adicionar um administrador "
                + "pelo sistema. Tente fazê-lo diretamente no banco de dados.");
    }
}

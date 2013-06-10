/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Esta é uma classe abstrata responsavel por fazer operações no banco de dados 
 * para buscar informações sobre o Usuário.
 *
 * @author Pedro Augusto
 * @version 3.0
 * @since 3.0
 */
public abstract class JDBCUsuarioDao<T extends Usuario> extends JDBCDao<T> implements UsuarioDao<T> {

    /**
     * Construtor da classe.
     *
     * @author Pedro Augusto
     * @version 3.0
     * @since 3.0
     * @param :: Deve receber uma conexão com o banco de dados.
     */
    public JDBCUsuarioDao(Connection conn) {
        super(conn);
    }

    
    /**
     * Salva um usuário no banco de dados.
     *
     * @author Pedro Augusto
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @return :: Identificador para o próximo usuário.
     * @param :: Um usuário.
     */
    @Override
    public int salvar(T usuario) throws SQLException {
        Usuario u = (Usuario) usuario;
        Statement st = getConexao().createStatement();
        ResultSet rs = st.executeQuery("SHOW TABLE STATUS LIKE 'usuario'");
        rs.next();
        int proximoId = rs.getInt("Auto_increment");
        st.close();
        String sql = "insert into usuario values (null, ?, md5(?))";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, u.getEmail());
        pst.setString(2, u.getSenha());
        pst.executeUpdate();
        pst.close();
        return proximoId;
    }

    /**
     * Edita um usuário no banco de dados.
     *
     * @author Pedro Augusto
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @param :: Um usuário.
     */
    @Override
    public void editar(T usuario) throws SQLException {
        Usuario u = (Usuario) usuario;
        String sql = "update usuario set email = ?, senha = ? where id = ?";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, u.getEmail());
        pst.setString(2, u.getSenha());
        pst.setInt(3, u.getId());
        pst.executeUpdate();
        pst.close();
    }

     /**
     * Preenche as informações de um usuário no banco de dados.
     *
     * @author Pedro Augusto
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @param :: Um usuário, e uma consulta no banco.
     */
    protected void preencher(Usuario u, ResultSet rs) throws SQLException {
        u.setId(rs.getInt("id"));
        u.setEmail(rs.getString("email"));
        u.setSenha(rs.getString("senha"));
    }

    
     /**
     * Exclui um usuário no banco de dados.
     *
     * @author Pedro Augusto
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @return :: Retorna uma mensagem de que usuários não podem ser excluídos.
     * @param :: Id de um usuário.
     */
    @Override
    public void excluir(int id) throws SQLException {
        throw new UnsupportedOperationException("Não é possível excluir um usuário. "
                + "Tente desligar o usuário (definir uma data de saída) caso ele "
                + "seja um petiano.");
    }

    
    /**
     * Verifica o e-mail de um usuário no banco de dados.
     *
     * @author Pedro Augusto
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @return :: True se existir e False se não existir.
     * @param :: Um e-mail de usuário.
     */
    @Override
    public boolean existeEmail(String email) throws SQLException {
        String sql = "select id from usuario where email = ?";
        PreparedStatement pst = Conexao.getConnection().prepareStatement(sql);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        if (rs.next())
            return true;
        else
            return false;
    }
}

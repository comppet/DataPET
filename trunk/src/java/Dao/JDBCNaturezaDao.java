/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Grupo;
import Model.Natureza;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Esta é uma classe que realiza operações no banco de dados
 * relacionada as naturezas, é subclasse de JDBCDao e implementa
 * a interface NaturezaDAO.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public class JDBCNaturezaDao extends JDBCDao<Natureza> implements NaturezaDao {
    
     /**
     * Construtor da classe que recebe uma conexão de uma classe a nível de Controller.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param Conexão com o banco de dados MySQL, recebida de uma classe a nível de Controller.
     */
    public JDBCNaturezaDao(Connection conn) throws SQLException{
        super(conn);
    }
    
     /**
     * Construtor da classe.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    public JDBCNaturezaDao() throws SQLException{
        super(null);
    }

     /**
     * Recebe uma natureza, parâmetro "natureza", e o insere no banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param natureza que será inserida no banco de dados.
     * @return o próximo id, da sequência de incremento, que terá a próxima natureza a ser inserida.
     */
    @Override
    public int salvar(Natureza natureza) throws SQLException {
        Statement st = getConexao().createStatement();
        ResultSet rs = st.executeQuery("SHOW TABLE STATUS LIKE 'natureza'");
        rs.next();
        int proximoId = rs.getInt("Auto_increment");
        st.close();
        
        String sql = "insert into natureza values(null, ?, ?)";
        //String sql = "insert into natureza values(NULL, " +  + ", '" +  + "')";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, natureza.getGrupo().getId());
        pst.setString(2, natureza.getNome());
        pst.executeUpdate();
        pst.close();
        return proximoId;
    }

     /**
     * Recebe uma natureza, parâmetro "natureza", já existente e modifica suas informações no
     * banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param natureza que terá suas informações alteradas.
     */
    @Override
    public void editar(Natureza natureza) throws SQLException {
        String sql = "update natureza set nome = ? where id = ?";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, natureza.getNome());
        pst.setInt(2, natureza.getId());
        pst.executeUpdate();
        pst.close();
    }

    
    @Override
    public void excluir(int id) throws SQLException {
        Statement st = getConexao().createStatement();
        Statement st2 = getConexao().createStatement();
        String sql =
                " delete from natureza where id = " + id;
        String sql2 = "update atividadepublica set id_natureza = 1 where id_natureza = " + id + ";";
        st2.executeUpdate(sql2);
        st.executeUpdate(sql);
        st.close();
    }

     /**
     * povoa um objeto "Natureza" passado como parâmetro ("n") com as informações contidas no
     * parâmetro do tipo "ResultSet" ("rs") que contém as informações da natureza recuperada do
     * banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param n objeto "Natureza" que será povoado com as informações de uma natureza recuperada do banco de dados.
     *        rs objeto que contém as informações da natureza recuperada do banco de dados.
     */
    private void preencher(Natureza n, ResultSet rs) throws SQLException {
        Grupo g = new Grupo();
        g.setId(rs.getInt("id_grupo"));
        
        n.setId(rs.getInt("id"));
        n.setGrupo(g);
        n.setNome(rs.getString("nome"));
    }

    /**
     * Busca uma natureza do banco de dados, que tenha o id passado como parâmetro.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param id da natureza a ser recuperado.
     * @return natureza recuperada do banco de dados.
     */
    @Override
    public Natureza recuperarPorId(int id) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select * from natureza where id = " + id;
        ResultSet rs = st.executeQuery(sql);
        Natureza n = null;
        
        if (rs.next()) {
            n = new Natureza();
            preencher(n, rs);
        }
        
        st.close();
        return n;
    }

     /**
     * Carrega do banco de dados uma lista de todos as naturezas.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idGrupo id do grupo PET.
     * @return Lista de todas as naturezas do grupo PET passado como parâmetro.
     */
    @Override
    public ArrayList<Natureza> listarTodos(int idGrupo) throws SQLException {
        ArrayList<Natureza> naturezas = new ArrayList<Natureza>();
        // consulta que retorna todas as naturezas
        Statement st = getConexao().createStatement();
        String sql = "select * from natureza where id_grupo = " + idGrupo;
        ResultSet rs = st.executeQuery(sql);

        // cria os objetos "Natureza" a partir do resultado da busca
        while (rs.next()) {
            Natureza n = new Natureza();
            preencher(n, rs);
            naturezas.add(n);
        }
        
        st.close();
        return naturezas;
    }
}

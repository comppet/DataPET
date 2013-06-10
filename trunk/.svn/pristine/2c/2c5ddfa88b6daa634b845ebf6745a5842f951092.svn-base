/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Grupo;
import Model.GrupoHistorico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Esta é uma classe que realiza operações no banco de dados
 * relacionada às operações de desativação e reativação sofridas por um grupo PET,
 * é subclasse de JDBCDao e implementa a interface GrupoHistoricoDAO.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public class JDBCGrupoHistoricoDao extends JDBCDao<GrupoHistorico> implements GrupoHistoricoDao {

    /**
     * Construtor da classe que recebe uma conexão de uma classe a nível de Controller.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param Conexão com o banco de dados MySQL, recebida de uma classe a nível de Controller.
     */
    public JDBCGrupoHistoricoDao(Connection conn) throws SQLException {
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
    public JDBCGrupoHistoricoDao() throws SQLException {
        super(null);
    }

    /**
     * Recebe uma operação de desativação ou reativação de um grupo PET e o insere no banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param historico operação de desativação ou reativação sofrida por um grupo PET.
     * @return o próximo id, da sequência de incremento, que terá a próxima operação (desativação ou reativação)
     * sofrida por um grupo PET.
     */
    @Override
    public int salvar(GrupoHistorico historico) throws SQLException {
        Statement st = getConexao().createStatement();
        ResultSet rs = st.executeQuery("SHOW TABLE STATUS LIKE 'grupo_historico'");
        rs.next();
        int proximoId = rs.getInt("Auto_increment");
        st.close();

        String sql = "insert into grupo_historico values (null, ?, ?, ?, ?)";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, historico.getGrupo().getId());
        pst.setInt(2, historico.getOperacao());
        pst.setString(3, historico.getJustificativa());
        pst.setDate(4, new java.sql.Date(historico.getData().getTime()));
        pst.executeUpdate();
        pst.close();
        return proximoId;
    }

    /**
     * Recebe o id de uma operação de desativação ou reativação feita por um grupo PET e a
     * recupera do banco de dados criando um objeto "GrupoHistorico" com as informações
     * dessa operação.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param id id da operação de desativação ou reativação de um grupo PET.
     * @return um objeto "GrupoHistorico" que contém as informações de uma operação de
     * desativação ou reativação.
     */
    @Override
    public GrupoHistorico recuperarPorId(int id) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select * from grupo_historico where id = " + id;
        ResultSet rs = st.executeQuery(sql);
        GrupoHistorico historico = null;
        if (rs.next()) {
            historico = new GrupoHistorico();
            preencher(historico, rs);
        }
        st.close();
        return historico;
    }

    /**
     * Recupera do banco de dados todas as operações de desativação e reativação
     * sofridas pelo grupo PET com id passado por parâmetro, criando uma lista
     * com tais informações que será retornada pelo método.
     *
     * @author Pedro Augusto
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idGrupo que terá o seu histórico recuperado do banco de dados.
     * @return Lista de todas as operações de desativação e reativação sofridas
     * pelo grupo PET com o id passado por parâmetro
     */
    @Override
    public ArrayList<GrupoHistorico> listarTodos(int idGrupo) throws SQLException {
        ArrayList<GrupoHistorico> historico = new ArrayList<GrupoHistorico>();
        Statement st = getConexao().createStatement();
        String sql = "select * from grupo_historico where id_grupo = " + idGrupo;
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            GrupoHistorico hist = new GrupoHistorico();
            preencher(hist, rs);
            historico.add(hist);
        }
        st.close();
        return historico;
    }

    /**
     * povoa um objeto "GrupoHistorico" passado como parâmetro ("historico") com as informações contidas no
     * parâmetro do tipo "ResultSet" ("rs") que contém as informações de uma opearação de desativação ou
     * reativação, de um grupo PET, recuperado do banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param g objeto "historico" que será povoado com as informações de uma opearação de desativação ou
     * reativação de um grupo PET.
     *        rs objeto que contém as informações de uma opearação de desativação ou reativação de um grupo PET.
     */
    private void preencher(GrupoHistorico historico, ResultSet rs) throws SQLException {
        Grupo g = new Grupo();
        g.setId(rs.getInt("id_grupo"));

        historico.setId(rs.getInt("id"));
        historico.setGrupo(g);
        historico.setOperacao(rs.getInt("operacao"));
        historico.setJustificativa(rs.getString("justificativa"));
        historico.setData(rs.getDate("data"));
    }

    /**
     * Apenas lança a exceção "UnsupportedOperationException", informando que a operação de exclusão de
     * uma operação de desativação ou reativação, de um grupo PET, do banco de dados não é suportada pelo sistema.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param id da operação de desativação ou reativação, de um grupo PET, a ser excluído do banco de dados.
     */
    @Override
    public void excluir(int id) throws SQLException {
        throw new UnsupportedOperationException("Não é possível excluir o passado! Impossível excluir o histórico.");
    }

    /**
     * Apenas lança a exceção "UnsupportedOperationException", informando que a operação de edição de
     * uma operação de desativação ou reativação, de um grupo PET, do banco de dados não é suportada pelo sistema.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param id da operação de desativação ou reativação, de um grupo PET, a ser modificado no banco de dados.
     */
    @Override
    public void editar(GrupoHistorico object) throws SQLException {
        throw new UnsupportedOperationException("Não é possível mudar o passado! Impossível editar o histórico.");
    }
}

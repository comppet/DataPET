/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Curso;
import Model.Grupo;
import Model.Instituicao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Esta é uma classe que realiza operações no banco de dados
 * relacionada aos grupos PET, é subclasse de JDBCDao e implementa
 * a interface GrupoDAO.
 *
 * @author Douglas Antunes
 * @version 3.0
 * @since 3.0
 */
public class JDBCGrupoDao extends JDBCDao<Grupo> implements GrupoDao {

    /**
     * Construtor da classe que recebe uma conexão de uma classe a nível de Controller.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param Conexão com o banco de dados MySQL, recebida de uma classe a nível de Controller.
     */
    public JDBCGrupoDao(Connection conn) throws SQLException {
        super(conn);
    }

    /**
     * Construtor da classe.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    public JDBCGrupoDao() throws SQLException {
        super(null);
    }

    /**
     * Recebe um grupo PET, parâmetro "grupo", e o insere no banco de dados.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param grupo grupo PET que será inserido no banco de dados.
     * @return o próximo id, da sequência de incremento, que terá o próximo grupo a ser inserido.
     */
    @Override
    public int salvar(Grupo grupo) throws SQLException {
        Statement st = getConexao().createStatement();
        ResultSet rs = st.executeQuery("SHOW TABLE STATUS LIKE 'grupo'");
        rs.next();
        int proximoId = rs.getInt("Auto_increment");
        st.close();

        String sql = "insert into grupo values (?, ?, ?, ?, ?, ?, ?, ?, ?, true)";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, proximoId);
        pst.setInt(2, grupo.getInstituicao().getId());
        if (grupo.getCurso().getId() == 0) {
            pst.setNull(3, java.sql.Types.INTEGER);
        } else {
            pst.setInt(3, grupo.getCurso().getId());
        }
        pst.setString(4, grupo.getSigla());
        pst.setInt(5, grupo.getTipo());
        pst.setDate(6, new java.sql.Date(grupo.getImplantacao().getTime()));
        pst.setString(7, grupo.getSite());
        pst.setString(8, grupo.getTema());
        pst.setString(9, grupo.getTelefone());
        pst.executeUpdate();
        pst.close();
        return proximoId;
    }

    /**
     * Recebe um grupo PET, parâmetro "grupo", já existente e modifica suas informações no
     * banco de dados.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param grupo grupo PET que terá suas informações alteradas.
     */
    @Override
    public void editar(Grupo grupo) throws SQLException {
        java.sql.Date implantacao = null;
        if (grupo.getImplantacao() != null) {
            implantacao = new java.sql.Date(grupo.getImplantacao().getTime());
        }

        String sql = "update grupo set sigla = ?, tipo = ?, implantacao = ?, site = ?, "
                + "tema = ?, telefone = ?, ativado = ?, id_curso = ? where id = ?";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, grupo.getSigla());
        pst.setInt(2, grupo.getTipo());
        pst.setDate(3, implantacao);
        pst.setString(4, grupo.getSite());
        pst.setString(5, grupo.getTema());
        pst.setString(6, grupo.getTelefone());
        pst.setBoolean(7, grupo.isAtivado());
        if (grupo.getCurso().getId() == 0) {
            pst.setNull(8, java.sql.Types.INTEGER);
        } else {
            pst.setInt(8, grupo.getCurso().getId());
        }
        pst.setInt(9, grupo.getId());
        pst.executeUpdate();
        pst.close();
    }

    /**
     * Busca um grupo PET do banco de dados, que tenha o id passado como parâmetro.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param id do grupo PET a ser recuperado.
     * @return grupo PET recuperado do banco de dados.
     */
    @Override
    public Grupo recuperarPorId(int id) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select * "
                + "from grupo as g "
                + "where g.id = " + id;
        ResultSet rs = st.executeQuery(sql);
        Grupo g = null;
        if (rs.next()) {
            g = new Grupo();
            preencher(g, rs);
        }

        st.close();
        return g;
    }

    /**
     * povoa um objeto "Grupo" passado como parâmetro ("g") com as informações contidas no
     * parâmetro do tipo "ResultSet" ("rs") que contém as informações do grupo PET recuperado do
     * banco de dados.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param g objeto "Grupo" que será povoado com as informações de um grupo PET recuperado do banco de dados.
     *        rs objeto que contém as informações de um grupo PET recuperado do banco de dados.
     */
    protected void preencher(Grupo g, ResultSet rs) throws SQLException {
        Instituicao i = new Instituicao();
        i.setId(rs.getInt("id_instituicao"));
        Curso c = new Curso();
        c.setId(rs.getInt("id_curso"));

        g.setId(rs.getInt("id"));
        g.setInstituicao(i);
        g.setCurso(c);
        g.setSigla(rs.getString("sigla"));
        g.setTipo(rs.getInt("tipo"));
        g.setImplantacao(rs.getDate("implantacao"));
        g.setSite(rs.getString("site"));
        g.setTema(rs.getString("tema"));
        g.setTelefone(rs.getString("telefone"));
        g.setAtivado(rs.getBoolean("ativado"));
    }

    /**
     * Carrega do banco de dados uma lista de todos os grupos PET
     * da instituição passada por parâmetro.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idInstituicao id da instituição que terá todos os seus grupos PET buscados.
     * @return Lista de todos os grupos PET da instituição passada como parâmetro.
     */
    @Override
    public ArrayList<Grupo> listarTodos(int idInstituicao) throws SQLException {
        ArrayList<Grupo> grupos = new ArrayList<Grupo>();
        // consulta que retorna todas as pesquisas e seus campos
        Statement st = getConexao().createStatement();
        String sql = "select * "
                + "from grupo as g "
                + "where id_instituicao = " + idInstituicao;
        ResultSet rs = st.executeQuery(sql);
        // cria os objetos "Grupo" a partir do resultado da busca
        while (rs.next()) {
            Grupo g = new Grupo();
            preencher(g, rs);
            grupos.add(g);
        }

        st.close();
        return grupos;
    }

    /**
     * Apenas lança a exceção "UnsupportedOperationException", informando que a operação de exclusão de
     * um grupo PET do banco de dados não é suportada pelo sistema.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param id do grupo PET a ser excluído do banco de dados.
     */
    @Override
    public void excluir(int id) throws SQLException {
        throw new UnsupportedOperationException("Não é possível excluir um grupo, tente desativá-lo.");
    }

    /**
     * Carrega do banco de dados um grupo PET que tenha a sigla passada como parâmetro "siglaGrupo"
     * e faça parte da instituição passada por parâmetro "idInstituicao".
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param siglaGrupo sigla do grupo que está sendo buscado do banco de dados.
     *        idInstituicao id da instituição que terá o grupo PET recuperado.
     * @return grupo PET que tenha a sigla passada como parâmetro "siglaGrupo"
     * e faça parte da instituição passada por parâmetro "idInstituicao".
     */
    @Override
    public Grupo recuperaPorSigla(String siglaGrupo, int idInstituicao) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select * from grupo where sigla = ? and id_instituicao = ?;";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, siglaGrupo);
        pst.setInt(2, idInstituicao);
        ResultSet rs = pst.executeQuery();
        Grupo grupo = new Grupo();
        if (!rs.next()) {
            grupo = null;
        } else {
            preencher(grupo, rs);
        }
        pst.close();
        return grupo;
    }
}

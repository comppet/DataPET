package Dao;

import Model.Instituicao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Esta é uma classe que realiza operações no banco de dados
 * relacionada as Instituições, é subclasse de JDBCDao e implementa
 * a interface InstituicaoDAO.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public class JDBCInstituicaoDao extends JDBCDao<Instituicao> implements InstituicaoDao {

    /**
     * Construtor da classe que recebe uma conexão de uma classe a nível de Controller.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param Conexão com o banco de dados MySQL, recebida de uma classe a nível de Controller.
     */
    public JDBCInstituicaoDao(Connection conn) throws SQLException {
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
    public JDBCInstituicaoDao() throws SQLException {
        super(null);
    }

    /**
     * Recebe uma Instituição, parâmetro "inst", e o insere no banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param instituição instituição que será inserido no banco de dados.
     * @return o próximo id, da sequência de incremento, que terá a próxima
     * instituição a ser inserida.
     */
    @Override
    public int salvar(Instituicao inst) throws SQLException {
        // descobre o valor do campo id (que e' auto-incrementado) para a proxima insercao
        Statement st = getConexao().createStatement();
        ResultSet rs = st.executeQuery("SHOW TABLE STATUS LIKE 'instituicao'");
        rs.next();
        int proximoId = rs.getInt("Auto_increment");
        st.close();

        // Adiciona a nova instituição no bd
        String sql = "insert into instituicao values (null, ?, ?, ?, ?)";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, inst.getSigla());
        pst.setString(2, inst.getNome());
        pst.setString(3, inst.getProReitor());
        pst.setString(4, inst.getEmailProReitor());
        pst.executeUpdate();
        pst.close();
        return proximoId;
    }

    /**
     * Recebe o id de uma Instituição, parâmetro "id", e o remove do banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idInstituição idInstituição que será removido do banco de dados.
     */
    @Override
    public void excluir(int id) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "delete from instituicao where id = " + id;
        st.executeUpdate(sql);
        st.close();
    }

    /**
     * Recebe uma instituição, parâmetro "inst", já existente e modifica
     * suas informações no banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param instituição instituição que terá suas informações alteradas.
     */
    @Override
    public void editar(Instituicao inst) throws SQLException {
        String sql = "update instituicao set sigla = ?, nome = ?, pro_reitor = ?, email_pro_reitor = ? where id = ?";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, inst.getSigla());
        pst.setString(2, inst.getNome());
        pst.setString(3, inst.getProReitor());
        pst.setString(4, inst.getEmailProReitor());
        pst.setInt(5, inst.getId());
        pst.executeUpdate();
        pst.close();
    }

    /**
     * Busca uma instituição no banco de dados, que tenha o id passado como parâmetro.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param id da instituição a ser recuperada.
     * @return instituição recuperada do banco de dados.
     */
    @Override
    public Instituicao recuperarPorId(int id) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select * "
                + "from instituicao inst "
                + "where inst.id = " + id;

        ResultSet rs = st.executeQuery(sql);
        Instituicao inst = null;

        if (rs.next()) {
            inst = new Instituicao();
            preencher(inst, rs);
        }

        st.close();
        return inst;
    }

    /**
     * povoa um objeto "Instituição" passado como parâmetro ("inst") com as informações contidas no
     * parâmetro do tipo "ResultSet" ("rs") que contém as informações da instituição recuperada do
     * banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param inst objeto "Instituição" que será povoado com as informações de uma instituição recuperado do banco de dados.
     *        rs objeto que contém as informações de uma instituição recuperado do banco de dados.
     */
    private void preencher(Instituicao inst, ResultSet rs) throws SQLException {
        inst.setId(rs.getInt("id"));
        inst.setSigla(rs.getString("sigla"));
        inst.setNome(rs.getString("nome"));
        inst.setProReitor(rs.getString("pro_reitor"));
        inst.setEmailProReitor(rs.getString("email_pro_reitor"));
    }

    /**
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return Lista de todas as Instituições do sistema.
     */
    @Override
    public ArrayList<Instituicao> listarTodos() throws SQLException {
        ArrayList<Instituicao> instituicoes = new ArrayList<Instituicao>();
        // consulta que retorna todas os alunos e seus campos
        Statement st = getConexao().createStatement();
        String sql = "select * from instituicao";
        ResultSet rs = st.executeQuery(sql);
        // cria os objetos "Instituicao" a partir do resultado da busca
        while (rs.next()) {
            Instituicao inst = new Instituicao();
            preencher(inst, rs);
            instituicoes.add(inst);
        }

        st.close();
        return instituicoes;
    }

    /**
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param siglaInstituição sigla da instituição que está sendo buscada
     * no banco de dados.
     * @return instituição que tenha a sigla passada como parâmetro "siglaInstituição"
     */
    @Override
    public Instituicao recuperarPorSigla(String sigla) throws SQLException {
        String sql = "select * from instituicao where sigla = ?;";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, sigla);
        ResultSet rs = pst.executeQuery();
        Instituicao instituicao = new Instituicao();
        if (!rs.next()) {
            instituicao = null;
        } else {
            preencher(instituicao, rs);
        }
        pst.close();
        return instituicao;
    }

    /**
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param nomeInstituição nome da instituição que está sendo buscada no
     * banco de dados.
     * @return instituição que tenha o nome passado como parâmetro "nomeInstituição"
     */
    @Override
    public Instituicao recuperarPorNome(String nome) throws SQLException {
        String sql = "select * from instituicao where nome = ?;";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, nome);
        ResultSet rs = pst.executeQuery();
        Instituicao instituicao = new Instituicao();
        if (!rs.next()) {
            instituicao = null;
        } else {
            preencher(instituicao, rs);
        }
        pst.close();
        return instituicao;
    }
}

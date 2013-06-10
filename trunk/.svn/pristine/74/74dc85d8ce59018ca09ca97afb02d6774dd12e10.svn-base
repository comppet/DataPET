package Dao;

import Model.Atividade;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Model.Ensino;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Esta é uma classe que realiza operações no banco de dados
 * relacionada as atividades de ensino, é subclasse de JDBCDao e implementa
 * a interface EnsinoDAO.
 *
 * @author Eduardo Silva Rosa
 * @version 3.0
 * @since 3.0
 */
public class JDBCEnsinoDao extends JDBCAtividadePublicaDao<Ensino> implements EnsinoDao {

     /**
     * Construtor da classe que recebe uma conexão de uma classe a nível de Controller.
     *
     * @author Eduardo Silva Rosa
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param Conexão com o banco de dados MySQL, recebida de uma classe a nível de Controller.
     */
    public JDBCEnsinoDao(Connection conn) throws SQLException{
        super(conn);
    }
    
     /**
     * Construtor da classe.
     *
     * @author Eduardo Silva Rosa
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    public JDBCEnsinoDao() throws SQLException{
        super(null);
    }
    
     /**
     * Recebe uma atividade de ensino, parâmetro "ensino", e o insere no banco de dados.
     *
     * @author Eduardo Silva Rosa
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param atividade ensino que será inserido no banco de dados.
     * @return o próximo id, da sequência de incremento, que terá a próxima atividade ensino a ser inserida.
     */
    @Override
    public int salvar(Ensino ensino) throws SQLException {
        int proximoId = super.salvar(ensino);
        // Adiciona a nova atividade na tabela de Ensino
        Statement st = getConexao().createStatement();
        String sql = "insert into ensino values(" + proximoId + ");";
        st.executeUpdate(sql);
        st.close();
        return proximoId;
    }

    /**
     * Busca uma atividade de ensino do banco de dados, que tenha o id passado como parâmetro.
     *
     * @author Eduardo Silva Rosa
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param id da atividade ensino a ser recuperado.
     * @return atividade ensino recuperada do banco de dados.
     */
    @Override
    public Ensino recuperarPorId(int id) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select a.id as id, a.id_grupo as id_grupo, titulo, parceiros, descricao, justificativa,"
                + " datainicio, datafim, comentario, resultadosesperados, coletiva,"
                + " resultadosalcancados, natureza.id as id_natureza, natureza.nome as nome_natureza, publicoalvo "
                + "from atividadepublica as atp, atividade as a, natureza, ensino as e "
                + "where atp.id = " + id + " and atp.id = a.id and (atp.id_natureza = natureza.id or (atp.id_natureza = 1 and natureza.id_grupo is null)) and a.id = e.id";
        ResultSet rs = st.executeQuery(sql);
        Ensino e = null;
        
        if (rs.next()) {
            e = new Ensino();
            preencher(e, rs);
        }
        
        st.close();
        return e;
    }
 /**
     * Carrega do banco de dados uma lista de todas as atividade de ensino
     * do grupo PET em determinada data passadas por parâmetro.
     *
     * @author Eduardo Silva Rosa
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idGrupo id do grupo PET, periodoInicio, periodoFim, datas em que ocorreu a atividade de ensino.
     * @return Lista de todas as atividades de ensino do grupo PET e entre as datas passadas como parâmetro.
     */
    @Override
    public ArrayList<Ensino> listarTodos(int idGrupo, Date periodoInicio, Date periodoFim) throws SQLException {
        SimpleDateFormat formatoSQL = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<Ensino> atividades = new ArrayList<Ensino>();
        // consulta que retorna todas as pesquisas e seus campos
        Statement st = getConexao().createStatement();
        String sql = "select a.id as id, a.id_grupo as id_grupo, titulo, parceiros, descricao, justificativa,"
                + " datainicio, datafim, comentario, resultadosesperados, coletiva,"
                + " resultadosalcancados, natureza.id as id_natureza, natureza.nome as nome_natureza, publicoalvo "
                + "from atividadepublica as atp, atividade as a, ensino as e, natureza "
                + "where atp.id = a.id and a.id = e.id and (atp.id_natureza = natureza.id or (atp.id_natureza = 1 and natureza.id_grupo is null)) and a.id_grupo = " + idGrupo + " and "
                + "((datainicio is null and datafim is null) ||"
                + "((datainicio >= '" + formatoSQL.format(periodoInicio) + "' or (datainicio is null and datafim >= '" + formatoSQL.format(periodoInicio) + "')) and "
                + "(datafim <= '" + formatoSQL.format(periodoFim) + "' or (datafim is null and datainicio <= '" + formatoSQL.format(periodoFim) + "')))) order by titulo";
        ResultSet rs = st.executeQuery(sql);
        // cria os objetos "AtividadePublica" a partir do resultado da busca
        while (rs.next()) {
            Ensino e = new Ensino();
            preencher(e, rs);
            atividades.add(e);
        }
        st.close();
        return atividades;
    }

}

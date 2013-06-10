package Dao;

import Model.Pesquisa;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Esta é uma classe que realiza operações no banco de dados
 * relacionada as pesquisas, é subclasse de JDBCDao e implementa
 * a interface PesquisaDAO.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public class JDBCPesquisaDao extends JDBCAtividadeDao<Pesquisa> implements PesquisaDao {
    
     /**
     * Construtor da classe que recebe uma conexão de uma classe a nível de Controller.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param Conexão com o banco de dados MySQL, recebida de uma classe a nível de Controller.
     */

    public JDBCPesquisaDao(Connection conn) throws SQLException{
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
    public JDBCPesquisaDao() throws SQLException{
        super(null);
    }

    /**
     * Recebe uma atividade de pesquisa, parâmetro "pesquisa", e o insere no banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param atividade pesquisa que será inserido no banco de dados.
     * @return o próximo id, da sequência de incremento, que terá a próxima pesquisa a ser inserida.
     */
    @Override
    public int salvar(Pesquisa pesquisa) throws SQLException {
        int proximoId = super.salvar(pesquisa);
        // Adiciona a nova atividade na tabela de pesquisas
        Statement st = getConexao().createStatement();
        String sql = "insert into pesquisa values(" + proximoId + ");";
        st.executeUpdate(sql);
        st.close();
        return proximoId;
    }
    
 /**
     * Busca uma pesquisa do banco de dados, que tenha o id passado como parâmetro.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param id da atividade pesquisa a ser recuperado.
     * @return pesquisa recuperada do banco de dados.
     */
    @Override
    public Pesquisa recuperarPorId(int id) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select a.id as id, id_grupo, titulo, parceiros, descricao, justificativa, datainicio, datafim, comentario, resultadosesperados, resultadosalcancados, coletiva "
                + "from pesquisa as p, atividade as a "
                + "where p.id = " + id + " and p.id = a.id";
        ResultSet rs = st.executeQuery(sql);
        
        Pesquisa p = null;
        
        if (rs.next()) {
            p = new Pesquisa();
            preencher(p, rs);
        }
        
        st.close();
        return p;
    }

     /**
     * Carrega do banco de dados uma lista de todos as pesquisas
     * do grupo PET em determinada data passadas por parâmetro.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idGrupo id do grupo PET, periodoInicio, periodoFim, datas em que ocorreu a pesquisa.
     * @return Lista de todos as pesquisas do grupo PET e entre as datas passadas como parâmetro.
     */
    @Override
    public ArrayList<Pesquisa> listarTodos(int idGrupo, Date periodoInicio, Date periodoFim) throws SQLException {
        SimpleDateFormat formatoSQL = new SimpleDateFormat( "yyyy-MM-dd" );

        ArrayList<Pesquisa> pesquisas = new ArrayList<Pesquisa>();
        // consulta que retorna todas as pesquisas e seus campos
        Statement st = getConexao().createStatement();
        String sql = "select a.id, id_grupo, titulo, parceiros, descricao, justificativa, datainicio, datafim, comentario, resultadosesperados, resultadosalcancados, coletiva "
                + "from pesquisa as p, atividade as a "
                + "where p.id = a.id and a.id_grupo = " + idGrupo + " and "
                + "((datainicio is null and datafim is null) ||"
                + "((datainicio >= '" + formatoSQL.format(periodoInicio) + "' or (datainicio is null and datafim >= '" + formatoSQL.format(periodoInicio) + "')) and "
                + "(datafim <= '" + formatoSQL.format(periodoFim) + "' or (datafim is null and datainicio <= '" + formatoSQL.format(periodoFim) + "')))) order by titulo";
        ResultSet rs = st.executeQuery(sql);
        // cria os objetos "Pesquisa" a partir do resultado da busca
        while (rs.next()) {
            Pesquisa p = new Pesquisa();
            preencher(p, rs);
            pesquisas.add(p);
        }
        
        st.close();
        return pesquisas;
    }

}

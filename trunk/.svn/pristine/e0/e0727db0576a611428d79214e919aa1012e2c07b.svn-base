/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;
import Model.Anotacao;
import Model.Aluno;
//import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.io.*;

/**
 * Esta é uma classe que realiza operações no banco de dados
 * relacionada as Anotações, é subclasse de JDBCDao e implementa
 * a interface AnotacaoDAO.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public class JDBCAnotacaoDao extends JDBCDao<Anotacao> implements AnotacaoDao {

    /**
     * Construtor da classe que recebe uma conexão de uma classe a nível de Controller.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param Conexão com o banco de dados MySQL, recebida de uma classe a nível de Controller.
     */
    public JDBCAnotacaoDao(Connection conn) throws SQLException{
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
    public JDBCAnotacaoDao() throws SQLException{
        super(null);
    }

    /**
     * Recebe uma Anotação, parâmetro "anotacao", e a insere no banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param anotação anotação que será inserida no banco de dados.
     * @return o próximo id, da sequência de incremento, que terá a próxima
     * anotação a ser inserida.
     */
    @Override
    public int salvar(Anotacao anotacao) throws SQLException{
        Statement st = getConexao().createStatement();
        ResultSet rs = st.executeQuery("SHOW TABLE STATUS LIKE 'anotacao'");
        rs.next();
        int proximoId = rs.getInt("Auto_increment");
        st.close();
        
        String carater;
        if (anotacao.getCarater()) carater = "1";
        else carater = "0";
        
        String sql = "insert into anotacao values (null, ?, ?, ?, ?)";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, anotacao.getAluno().getId());
        pst.setDate(2, new java.sql.Date(anotacao.getData().getTime()));
        pst.setString(3, carater);
        pst.setString(4, anotacao.getTexto());
        pst.executeUpdate();

        pst.close();
        return proximoId;
    }

    /**
     * Recebe uma anotação, parâmetro "anotacao", já existente e modifica
     * suas informações no banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param  anotação anotação que terá suas informações alteradas.
     */
    @Override
     public void editar(Anotacao anotacao) throws SQLException{
        String carater;
        if (anotacao.getCarater()) carater = "1";
        else carater = "0";

        String sql = "update anotacao set carater = ?, texto = ? where id = ?";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, carater);
        pst.setString(2, anotacao.getTexto());
        pst.setInt(3, anotacao.getId());
        pst.executeUpdate();

        pst.close();
    }

    /**
     * Recebe o id de uma Anotação, parâmetro "id", e a remove do banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idAnotação idAnotação que será removida do banco de dados.
     */
    @Override
    public void excluir(int id) throws SQLException{
        Statement st = getConexao().createStatement();
        String sql = "delete from anotacao where id = " + id;
        st.executeUpdate(sql);
        st.close();
    }

    /**
     * povoa um objeto "Anotação" passado como parâmetro ("a") com as informações contidas no
     * parâmetro do tipo "ResultSet" ("rs") que contém as informações da anotacação recuperada do
     * banco de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param a objeto "Anotação" que será povoado com as informações de uma anotação recuperada do banco de dados.
     *        rs objeto que contém as informações de uma anotação recuperada do banco de dados.
     */
    private void preencher(Anotacao a, ResultSet rs) throws SQLException{
        a.setId(rs.getInt("id"));
        a.setData(rs.getDate("data"));
        Aluno aluno = new Aluno();
        aluno.setId(rs.getInt("id_aluno"));
        a.setAluno(aluno);
        a.setCarater(rs.getBoolean("carater"));
        a.setTexto(rs.getString("texto"));
    }
    
    /**
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idAnotação id da Anotação da qual se fara a busca no banco de dados.
     * @return anotação anotação referente ao id passado como parametro.
     */
    @Override
    public Anotacao recuperarPorId(int id) throws SQLException{
        Statement st = getConexao().createStatement();
        String sql = "select * from anotacao where id = " + id;
        ResultSet rs = st.executeQuery(sql);
        Anotacao a = null;
        if (rs.next()){
            a = new Anotacao();
            preencher(a, rs);
        }
        st.close();
        return a;
    }

    /**
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idAluno id do aluno do qual se fara a busca por anotações no
     * banco de dados.
     * @return anotações Array de anotações referente ao aluno de id passado
     * como parametro.
     */
    @Override
    public ArrayList<Anotacao> recuperarPorAluno(int id) throws SQLException{
        Statement st = getConexao().createStatement();
        String sql = "select * from anotacao where id_aluno = " + id + " order by id desc";
        ResultSet rs = st.executeQuery(sql);
        ArrayList anotacoes = new ArrayList<Anotacao>();
        while (rs.next()){
            Anotacao a = new Anotacao();
            preencher(a, rs);
            anotacoes.add(a);
        }
        st.close();
        return anotacoes;
    }
}

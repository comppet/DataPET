/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Curso;
import Model.Instituicao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Tiago Peres
 */
public class JDBCCursoDao extends JDBCDao<Curso> implements CursoDao {

    public JDBCCursoDao(Connection conn) throws SQLException{
        super(conn);
    }
    
    public JDBCCursoDao() throws SQLException{
        super(null);
    }

    @Override
    public int salvar(Curso curso) throws SQLException {
        // descobre qual o id do próximo elemento a ser inserido
        Statement st = getConexao().createStatement();
        ResultSet rs = st.executeQuery("SHOW TABLE STATUS LIKE 'curso'");
        rs.next();
        int proximoId = rs.getInt("Auto_increment");
        
        // insere o curso
        st = getConexao().createStatement();
        String sql = "insert into curso values (null, ?, ?, ?, ?)";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, curso.getInstituicao().getId());
        pst.setString(2, curso.getNome());
        pst.setInt(3, curso.getFormacao());
        pst.setInt(4, curso.getPeriodo());
        pst.executeUpdate();
        pst.close();
        return proximoId;
    }

    @Override
    public void editar(Curso curso) throws SQLException {
        String sql = "update curso set nome = ?, formacao = ?, periodo = ? where id = ?";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, curso.getNome());
        pst.setInt(2, curso.getFormacao());
        pst.setInt(3, curso.getPeriodo());
        pst.setInt(4, curso.getId());
        pst.executeUpdate();
        pst.close();
    }

    @Override
    public Curso recuperarPorId(int id) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select * from curso where id = " + id;
        ResultSet rs = st.executeQuery(sql);
        Curso c = null;
        if (rs.next()){
            c = new Curso();
            preencher(c, rs);
        }
        st.close();
        return c;
    }

    @Override
    public ArrayList<Curso> listarTodos(int idInstituicao) throws SQLException {
        ArrayList<Curso> cursos = new ArrayList<Curso>();
        Statement st = getConexao().createStatement();
        String sql = "select * from curso where id_instituicao = " + idInstituicao;
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()){
            Curso c = new Curso();
            preencher(c, rs);
            cursos.add(c);
        }
        st.close();
        return cursos;
    }

    private void preencher(Curso c, ResultSet rs) throws SQLException{
        Instituicao i = new Instituicao();
        i.setId(rs.getInt("id_instituicao"));
        
        c.setId(rs.getInt("id"));
        c.setInstituicao(i);
        c.setNome(rs.getString("nome"));
        c.setFormacao(rs.getInt("formacao"));
        c.setPeriodo(rs.getInt("periodo"));
    }

    @Override
    public void excluir(int id) throws SQLException {
        throw new UnsupportedOperationException("Não é possível excluir um curso.");
    }

    @Override
    public Curso recuperarPorNome(String nomeCurso, int idInstituicao) throws SQLException {
        System.out.println("No dao!!!");
        Statement st = getConexao().createStatement();
        String sql = "select * from curso where nome = ? and id_instituicao = ?;";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, nomeCurso);
        pst.setInt(2, idInstituicao);
        ResultSet rs = pst.executeQuery();
        Curso curso = new Curso();
        if (!rs.next()) {
            curso = null;
        } else {
            preencher(curso, rs);
        }
        pst.close();
        return curso;
    }

}

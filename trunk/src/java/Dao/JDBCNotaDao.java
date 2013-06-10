/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Aluno;
import Model.Nota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
* Classe que opera informaçes de notas no banco de dados.
*
* @author Tiago Peres
* @version 3.0
* @since 3.0
* @throws :: SQLException
*/
public class JDBCNotaDao extends JDBCDao<Nota> implements NotaDao {
    
    
    /**
    * Construtor da classe.
    *
    * @author Tiago Peres
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @param :: Conexão com o banco de dados.
    */
    public JDBCNotaDao(Connection conn) throws SQLException{
        super(conn);
    }
    
    
    /**
    * Construtor da classe.
    *
    * @author Tiago Peres
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    */
    public JDBCNotaDao() throws SQLException{
        super(null);
    }

    
    
    /**
    * Salva uma nota no banco de dados.
    *
    * @author Tiago Peres
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @return :: Identificador para a próxima nota.
    * @param :: Uma nota.
    */
    @Override
    public int salvar(Nota nota) throws SQLException {
        Statement st = getConexao().createStatement();
        ResultSet rs = st.executeQuery("SHOW TABLE STATUS LIKE 'nota'");
        rs.next();
        int proximoId = rs.getInt("Auto_increment");
        st.close();
        
        String sql = "insert into nota values (null, ?, ?, ?, ?)";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, nota.getAluno().getId());
        pst.setFloat(2, nota.getValor());
        pst.setInt(3, nota.getAno());
        if (nota.getSemestre() == 0)
            pst.setNull(4, java.sql.Types.INTEGER);
        else
            pst.setInt(4, nota.getSemestre()); 
        pst.executeUpdate();
        pst.close();
        return proximoId;
    }

    
    /**
    * Edita uma nota no banco de dados.
    *
    * @author Tiago Peres
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @param :: Nova a ser editada.
    */
    @Override
    public void editar(Nota nota) throws SQLException {
        String sql = "update nota set valor = ? where id = ?";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setFloat(1, nota.getValor());
        pst.setInt(2, nota.getId());
        pst.executeUpdate();
        pst.close();
    }

    
    /**
    * Recupera uma nota pelo ID.
    *
    * @author Tiago Peres
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @return :: Mensagem de erro.
    * @param :: ID da nota.
    */
    @Override
    public Nota recuperarPorId(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
    /**
    * Listar todas as notas de um aluno.
    *
    * @author Tiago Peres
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @return :: ArrayList com as Notas.
    * @param :: ID do aluno.
    */
    @Override
    public ArrayList<Nota> listarTodos(int idAluno) throws SQLException {
        ArrayList<Nota> notas = new ArrayList<Nota>();
        Statement st = getConexao().createStatement();
        String sql = "select * from nota where id_aluno = " + idAluno + " order by ano desc";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()){
            Nota n = new Nota();
            preencher(n, rs);
            notas.add(n);
        }
        st.close();
        return notas;
    }

    
    /**
    * `Preenche uma nota no banco de dados.
    *
    * @author Tiago Peres
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @param :: Uma nota e uma consulta no banco de dados.
    */
    private void preencher(Nota n, ResultSet rs) throws SQLException{
        Aluno a = new Aluno();
        a.setId(rs.getInt("id_aluno"));
        
        n.setId(rs.getInt("id"));
        n.setAluno(a);
        n.setValor(rs.getFloat("valor"));
        n.setAno(rs.getInt("ano"));
        n.setSemestre(rs.getInt("semestre"));
    }

    
    /**
    * Listar todas as notas do grupo.
    *
    * @author Tiago Peres
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @return :: ArrayList com as notas.
    * @param :: ID do grupo, ano das notas e semestre.
    */
    @Override
    public ArrayList<Nota> listarTodosGrupo(int idGrupo, int ano, int semestre) throws SQLException{
        ArrayList<Nota> notas = new ArrayList<Nota>();
        String buscaSemestre = "";
        if (semestre > 0) buscaSemestre = " and (n.semestre = " + semestre + " or n.semestre is NULL)";
        Statement st = getConexao().createStatement();
        String sql = "select n.id, n.id_aluno, n.valor, n.ano, n.semestre from nota as n, petiano as p "
                + "where n.id_aluno = p.id and p.id_grupo = " + idGrupo + " and n.ano = " + ano
                + buscaSemestre + " order by ano desc";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()){
            Nota n = new Nota();
            preencher(n, rs);
            notas.add(n);
        }
        st.close();
        return notas;
    }

    
    
    /**
    * Excluir uma nota pelo ID no banco de dados.
    *
    * @author Tiago Peres
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @return :: MEnsagem de erro.
    * @param :: ID da nota.
    */
    @Override
    public void excluir(int id) throws SQLException {
        String sql = "delete from nota where id_aluno = ?";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, id);
        pst.executeUpdate();
        pst.close();
    }

}

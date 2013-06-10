/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Usuario;
import Model.Tutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
* Classe que opera informaçes do tutor no banco de dados.
*
* @author Pedro Augusto
* @version 3.0
* @since 3.0
* @throws :: SQLException
*/
public class JDBCTutorDao extends JDBCPetianoDao<Tutor> implements TutorDao {

    
    /**
    * Construtor da classe.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @param :: Conexão com o banco de dados.
    */
    public JDBCTutorDao(Connection conn) throws SQLException{
        super(conn);
    }
    
    
    /**
    * Construtor da classe.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    */
    public JDBCTutorDao() throws SQLException{
        super(null);
    }

    
    /**
    * Salva um tutor no banco de dados.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @return :: Identificador para o próximo tutor.
    * @param :: Um usuário tutor.
    */
    @Override
    public int salvar(Tutor tutor) throws SQLException{
        int proximoId = super.salvar(tutor);
        String sql = "insert into tutor values (?, ?, ?)";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, proximoId);
        pst.setString(2, tutor.getTitulacao());
        pst.setString(3, tutor.getArea());
        pst.executeUpdate();
        pst.close();
        return proximoId;
    }

    
    /**
    * Edita um tutor no banco de dados.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @param :: Um usuário tutor.
    */
    @Override
     public void editar(Tutor tutor) throws SQLException{
        super.editar(tutor);
        String sql = "update tutor set titulacao = ?, area = ? where id = ?";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, tutor.getTitulacao());
        pst.setString(2, tutor.getArea());
        pst.setInt(3, tutor.getId());
        pst.executeUpdate();
        pst.close();
     }

    
    
    /**
    * Recupera um tutor no banco de dados.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @return :: Um tutor.
    * @param :: ID do tutor.
    */
    @Override
     public Tutor recuperarPorId(int id) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select u.id, email, senha, p.id_grupo, nome, telefone, celular, dataIngressoPet, dataSaidaPet, titulacao, area "
                    + "from usuario as u, petiano as p, tutor as t "
                    + "where t.id = " + id + " and t.id = p.id and p.id = u.id";
        ResultSet rs = st.executeQuery(sql);
        Tutor t = null;
        
        if (rs.next()){
            t = new Tutor();
            preencher(t, rs);
        }
        
        st.close();
        return t;
     }

    
    /**
    * Preenche as informações de um tutor no banco de dados.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @param :: Um usuário e uma consulta no banco de dados.
    */
    @Override
     protected void preencher(Usuario u, ResultSet rs) throws SQLException{
        super.preencher(u, rs);
        Tutor t = (Tutor) u;
        t.setTitulacao(rs.getString("titulacao"));
        t.setArea(rs.getString("area"));
    }

    
    
    /**
    * Lista todos os tutores de um grupo no banco de dados.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @return :: Um vetor com todos os tutores.
    * @param :: ID do grupo.
    */
    @Override
    public ArrayList<Tutor> listarTodos(int idGrupo) throws SQLException {
        ArrayList<Tutor> tutores = new ArrayList<Tutor>();
        // consulta que retorna todos os tutores e seus campos
        Statement st = getConexao().createStatement();
        String sql = "select u.id, u.email, u.senha, p.id_grupo, p.nome, telefone, celular, p.dataIngressoPet, p.dataSaidaPet, t.titulacao, t.area "
                    + "from usuario as u, petiano as p, tutor as t "
                    + "where p.id = u.id and t.id = p.id and p.id_grupo = " + idGrupo;
        ResultSet rs = st.executeQuery(sql);
        // cria os objetos "Tutor" a partir do resultado da busca
        while (rs.next()){
            Tutor t = new Tutor();
            preencher(t, rs);
            tutores.add(t);
        }
        st.close();
        return tutores;
    }

    
    
    /**
    * Recupera qual tutor está ativo no grupo.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @return :: Tutor ativo.
    * @param :: ID do grupo.
    */
    @Override
    public Tutor recuperarTutorAtivo(int idGrupo) throws SQLException {
        Tutor tutor = null;
        // consulta que retorna o tutor ativo (data de saída igual a null)
        Statement st = getConexao().createStatement();
        String sql = "select u.id, u.email, u.senha, p.id_grupo, p.nome, telefone, celular, p.dataIngressoPet, p.dataSaidaPet, t.titulacao, t.area "
                    + "from usuario as u, petiano as p, tutor as t "
                    + "where p.id = u.id and t.id = p.id and p.id_grupo = " + idGrupo + " and p.dataSaidaPet is null";
        ResultSet rs = st.executeQuery(sql);
        // cria o objeto "Tutor" a partir do resultado da busca
        if (rs.next()){
            tutor = new Tutor();
            preencher(tutor, rs);
        }
        st.close();
        return tutor;
    }

    
    /**
    * Recupera um tutor no banco de dados.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @return :: Um Tutor.
    * @param :: E-mail do tutor.
    */
    @Override
    public Tutor recuperarPorEmail(String email) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select u.id, email, senha, p.id_grupo, nome, telefone, celular, dataIngressoPet, dataSaidaPet, titulacao, area "
                    + "from usuario as u, petiano as p, tutor as t "
                    + "where u.email = '" + email + "' and t.id = p.id and p.id = u.id";
        ResultSet rs = st.executeQuery(sql);
        Tutor t = null;
        
        if (rs.next()){
            t = new Tutor();
            preencher(t, rs);
        }
        
        st.close();
        return t;
    }

}
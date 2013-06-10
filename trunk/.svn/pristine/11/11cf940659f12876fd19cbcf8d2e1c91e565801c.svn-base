/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Usuario;
import Model.Aluno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
    * Classe que opera informaçes do aluno no banco de dados.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    */
public class JDBCAlunoDao extends JDBCPetianoDao<Aluno> implements AlunoDao {

    
    /**
    * Construtor da classe.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @param :: Uma conexão com o banco de dados.
    */
    public JDBCAlunoDao(Connection conn) throws SQLException{
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
    public JDBCAlunoDao() throws SQLException{
        super(null);
    }

    
    
    /**
    * Salva um aluno no banco de dados.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @return :: Identificador para o próximo aluno.
    * @param :: Um usuário aluno.
    */
    @Override
    public int salvar(Aluno aluno) throws SQLException {
        int proximoId = super.salvar(aluno);

        String sql = "insert into aluno values (?, ?, ?, ?, ?)";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, proximoId);
        pst.setBoolean(2, aluno.isBolsista());
        pst.setInt(3, aluno.getPeriodo());
        pst.setDate(4, new java.sql.Date(aluno.getDataIngressoInst().getTime()));
        pst.setFloat(5, aluno.getCraGeral());
        pst.executeUpdate();
        pst.close();
        return proximoId;
    }

    
    /**
    * Edita as informações de um aluno no banco de dados.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @param :: Um usuário aluno.
    */
    @Override
    public void editar(Aluno aluno) throws SQLException {
        super.editar(aluno);
        System.out.println("CRA Geral: " + aluno.getCraGeral());
        String sql = "update aluno set periodo = ?, bolsista = ?, dataIngressoInst = ?, cra_geral = ? where id = ?";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, aluno.getPeriodo());
        pst.setBoolean(2, aluno.isBolsista());
        pst.setDate(3, new java.sql.Date(aluno.getDataIngressoInst().getTime()));
        pst.setFloat(4, aluno.getCraGeral());
        pst.setInt(5, aluno.getId());
        
        pst.executeUpdate();
        pst.close();
    }

    
    /**
    * Recupera um aluno pelo ID no banco de dados.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @return :: Um aluno.
    * @param :: ID do aluno.
    */
    @Override
    public Aluno recuperarPorId(int id) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select u.id, u.email, u.senha, p.id_grupo, p.nome, telefone, celular, p.dataIngressoPet, p.dataSaidaPet, a.bolsista, a.periodo, a.dataIngressoInst, a.cra_geral "
                + "from usuario as u, petiano as p, aluno as a "
                + "where a.id = " + id + " and a.id = p.id and p.id = u.id; ";
        ResultSet rs = st.executeQuery(sql);
        Aluno a = null;
        
        if (rs.next()) {
            a = new Aluno();
            preencher(a, rs);
            return a;
        }
        
        st.close();
        return a;
    }

    
    
    /**
    * Preenche um aluno no banco de dados.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @param :: Um usuário e uma consulta no banco de dados.
    */
    @Override
    protected void preencher(Usuario u, ResultSet rs) throws SQLException {
        super.preencher(u, rs);
        Aluno a = (Aluno) u;
        a.setBolsista(rs.getBoolean("bolsista"));
        a.setPeriodo(rs.getInt("periodo"));
        a.setDataIngressoInst(rs.getDate("dataIngressoInst"));
        a.setCraGeral(rs.getFloat("cra_geral"));
    }

    
    /**
    * Lista todos os alunos de um grupo.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @return :: ArrayList com todos os alunos do grupo.
    * @param :: ID do grupo.
    */
    @Override
    public ArrayList<Aluno> listarTodos(int idGrupo) throws SQLException {
        ArrayList<Aluno> alunos = new ArrayList<Aluno>();
        // consulta que retorna todas os alunos e seus campos
        Statement st = getConexao().createStatement();
        String sql = "select u.id, u.email, u.senha, p.id_grupo, p.nome, telefone, celular, p.dataIngressoPet, p.dataSaidaPet, a.bolsista, a.periodo, a.dataIngressoInst, a.cra_geral "
                + "from usuario as u, petiano as p, aluno as a "
                + "where p.id = u.id and a.id = p.id and p.id_grupo = " + idGrupo;
        ResultSet rs = st.executeQuery(sql);
        // cria os objetos "Aluno" a partir do resultado da busca
        while (rs.next()) {
            Aluno a = new Aluno();
            preencher(a, rs);
            alunos.add(a);
        }
        st.close();
        return alunos;
    }

    
    
    /**
    * Recupera um aluno no banco de dados.
    *
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @throws :: SQLException
    * @return :: Um aluno.
    * @param :: E-mail.
    */
    @Override
    public Aluno recuperarPorEmail(String email) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select u.id, u.email, u.senha, p.id_grupo, p.nome, telefone, celular, p.dataIngressoPet, p.dataSaidaPet, a.bolsista, a.periodo, a.dataIngressoInst, a.cra_geral "
                + "from usuario as u, petiano as p, aluno as a "
                + "where u.email = '" + email + "' and a.id = p.id and p.id = u.id; ";
        ResultSet rs = st.executeQuery(sql);
        Aluno a = null;
        if (rs.next()) {
            a = new Aluno();
            preencher(a, rs);
            return a;
        }
        st.close();
        return a;
    }
}

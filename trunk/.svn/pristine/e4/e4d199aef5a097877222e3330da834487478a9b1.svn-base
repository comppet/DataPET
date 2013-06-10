/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Aluno;
import Model.Atividade;
import Model.Grupo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Tiago Peres
 */
public abstract class JDBCAtividadeDao<T extends Atividade> extends JDBCDao<T> implements AtividadeDao<T>{
    
    public JDBCAtividadeDao(Connection conn) throws SQLException{
        super(conn);
    }

    @Override
    public int salvar(T atividade) throws SQLException {
        // descobre o valor do campo id (que e' auto-incrementado) para a proxima insercao
        Statement st = getConexao().createStatement();
        ResultSet rs = st.executeQuery("SHOW TABLE STATUS LIKE 'atividade'");
        rs.next();
        int proximoId = rs.getInt("Auto_increment");
        atividade.setId(proximoId);
        
        java.sql.Date inicio = null;
        java.sql.Date fim = null;
        if (atividade.getDataInicio() != null)
            inicio = new java.sql.Date(atividade.getDataInicio().getTime());
        if (atividade.getDataFim() != null)
            fim = new java.sql.Date(atividade.getDataFim().getTime());
        
        st.close();
        // Adiciona a nova atividade na tabela de atividades
        //st = getConexao().createStatement();
        String sql = "insert into atividade values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, atividade.getGrupo().getId());
        pst.setString(2, atividade.getTitulo());
        pst.setString(3, atividade.getParceiros());
        pst.setString(4, atividade.getDescricao());
        pst.setString(5, atividade.getJustificativa());
        pst.setDate(6, inicio);
        pst.setDate(7, fim);
        pst.setString(8, atividade.getComentario());
        pst.setString(9, atividade.getResultadosEsperados());
        pst.setString(10, atividade.getResultadosAlcancados());
        pst.setBoolean(11, atividade.isColetiva());
        pst.executeUpdate();
        pst.close();
        
        // adiciona os responsaveis
        adicionaResponsaveis(atividade);
        return proximoId;
    }

    private void adicionaResponsaveis(Atividade atividade) throws SQLException{
        String sql = "insert into atividade_aluno values (?, ?)";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, atividade.getId());
        for (int i = 0; i < atividade.getResponsaveis().size(); i++){
            pst.setInt(2, atividade.getResponsaveis().get(i).getId());
            pst.executeUpdate();
        }
        pst.close();
    }

    @Override
    public void editar(T atividade) throws SQLException{
        java.sql.Date inicio = null;
        java.sql.Date fim = null;
        if (atividade.getDataInicio() != null)
            inicio = new java.sql.Date(atividade.getDataInicio().getTime());
        if (atividade.getDataFim() != null)
            fim = new java.sql.Date(atividade.getDataFim().getTime());
        
        String sql = "update atividade set titulo = ?, parceiros = ?, descricao = ?, justificativa = ?, "
                + "datainicio = ?, datafim = ?, comentario = ?, resultadosesperados = ?, resultadosalcancados = ?, "
                + "coletiva = ? where id = ?";
        
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, atividade.getTitulo());
        pst.setString(2, atividade.getParceiros());
        pst.setString(3, atividade.getDescricao());
        pst.setString(4, atividade.getJustificativa());
        pst.setDate(5, inicio);
        pst.setDate(6, fim);
        pst.setString(7, atividade.getComentario());
        pst.setString(8, atividade.getResultadosEsperados());
        pst.setString(9, atividade.getResultadosAlcancados());
        pst.setBoolean(10, atividade.isColetiva());
        pst.setInt(11, atividade.getId());

        pst.executeUpdate();
        pst.close();

        // remove todos os responsaveis por esta atividade
        sql = "delete from atividade_aluno where id_atividade = ?";
        pst = getConexao().prepareStatement(sql);
        pst.setInt(1, atividade.getId());
        pst.executeUpdate();
        pst.close();
        // adiciona os novos responsáveis
        adicionaResponsaveis(atividade);
    }

    /* Metodo interno a classe atividade e as que extendem esta. Seu objetivo
     * e' evitar a repeticao de codigo na recuperacao de uam atividade.
     */
    protected void preencher(Atividade a, ResultSet rs) throws SQLException{
        Grupo g = new Grupo();
        g.setId(rs.getInt("id_grupo"));
        
        a.setResponsaveis(encontrarResponsaveis(rs.getInt("id")));
        a.setId(rs.getInt("id"));
        a.setGrupo(g);
        a.setTitulo(rs.getString("titulo"));
        a.setParceiros(rs.getString("parceiros"));
        a.setDescricao(rs.getString("descricao"));
        a.setJustificativa(rs.getString("justificativa"));
        a.setDataInicio(rs.getDate("datainicio"));
        a.setDataFim(rs.getDate("datafim"));
        a.setComentario(rs.getString("comentario"));
        a.setResultadosEsperados(rs.getString("resultadosesperados"));
        a.setResultadosAlcancados(rs.getString("resultadosalcancados"));
        a.setColetiva(rs.getBoolean("coletiva"));
    }

    private ArrayList<Aluno> encontrarResponsaveis(int idAtividade) throws SQLException{
        Statement st = getConexao().createStatement();
        String sql = "select aluno.id as id, petiano.nome as nome "
                     + "from aluno, petiano, atividade_aluno as aa "
                     + "where aa.id_atividade = " + idAtividade + " and aa.id_aluno = aluno.id and aluno.id = petiano.id";
        ResultSet rs = st.executeQuery(sql);

        ArrayList<Aluno> responsaveis = new ArrayList<Aluno>();
        while (rs.next()){
            Aluno aluno = new Aluno();
            aluno.setId(rs.getInt("id"));
            aluno.setNome(rs.getString("nome"));
            responsaveis.add(aluno);
        }

        return responsaveis;
    }

    @Override
    public void excluir(int id) throws SQLException{
        Statement st = getConexao().createStatement();
        String sql = "delete from atividade where id = " + id + ";";
        System.out.println(sql);
        st.executeUpdate(sql);
        System.out.println("Expressão executada");
        st.close();
    }
}

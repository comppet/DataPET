/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Atividade;
import Model.AtividadePublica;
import Model.Natureza;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author edusr
 */
public abstract class JDBCAtividadePublicaDao<T extends AtividadePublica> extends JDBCAtividadeDao<T> implements AtividadePublicaDao<T> {

    protected String natureza;
    protected String publicoAlvo;

    public JDBCAtividadePublicaDao(Connection conn) throws SQLException{
        super(conn);
    }
    
    @Override
    public int salvar(T atividade) throws SQLException {
        int proximoId = super.salvar(atividade);
        // Adiciona a nova atividade na tabela de atividades publicas
        String sql = "insert into atividadepublica values (?, ?, ?)";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, proximoId);
        pst.setString(2, atividade.getPublicoAlvo());
        pst.setInt(3, atividade.getNatureza().getId());
        pst.executeUpdate();
        pst.close();
        return proximoId;
    }
    
    @Override
    public void editar(T atividade) throws SQLException {
        super.editar(atividade);
        //Edita a atividade na tabela de atividades publicas
        String sql = "update atividadepublica set id_natureza = ?, publicoalvo = ? where id = ?";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, atividade.getNatureza().getId());
        pst.setString(2, atividade.getPublicoAlvo());
        pst.setInt(3, atividade.getId());
        pst.executeUpdate();
        pst.close();
    }

    @Override
    public void preencher(Atividade a,ResultSet rs) throws SQLException{
        super.preencher(a,rs);
        AtividadePublica ap = (AtividadePublica) a;
        Natureza nat = new Natureza(rs.getInt("id_natureza"), rs.getString("nome_natureza"));
        ap.setNatureza(nat);
        ap.setPublicoAlvo(rs.getString("publicoAlvo"));     
    }

}
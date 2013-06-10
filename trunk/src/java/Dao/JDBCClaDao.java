/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Cla;
import Model.Instituicao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe que opera as informações de Cla no banco de dados.
 *
 * @author Eduardo Silva
 * @version 3.0
 * @since 3.0
 * @throws :: SQLException
 */
public class JDBCClaDao extends JDBCUsuarioDao<Cla> implements ClaDao {

    /**
     * Construtor da classe.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @param :: Uma conexão com o banco de dados.
     */
    public JDBCClaDao(Connection conn) throws SQLException {
        super(conn);
    }

    /**
     * Construtor da classe sem conexão.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     */
    public JDBCClaDao() throws SQLException {
        super(null);
    }

    /**
     * Salva um usuário cla no banco de dados.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @return :: Identificador para o próximo cla.
     * @param :: Um usuário cla.
     */
    @Override
    public int salvar(Cla cla) throws SQLException {
        int proximoId = super.salvar(cla);
        // Adiciona novo usuario na tabela de petianos
        if (cla.getIdCla() == 0) {
            String sql = "insert into cla values (?, ?, ?)";
            PreparedStatement pst = getConexao().prepareStatement(sql);
            pst.setInt(1, proximoId);
            pst.setInt(2, cla.getInstituicao().getId());
            pst.setString(3, cla.getInterlocutor());
            pst.executeUpdate();
            pst.close();
            cla.setIdCla(proximoId);
        }
        java.sql.Date ingresso = null;
        java.sql.Date saida = null;
        String sql2 = "insert into usuario_cla values (?, ?, ?, ?, ?)";
        PreparedStatement pst2 = getConexao().prepareStatement(sql2);
        if(cla.getDataIngressoCla() != null){
            ingresso = new java.sql.Date(cla.getDataIngressoCla().getTime());
        }
        if(cla.getDataSaidaCla() != null){
            saida = new java.sql.Date(cla.getDataSaidaCla().getTime());
        }
        pst2.setInt(1, proximoId);
        pst2.setInt(2, cla.getIdCla());
        pst2.setString(3, cla.getNome());
        pst2.setDate(4, ingresso);
        pst2.setDate(5, saida);
        pst2.executeUpdate();
        pst2.close();
        return proximoId;
    }

    /**
     * Edita um usuário cla no banco de dados.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @param :: Um usuário cla.
     */
    @Override
    public void editar(Cla cla) throws SQLException {
        super.editar(cla);
        String sql = "update cla set interlocutor = ? where id = ?";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, cla.getInterlocutor());
        pst.setInt(2, cla.getId());
        pst.executeUpdate();
        pst.close();
        java.sql.Date ingresso = null;
        java.sql.Date saida = null;
        String sql2 = "update usuario_cla set nome = ?, data_ingresso_cla = ?, "
                + "data_saida_cla = ? where id_usuario = ?";
        if(cla.getDataIngressoCla() != null){
            ingresso = new java.sql.Date(cla.getDataIngressoCla().getTime());
        }
        if(cla.getDataSaidaCla() != null){
            saida = new java.sql.Date(cla.getDataSaidaCla().getTime());
        }
        PreparedStatement pst2 = getConexao().prepareStatement(sql2);
        pst2.setString(1, cla.getNome());
        pst2.setDate(2, ingresso);
        pst2.setDate(3, saida);
        pst2.setInt(4, cla.getId());
        pst2.executeUpdate();
        pst2.close();
    }

    /**
     * Preenche informações de um usuário cla no banco de dados.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @param :: Um usuário cla e uma consulta do banco de dados.
     */
    private void preencher(Cla cla, ResultSet rs) throws SQLException {
        super.preencher(cla, rs);
        Instituicao i = new Instituicao();
        i.setId(rs.getInt("id_instituicao"));
        cla.setIdCla(rs.getInt("id_cla"));
        cla.setInstituicao(i);
        cla.setInterlocutor(rs.getString("interlocutor"));
        cla.setNome(rs.getString("nome"));
        cla.setDataIngressoCla(rs.getDate("data_ingresso_cla"));
        cla.setDataSaidaCla(rs.getDate("data_saida_cla"));
    }

    /**
     * Recupera um usuário cla no banco de dados.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @return :: Um Cla.
     * @param :: Identificador de um usuário cla.
     */
    @Override
    public Cla recuperarPorId(int id) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select u.id, email, senha, id_instituicao, interlocutor, "
                + "id_cla, nome, data_ingresso_cla, data_saida_cla "
                + "from usuario as u, cla as c, usuario_cla as uc "
                + "where u.id = " + id + " and u.id = uc.id_usuario and uc.id_cla = c.id";
        //System.out.println(sql);
        ResultSet rs = st.executeQuery(sql);
        Cla cla = null;

        if (rs.next()) {
            cla = new Cla();
            preencher(cla, rs);
        }

        st.close();
        return cla;
    }

    /**
     * Recupera o interlocutor do cla da instituição identificada pelo parâmetro "idInstituicao" do banco de dados.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @return :: Um Cla.
     * @param :: Identificador de um usuário cla.
     */
    @Override
    public Cla recuperarInterlocutorCla(int idInstituicao) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select u.id, email, senha, id_instituicao, interlocutor, "
                + "id_cla, nome, data_ingresso_cla, data_saida_cla "
                + "from usuario as u, cla as c, usuario_cla as uc "
                + "where c.id_instituicao = " + idInstituicao + " and u.id = uc.id_usuario and uc.id_usuario = c.id";
        //System.out.println(sql);
        ResultSet rs = st.executeQuery(sql);
        Cla cla = null;

        if (rs.next()) {
            cla = new Cla();
            preencher(cla, rs);
        }
        System.out.println(sql);
        st.close();
        return cla;
    }

    /**
     * Recupera o usuário cla, da instituição identificada por "idInstituicao", do banco de dados.
     *
     * @author Pedro Augusto
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @return :: Cla.
     * @param :: Identificador da instituição.
     */
    @Override
    public ArrayList<Cla> recuperarPorInstituicao(int idInstituicao) throws SQLException {
        Statement st = getConexao().createStatement();
        ArrayList<Cla> clas = new ArrayList<Cla>();
        String sql = "select u.id, u.email, u.senha, cla.id_instituicao, cla.interlocutor, cla.id, uc.nome, "
                + "uc.data_ingresso_cla, uc.data_saida_cla, id_cla "
                + "from usuario as u, cla, usuario_cla as uc "
                + "where uc.id_usuario = u.id and uc.id_cla = cla.id and cla.id_instituicao = " + idInstituicao + ";";
        //System.out.println(sql);
        ResultSet rs = st.executeQuery(sql);
        Cla cla = null;
        while (rs.next()) {
            cla = new Cla();
            preencher(cla, rs);
            clas.add(cla);
        }
        st.close();
        return clas;
    }

    /**
     * Recupera um usuário cla no banco de dados.
     *
     * @author Eduardo Silva
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @return :: Cla.
     * @param :: E-mail do usuário cla..
     */
    @Override
    public Cla recuperarPorEmail(String email) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "select id, email, senha, id_instituicao, interlocutor "
                + "nome, data_ingresso_cla, data_saida_cla"
                + "from usuario as u, cla as c "
                + "where u.email = '" + email + "' and c.id = u.id";
        //System.out.println(sql);
        ResultSet rs = st.executeQuery(sql);
        Cla cla = null;

        if (rs.next()) {
            cla = new Cla();
            preencher(cla, rs);
        }

        st.close();
        return cla;
    }
}
